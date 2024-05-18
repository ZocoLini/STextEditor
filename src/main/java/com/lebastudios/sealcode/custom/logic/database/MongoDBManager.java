package com.lebastudios.sealcode.custom.logic.database;

import com.lebastudios.sealcode.core.logic.config.FilePaths;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSBuckets;
import com.mongodb.client.gridfs.GridFSFindIterable;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.mongodb.client.gridfs.model.GridFSUploadOptions;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.io.*;
import java.util.function.Function;

public class MongoDBManager implements IDBManager<MongoClient>
{
    private static MongoDBManager instance;

    public static MongoDBManager getInstance()
    {
        if (instance == null) instance = new MongoDBManager();

        return instance;
    }

    private MongoDBManager()
    {
    }

    private static final String hostname = "vzy.h.filess.io";
    private static final String database = "SealCodeSettingsSync_badacross";
    private static final String port = "27018";
    private static final String username = "SealCodeSettingsSync_badacross";
    private static final String password = "a66843df497ecff0a235387d544043a5ba796ba1";

    private final File[] directoriesToManage = new File[]
            {
                    new File(FilePaths.getProgLangSyntaxDirectory()),
                    new File(FilePaths.getGlobalConfigDirectory())
            };

    @Override
    public boolean connect(Function<MongoClient, Boolean> function)
    {
        try (MongoClient connection = MongoClients.create(
                String.format("mongodb://%s:%s@%s:%s/%s", username, password, hostname, port, database)))
        {
            if (function != null) return function.apply(connection);
            return true;
        } catch (Exception exception)
        {
            return false;
        }
    }

    public boolean pushUserFiles()
    {
        if (!LogInUser.isAnyAccountConnected()) return false;

        User user = User.Deserialize();
        if (user == null) return false;

        try
        {
            for (var directory : directoriesToManage)
            {
                if (directory.isFile()) continue;

                pushDirectory(directory);
            }
        } catch (Exception exception)
        {
            return false;
        }

        return true;
    }

    private boolean pushDirectory(File file)
    {
        if (file.isFile())
        {
            System.err.println("Only directories should be pushed.");
            return false;
        }

        return connect(mongoClient -> {
            MongoDatabase database = mongoClient.getDatabase(MongoDBManager.database);

            // Create a gridFSBucket using the default bucket name "fs"
            GridFSBucket gridFSBucket = GridFSBuckets.create(database);

            // Create some custom options
            String userDirectory = User.Deserialize().userName()
                    + "::" + file.getParentFile().getName();

            GridFSUploadOptions options = new GridFSUploadOptions()
                    .chunkSizeBytes(1024).metadata(new Document("directory", userDirectory));

            // Find the files in the directory delete it
            GridFSFindIterable gridFSIterator = gridFSBucket.find(
                    Filters.eq("metadata.directory", userDirectory));

            for (var gridFSFile : gridFSIterator)
            {
                gridFSBucket.delete(gridFSFile.getObjectId());
            }

            for (File f : file.listFiles())
            {
                pushFile(f, gridFSBucket, options);
            }
            
            return true;
        });
    }

    private boolean pushFile(File file, GridFSBucket gridFSBucket, GridFSUploadOptions options)
    {
        if (file.isDirectory())
        {
            System.err.println("Only files should be pushed.");
            return false;
        }

        // Push the file to the db
        return connect(mongoClient ->
        {
            try (InputStream streamToUploadFrom = new FileInputStream(file))
            {
                gridFSBucket.uploadFromStream(file.getName(), streamToUploadFrom, options);
                System.out.println("Succesfuly pushed file: " + file);
            } catch (IOException e)
            {
                System.err.println("Error while uploading file: " + file.getName());
                e.printStackTrace();
                return false;
            }

            return true;
        });
    }

    public boolean pullUserFiles()
    {
        if (!LogInUser.isAnyAccountConnected()) return false;

        User user = User.Deserialize();
        if (user == null) return false;

        try
        {
            for (var directory : directoriesToManage)
            {
                if (directory.isFile()) continue;

                pullDirectory(directory, user);
            }
        } catch (Exception exception)
        {
            return false;
        }

        return true;
    }

    private boolean pullDirectory(File file, User user)
    {
        if (file.isFile())
        {
            System.err.println("Only directories should be pulled.");
            return false;
        }

        // Pull the file from the db
        return connect(mongoClient ->
        {
            MongoDatabase database = mongoClient.getDatabase(MongoDBManager.database);

            GridFSBucket gridFSBucket = GridFSBuckets.create(database);

            // Find the files with the customId and delete it
            String userDirectory = user.userName()
                    + "::" + file.getName();
            GridFSFindIterable gridFSIterator = gridFSBucket.find(
                    Filters.eq("metadata.directory", userDirectory));

            if (gridFSIterator.first() == null) 
            {
                System.out.println("No files found to pull in the directory: " + file);
                return false;
            }
            
            for (var variable : file.listFiles())
            {
                variable.delete();
                System.out.println("Deleted file before pulling in the directory: " + variable);
            }

            for (GridFSFile gridFSFile : gridFSIterator)
            {
                File newFile = new File(file.getAbsolutePath() + "/" + gridFSFile.getFilename());
                pullFile(newFile, gridFSBucket, gridFSFile.getObjectId());
            }

            return true;
        });
    }

    private boolean pullFile(File file, GridFSBucket gridFSBucket, ObjectId fileId)
    {
        try (OutputStream streamToDownloadTo = new FileOutputStream(file))
        {
            gridFSBucket.downloadToStream(fileId, streamToDownloadTo);
            System.out.println("Succesfuly pulled file: " + file);
        } catch (IOException e)
        {
            System.err.println("Error while downloading file: " + file.getName());
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public boolean pullDefaultFiles()
    {
        User user = User.defaultUser();

        try
        {
            for (var directory : directoriesToManage)
            {
                if (directory.isFile()) continue;

                new Thread(() -> pullDirectory(directory, user)).start();
            }
        } catch (Exception exception)
        {
            return false;
        }

        return true;
    }
}

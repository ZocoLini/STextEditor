package com.lebastudios.sealcodeplugins.database;

import com.lebastudios.sealcode.FilePaths;
import com.lebastudios.sealcode.config.GlobalConfig;
import com.lebastudios.sealcode.events.AppEvents;
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
                    new File(FilePaths.getHighlightingRulesDir()),
                    new File(FilePaths.getGlobalConfigDirectory()),
                    new File(FilePaths.getCompletationsDir())
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
            return connect(mongoClient -> {
                for (var directory : directoriesToManage)
                {
                    if (directory.isFile()) continue;

                    pushDesignedDirectory(directory, mongoClient);
                }

                return true;
            });
        } catch (Exception exception)
        {
            return false;
        }
    }

    private boolean pushDesignedDirectory(File file, MongoClient mongoClient)
    {
        if (file.isFile())
        {
            System.err.println("Only directories should be pushed.");
            return false;
        }

        MongoDatabase database = mongoClient.getDatabase(MongoDBManager.database);

        // Create a gridFSBucket using the default bucket styleClass "fs"
        GridFSBucket gridFSBucket = GridFSBuckets.create(database, User.Deserialize().userName());

        // Find the files in the directory delete it
        GridFSFindIterable gridFSIterator = gridFSBucket.find(
                Filters.eq("metadata.directory", file.getName()));

        for (var gridFSFile : gridFSIterator)
        {
            gridFSBucket.delete(gridFSFile.getObjectId());
        }

        for (File f : file.listFiles())
        {
            pushFile(f, gridFSBucket);
        }

        return true;
    }

    private boolean pushFile(File file, GridFSBucket gridFSBucket)
    {
        if (file.isDirectory())
        {
            System.err.println("Only files should be pushed.");
            return false;
        }

        // Create some custom options
        GridFSUploadOptions options = new GridFSUploadOptions()
                .chunkSizeBytes(1024).metadata(new Document("directory", file.getParentFile().getName()));

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
    }

    public boolean pullUserFiles()
    {
        User user = User.Deserialize();
        
        if (user == null) return false;

        try
        {
            return connect(mongoClient -> {
                for (var directory : directoriesToManage)
                {
                    if (directory.isFile()) continue;

                    pullDesignedDirectory(directory, user, mongoClient);
                }

                GlobalConfig.getStaticInstance().load();
                
                AppEvents.onGlobalConfigUpdate.invoke();
                
                return true;
            });
        } catch (Exception exception)
        {
            return false;
        }
    }

    private boolean pullDesignedDirectory(File directory, User user, MongoClient mongoClient)
    {
        if (directory.isFile())
        {
            System.err.println("Only directories should be pulled.");
            return false;
        }

        // Pull the directory from the db
        MongoDatabase database = mongoClient.getDatabase(MongoDBManager.database);

        GridFSBucket gridFSBucket = GridFSBuckets.create(database, user.userName());

        // Find the files with the customId and delete it
        GridFSFindIterable gridFSIterator = gridFSBucket.find(
                Filters.eq("metadata.directory", directory.getName()));

        if (gridFSIterator.first() == null)
        {
            System.out.println("No files found to pull in the directory: " + directory);
            return false;
        }

        for (var variable : directory.listFiles())
        {
            variable.delete();
            System.out.println("Deleted directory before pulling in the directory: " + variable);
        }

        for (GridFSFile gridFSFile : gridFSIterator)
        {
            File newFile = new File(directory.getAbsolutePath() + "/" + gridFSFile.getFilename());
            pullFile(newFile, gridFSBucket, gridFSFile.getObjectId());
        }

        return true;
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
}

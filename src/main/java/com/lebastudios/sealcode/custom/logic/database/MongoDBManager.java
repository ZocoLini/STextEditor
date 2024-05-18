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
    
    private MongoDBManager() {}
    
    private static final String hostname = "vzy.h.filess.io";
    private static final String database = "SealCodeSettingsSync_badacross";
    private static final String port = "27018";
    private static final String username = "SealCodeSettingsSync_badacross";
    private static final String password = "a66843df497ecff0a235387d544043a5ba796ba1";

    @Override
    public boolean connect(Function<MongoClient, Boolean> function)
    {
        try (MongoClient connection = MongoClients.create(
                String.format("mongodb://%s:%s@%s:%s/%s", username, password, hostname, port, database)))
        {
            if (function != null) return function.apply(connection);
            return true;
        }
        catch (Exception exception)
        {
            return false;
        }
    }
    
    public boolean pushUserFiles()
    {
        if (!LogInUser.isAnyAccountConnected()) return false;
        
        User user = User.Deserialize();
        if (user == null) return false;
        
        // Push the files into the db
        File highlightingRules = new File(FilePaths.getProgLangSyntaxDirectory());
        File config = new File(FilePaths.getGlobalConfigDirectory());
        
        try
        {
            new Thread(() -> pushDirectory(highlightingRules)).start();
            new Thread(() -> pushDirectory(config)).start();
        }
        catch (Exception exception)
        {
            return false;
        }
        
        return true;
    }
    
    private boolean pushDirectory(File file)
    {
        if (file.isFile()) 
        {
            return pushFile(file);
        }
        
        for (File f : file.listFiles())
        {
            pushDirectory(f);
        }
        
        return true;
    }
    
    private boolean pushFile(File file)
    {
        if (file.isDirectory()) 
        {
            return pushDirectory(file);
        }
        
        // Push the file to the db
        return connect(mongoClient ->
        {
            MongoDatabase database = mongoClient.getDatabase(MongoDBManager.database);

            // Create a gridFSBucket using the default bucket name "fs"
            GridFSBucket gridFSBucket = GridFSBuckets.create(database);

            try (InputStream streamToUploadFrom = new FileInputStream(file)) {
                // Create some custom options
                String customId = User.Deserialize().userName() 
                        + "::" + file.getParentFile().getName() 
                        + "::" + file.getName();
                
                GridFSUploadOptions options = new GridFSUploadOptions()
                        .chunkSizeBytes(1024).metadata(new Document("customID", customId)
                                .append("directory", file.getParentFile().getName()));

                // Find the file with the customId and delete it
                GridFSFile gridFSFile = gridFSBucket.find(Filters.eq("metadata.customID", customId)).first();
                if (gridFSFile != null) {
                    gridFSBucket.delete(gridFSFile.getObjectId());
                }
                
                gridFSBucket.uploadFromStream(file.getName(), streamToUploadFrom, options);
                System.out.println("Succesfuly pushed file: " + file);
            } catch (IOException e) {
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
        
        // Pull the files from the db
        File highlightingRules = new File(FilePaths.getProgLangSyntaxDirectory());
        File config = new File(FilePaths.getGlobalConfigDirectory());
        
        try
        {
            new Thread(() -> pullDirectory(highlightingRules)).start();
            new Thread(() -> pullDirectory(config)).start();
        }
        catch (Exception exception)
        {
            return false;
        }
        
        return true;
    }
    
    private boolean pullDirectory(File file)
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
            
            // Find the file with the customId and delete it
            GridFSFindIterable gridFSIterator = gridFSBucket.find(
                    Filters.eq("metadata.directory", file.getName()));
            
            for (var variable : file.listFiles())
            {
                variable.delete();
                System.out.println("Deleted file before pulling in the directory: " + variable);
            }
            
            for (GridFSFile gridFSFile : gridFSIterator)
            {
                File newFile = new File(file.getAbsolutePath() + "/" + gridFSFile.getFilename());
                if (!pullFile(newFile, gridFSBucket, gridFSFile.getObjectId()))
                {
                    System.out.println("Error while pulling file: " + newFile);
                }
            }
            
            return true;
        });
    }
    
    private boolean pullFile(File file, GridFSBucket gridFSBucket, ObjectId fileId)
    {
        try (OutputStream streamToDownloadTo = new FileOutputStream(file)) {
            
            gridFSBucket.downloadToStream(fileId, streamToDownloadTo);
            System.out.println("Succesfuly pulled file: " + file);
        } catch (IOException e) {
            System.err.println("Error while downloading file: " + file.getName());
            e.printStackTrace();
            return false;
        }
        
        return true;
    }
}

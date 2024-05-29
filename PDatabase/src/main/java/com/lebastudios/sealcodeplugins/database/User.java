package com.lebastudios.sealcodeplugins.database;

import com.lebastudios.sealcode.controllers.MainStageController;
import com.lebastudios.sealcode.frontend.Notification;
import com.lebastudios.sealcode.MessageType;

import java.io.*;

public record User(String userName, String password) implements Serializable
{
    private static final String USER_FILE = MainDBManager.DATABASE_FOLDER + "/user.dat";
    
    public void Serialize()
    {
        File file = new File(MainDBManager.DATABASE_FOLDER);
        
        if (!file.exists()) file.mkdirs();
        
        try (ObjectOutputStream stream = new ObjectOutputStream(new FileOutputStream(USER_FILE)))
        {
            stream.writeObject(this);
        }
        catch (Exception exception)
        {
            MainStageController.getInstance().addNotification(new Notification(
                    "Error saving user data",
                    MessageType.Error
            ));
        }
    }
    
    public static User Deserialize()
    {
        try (ObjectInputStream stream = new ObjectInputStream(new FileInputStream(USER_FILE)))
        {
            return (User) stream.readObject();
        }
        catch (Exception exception)
        {
            Delete();
            return null;
        }
    }
    
    public static void Delete()
    {
        File file = new File(USER_FILE);
        
        if (file.exists()) file.delete();
    }
}
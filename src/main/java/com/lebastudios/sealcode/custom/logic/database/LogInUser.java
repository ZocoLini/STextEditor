package com.lebastudios.sealcode.custom.logic.database;

import at.favre.lib.crypto.bcrypt.BCrypt;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

class LogInUser
{
    public static boolean isAnyAccountConnected()
    {
        User user = User.Deserialize();
        
        if (user == null) return false;

        return MainDBConnection.getInstance().connect(connection ->
        {
            String sql = "select userPass from User where userName = ? and userPass = ?";

            try (PreparedStatement statement = connection.prepareStatement(sql))
            {
                statement.setString(1, user.userName());
                statement.setString(2, user.password());

                try (ResultSet result = statement.executeQuery();)
                {
                    return result.next();
                } catch (Exception exception)
                {
                    return false;
                }
            } catch (Exception exception)
            {
                return false;
            }
        });
    }

    public static void logOut()
    {
        User.Delete();
    }
    
    public static boolean logIn(String user, String passwordNotEncrypted)
    {
        return MainDBConnection.getInstance().connect(connection ->
        {
            String sql = "select userPass from User where userName = ?";

            try (PreparedStatement statement = connection.prepareStatement(sql))
            {
                statement.setString(1, user);

                try (ResultSet result = statement.executeQuery();)
                {
                    if (result.next())
                    {
                        String password = result.getString("userPass");
                        
                        if (BCrypt.verifyer().verify(passwordNotEncrypted.toCharArray(), password.toCharArray()).verified) 
                        {
                            new User(user, password).Serialize();
                            return true;
                        }
                    }

                    return false;
                } catch (Exception exception)
                {
                    return false;
                }
            } catch (Exception exception)
            {
                return false;
            }
        });
    }

    public static boolean register(String user, String passwordNotEncrypted)
    {
        return MainDBConnection.getInstance().connect(connection ->
        {
            String sql = "insert into User (userName, userPass) values (?, ?)";

            try (PreparedStatement statement = connection.prepareStatement(sql))
            {
                statement.setString(1, user);
                statement.setString(2, BCrypt.withDefaults().hashToString(12, passwordNotEncrypted.toCharArray()));

                return statement.executeUpdate() > 0;

            } catch (Exception exception)
            {
                return false;
            }
        });
    }
}

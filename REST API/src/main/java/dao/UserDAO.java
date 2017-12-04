package dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.PreparedStatement;

import model.User;
import weatherBoard.Response;

public abstract class UserDAO extends MySQLAccess{
	private static byte id;

    // Checks whether a user is in the users table
    public static boolean existsUser(String userName) throws Exception {
    	try {
	    	connectToDB();
	        statement = (PreparedStatement) connect.prepareStatement("select id from users where upper(name) = ?");
	        statement.setString(1, userName.toUpperCase());
	        try {
	        	resultSet = statement.executeQuery();
	        	if(resultSet.next()) {
	        		id =  resultSet.getByte("id");
	        		return true;
	        	}else {
	        		return false;
	        	}
	        } catch (SQLException e) {
	        	throw e;
	        }
	    } catch (Exception e) {
	        throw e;
	    } finally {
	        close();
	    }
    }
    // Inserts a new user into users table
    public static Response addUser(String userName) {
    	try {
    		User user;
    		String query = "";
	    	connectToDB();
	        statement = (PreparedStatement) connect.prepareStatement("{ call sp_add_user (?) }");
	        statement.setString(1, userName.toUpperCase());

            try {
	            statement.executeUpdate();
	            query = "select * from users where name = ?;";
	            statement = (PreparedStatement) connect.prepareStatement(query);
	            statement.setString(1, userName.toUpperCase());

	            ResultSet rs = statement.executeQuery();
	            if(!rs.next()) {
	            	return new Response("warn", "There was a problem trying to create the user.");
	            }else {
	            	user = new User(rs.getString("name"));
	            	user.setId((rs.getByte("id")));
	            	return new Response("success", user);
	            }          	

            } catch (SQLException e) {
            	return new Response("error", e.getMessage());
            }
	    } catch (Exception e) {
        	return new Response("error", e.getMessage());
	    } finally {
	        close();
	    }
    }
    // Tries to make a login for the given user
    public static Response login(String userName) {
    	try {
	    	if(!existsUser(userName)) {	    		
	            return addUser(userName);
	        }else {
	        	return new Response("success", id);
	        } 
	    } catch (Exception e) {
        	return new Response("error", e.getMessage());
	    } finally {
	        close();
	    }
    }
    
}

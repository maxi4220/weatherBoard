package weatherBoard;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.PreparedStatement;

public abstract class BoardModel extends MySQLAccess{

    // Inserts into the table boards_cities the list of idCity the user selected for a specific board
    public static Response addCitiesToBoard(byte idBoard, byte[] cities) throws Exception {
        try {
        	String query = "";
        	List<City> resultCities = new ArrayList<City>();
        	City city;
        	byte aux = 1;
        	
        	if(!existsBoard(idBoard)) {
        		return new Response("warn", "The idBoard " + idBoard + " does not exist.");
        	} else {
        		connectToDB();
        		query = "select * from cities where id in (";
	            for(byte idCity : cities) {
	
	            	query += "?,"; 
	            	
	            	// Gets the cities by idboard
		            statement = (PreparedStatement) connect.prepareStatement("{call sp_add_cities_to_board(?,?)}");
		            statement.setByte(1, idBoard);
		            statement.setByte(2, idCity);

		            try {
			            statement.executeUpdate();
		            } catch (SQLException e) {
		            	return new Response("error", e.getMessage());
		            }
		        }
	            //
	            query = query.substring(0, query.length() - 1);
	            query += ");";
	            
	            statement = (PreparedStatement) connect.prepareStatement(query);

	            for(byte idCity : cities) {
	            	statement.setByte(aux++, idCity);
	            }
	            try {
		            ResultSet rs = statement.executeQuery();
		            while(rs.next()) {
		            	city = new City(
		            			rs.getByte("id"),
		            			rs.getString("woeid"),
		            			rs.getString("name"),
		            			rs.getByte("humidity"),
		            			rs.getBigDecimal("pressure"),
		            			rs.getBigDecimal("temp"),
		            			rs.getString("text")
		            			);
		            	resultCities.add(city);
		            }
	            } catch (SQLException e) {
	            	return new Response("error", e.getMessage());
	            }
	        	return new Response("success", resultCities);
        	}
            
        } catch (Exception e) {
        	return new Response("error", e.getMessage());
        } finally {
            close();
        }
    }    
    // Deletes from the table boards_cities the list of idCity the user selected for a specific board
    public static Response removeCitiesFromBoard(byte idBoard, byte[] cities) throws Exception {
        try {
        	if(!existsBoard(idBoard)) {
        		return new Response("warn", "The idBoard " + idBoard + " does not exist.");
        	} else {
	        	connectToDB();
	            for(byte idCity : cities) {
	            	// Gets the cities by idboard
		            statement = (PreparedStatement) connect.prepareStatement("{call sp_remove_cities_from_board(?,?)}");
		            statement.setByte(1, idBoard);
		            statement.setByte(2, idCity);

		            try {
			            statement.executeUpdate();
		            } catch (SQLException e) {
		            	return new Response("error", e.getMessage());
		            }
		        }
	        	return new Response("success", "");
        	}
        } catch (Exception e) {
        	return new Response("error", e.getMessage());
        } finally {
            close();
        }
    }
    // Deletes from the table boards_cities the list of idCity the user selected for a specific board
    public static Response getBoardsByUserName(String userName) throws Exception {
        try {
        	
        	String query = "";
        	List<Byte> arrRes = new ArrayList<Byte>();

        	// Checks if user sent exists in the users table
        	if(!UserModel.existsUser(userName)) {
        		return new Response("warn", UserModel.USER_NOT_FOUND);
        	} else {
        		// Creates a connection to the DB
	        	connectToDB();
	        	
	        	query =  "select b.id from boards b ";
	        	query += "	inner join users u on b.iduser = u.id ";
	        	query += "where upper(u.name) = ?";
	            statement = (PreparedStatement) connect.prepareStatement(query);
	            statement.setString(1, userName.toUpperCase());

	            try {
	            	resultSet = statement.executeQuery();

            		while(resultSet.next()) {
		            	arrRes.add(resultSet.getByte("id"));
		            }
	            	
	            	return new Response("success", arrRes);

	            } catch (SQLException e) {
	            	return new Response("error", e.getMessage());
	            }
        	}
        } catch (Exception e) {
        	return new Response("error", e.getMessage());
        } finally {
            close();
        }
    }
    // Checks whether an idBoard is in the boards table
    private static boolean existsBoard(byte idBoard) throws Exception {

    	try {
	    	connectToDB();
	        statement = (PreparedStatement) connect.prepareStatement("select count(*) qty from boards where id = ?");
	        statement.setByte(1, idBoard);
	        try {
	        	resultSet = statement.executeQuery();
	        	resultSet.next();
	        	if(resultSet.getByte("qty") > 0) {
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
    // Inserts a new board into boards table
    public static Response addBoard(String userName) {
    	try {
	    	connectToDB();

	        statement = (PreparedStatement) connect.prepareStatement("{ call sp_add_board (?) }");
	        statement.setString(1, userName.toUpperCase());

            try {
	            int rowsAffected = statement.executeUpdate();
	            if(rowsAffected==0) {
	            	return new Response("warn", "It was not possible to create the board.");
	            }else {
	                return new Response("success", "");
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
}

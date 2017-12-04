package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.PreparedStatement;

import model.Board;
import weatherBoard.Response;

public abstract class BoardDAO extends MySQLAccess{

    // Deletes from the table boards_cities the list of idCity the user selected for a specific board
    public static Response removeCitiesFromBoard(byte idBoard, byte idCity) throws Exception {
        try {
        	if(!existsBoard(idBoard)) {
        		return new Response("warn", "The idBoard " + idBoard + " does not exist.");
        	} else {
	        	connectToDB();
	            
            	// Gets the cities by idboard
	            statement = (PreparedStatement) connect.prepareStatement("{call sp_remove_cities_from_board(?,?)}");
	            statement.setByte(1, idBoard);
	            statement.setByte(2, idCity);

	            try {
		            statement.executeUpdate();
	            } catch (SQLException e) {
	            	return new Response("error", e.getMessage());
	            }
	        
	        	return new Response("success", "");
        	}
        } catch (Exception e) {
        	return new Response("error", e.getMessage());
        } finally {
            close();
        }
    }
    // Gets all boards by user name
    public static Response getBoardsByUserName(String userName) throws Exception {
        try {
        	
        	String query = "";
        	List<Board> boards = new ArrayList<Board>();
        	Board board;

        	// Checks if user sent exists in the users table
        	if(!UserDAO.existsUser(userName)) {
        		return new Response("warn", "The user does not exist");
        	} else {
        		// Creates a connection to the DB
	        	connectToDB();
	        	
	        	query =  "select b.id, b.name from boards b ";
	        	query += "	inner join users u on b.iduser = u.id ";
	        	query += "where upper(u.name) = ?";
	            statement = (PreparedStatement) connect.prepareStatement(query);
	            statement.setString(1, userName.toUpperCase());

	            try {
	            	resultSet = statement.executeQuery();

            		while(resultSet.next()) {
            			board = new Board();
            			board.setId(resultSet.getByte("id"));
            			board.setName(resultSet.getString("name"));
		            	boards.add(board);
		            }
	            	
	            	return new Response("success", boards);

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
    public static boolean existsBoard(byte idBoard) throws Exception {

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
    		String query = "";
    		Board board;
	    	connectToDB();

	        statement = (PreparedStatement) connect.prepareStatement("{ call sp_add_board (?) }");
	        statement.setString(1, userName.toUpperCase());

            try {
	            int rowsAffected = statement.executeUpdate();
	            if(rowsAffected==0) {
	            	return new Response("warn", "It was not possible to create the board.");
	            }else {
	                
	            	query = "select b.id, b.name from boards b";
	            	query += " inner join users u on b.iduser = u.id ";
	            	query +="where upper(u.name) = ? ";
	            	query += " and b.id = (select max(b.id) from boards)";
		            statement = (PreparedStatement) connect.prepareStatement(query);
		            statement.setString(1, userName.toUpperCase());

		            ResultSet rs = statement.executeQuery();
		            if(!rs.next()) {
		            	return new Response("warn", "There was a problem trying to create the user.");
		            }else {
		            	board = new Board();
		            	board.setId(rs.getByte("id"));
		            	board.setName(rs.getString("name"));
		            	return new Response("success", board);
		            }
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
    // Changes the name of a board
    public static Response changeBoardName(Board board) {
    	try {
	    	connectToDB();

	        statement = (PreparedStatement) connect.prepareStatement("{ call sp_change_board_name (?, ?) }");
	        statement.setByte(1, board.getId());
	        statement.setString(2, board.getName());

            try {
            	statement.execute();
	            return new Response("success", "");
	            
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

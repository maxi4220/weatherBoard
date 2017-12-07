package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.PreparedStatement;

import model.City;
import weatherBoard.Response;

public abstract class CityDAO extends MySQLAccess{

    // Inserts into the table boards_cities the list of idCity the user selected for a specific board
    public static Response addCitiesToBoard(Long idBoard, City[] cities) throws Exception {
        try {
        	String query = "";
        	List<City> resultCities = new ArrayList<City>();
        	City auxCity;
        	int aux = 1;
        	
        	if(!BoardDAO.existsBoard(idBoard)) {
        		return new Response("warn", "The idBoard " + idBoard + " does not exist.");
        	} else {
        		connectToDB();
        		query = "select * from cities where id in (";
	            for(City city : cities) {
	
	            	query += "?,"; 
	            	
	            	// Gets the cities by idboard
		            statement = (PreparedStatement) connect.prepareStatement("{call sp_add_cities_to_board(?,?)}");
		            statement.setLong(1, idBoard);
		            statement.setLong(2, city.getId());

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

	            for(City city : cities) {
	            	statement.setLong(aux++, city.getId());
	            }
	            try {
		            ResultSet rs = statement.executeQuery();
		            while(rs.next()) {
		            	auxCity = new City(
		            			rs.getLong("id"),
		            			rs.getString("woeid"),
		            			rs.getString("name"),
		            			rs.getLong("humidity"),
		            			rs.getBigDecimal("pressure"),
		            			rs.getBigDecimal("temp"),
		            			rs.getString("text")
		            			);
		            	resultCities.add(auxCity);
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
    
    // Returns a list of cities filtered by idBoard
    public static Response getCitiesByIdBoard(Long idBoard) {
        try {
        	List<City> cities = new ArrayList<City>();
        	City city;
            
        	connectToDB();
            
            // We get the cities by idboard
            if(idBoard > 0) {
	            statement = (PreparedStatement) connect.prepareStatement("select * from weatherBoard.cities c inner join weatherBoard.boards_cities bc on c.id = bc.idcity where bc.idboard = ?");
	            statement.setLong(1, idBoard);
            } else {
            	statement = (PreparedStatement) connect.prepareStatement("select * from weatherBoard.cities");
            }
            resultSet = statement.executeQuery();
            
            // Loops the resultset, creates a City object for each record and adds it to the cities Array 
            while(resultSet.next()) {
            	city = new City(
            			resultSet.getLong("id"),
            			resultSet.getString("woeid"),
            			resultSet.getString("name"),
            			resultSet.getLong("humidity"),
            			resultSet.getBigDecimal("pressure"),
            			resultSet.getBigDecimal("temp"),
            			resultSet.getString("text")
            			);
            	cities.add(city);
            }

            return new Response("success", cities);

        } catch (Exception e) {
        	return new Response("error", e.getMessage());
        } finally {
            close();
        }

    }

}

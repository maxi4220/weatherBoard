package weatherBoard;

import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.PreparedStatement;

public class CityModel extends MySQLAccess{

    // Returns a list of cities filtered by idBoard
    public static Response getCitiesByIdBoard(byte idBoard) {
        try {
        	List<City> cities = new ArrayList<City>();
        	City city;
            
        	connectToDB();
            
            // We get the cities by idboard
            if(idBoard > 0) {
	            statement = (PreparedStatement) connect.prepareStatement("select * from weatherBoard.cities c inner join weatherBoard.boards_cities bc on c.id = bc.idcity where bc.idboard = ?");
	            statement.setByte(1, idBoard);
            } else {
            	statement = (PreparedStatement) connect.prepareStatement("select * from weatherBoard.cities");
            }
            resultSet = statement.executeQuery();
            
            // Loops the resultset, creates a City object for each record and adds it to the cities Array 
            while(resultSet.next()) {
            	city = new City(
            			resultSet.getByte("id"),
            			resultSet.getString("woeid"),
            			resultSet.getString("name"),
            			resultSet.getByte("humidity"),
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

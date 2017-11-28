/*
	Description: Representation of a board
	History: Class created: 11/18/2017 - Maximiliano Pozzi
*/
package weatherBoard;

import java.util.List;

public final class Board{

	private byte 		id;
	private byte 		iduser;
	private List<City> 	cities;
	
    public Board(
    		byte 		iduser,
    		List<City>	cities) {
    	this.iduser = iduser;
    	this.cities = cities;
    }

	public byte getId() {
		return id;
	}

	public void setId(byte id) {
		this.id = id;
	}

	public byte getIduser() {
		return iduser;
	}

	public void setIduser(byte iduser) {
		this.iduser = iduser;
	}

	public List<City> getCities() {
		return cities;
	}

	public void setCities(List<City> cities) {
		this.cities = cities;
	}

	public void addCity(City city) {
		this.cities.add(city);
	}
	
	public void removeCity(City city) {
		this.cities.remove(city);
	}

}
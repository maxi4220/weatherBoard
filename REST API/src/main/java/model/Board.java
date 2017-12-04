/*
	Description: Representation of a board
	History: Class created: 11/18/2017 - Maximiliano Pozzi
*/
package model;

import java.util.List;

public final class Board{

	private byte 		id;
	private byte 		iduser;
	private List<City> 	cities;
	private String		name;
	
    public Board() {}

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
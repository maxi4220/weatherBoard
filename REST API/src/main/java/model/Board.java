/*
	Description: Representation of a board
	History: Class created: 11/18/2017 - Maximiliano Pozzi
*/
package model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "boards")
public final class Board{

	private Long 		id;
	private Long 		iduser;
	private String		name;
	
	private List<City> 	cities;

    public Board() {}
    
    public Board(List<City> cities) {
    	this.cities =  cities;
    }
    public Board(Long iduser, String name) {
    	this.iduser = iduser;
    	this.name = name;
    }
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIduser() {
		return iduser;
	}

	public void setIduser(Long iduser) {
		this.iduser = iduser;
	}

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "boards_cities", joinColumns = @JoinColumn(name = "id_board", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "id_city", referencedColumnName = "id"))
	@JsonManagedReference
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
	
	public boolean isInCities(City city) {
		if(this.cities.contains(city)) {
			return true;			
		} else {
			return false;
		}
		
	}
	
}
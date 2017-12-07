/*
	Description: Model class representing a record of Cities table
	History: Class created: 11/17/2017 - Maximiliano Pozzi
*/
package model;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;


@Entity
@Table(name = "cities")
public final class City {


	private Long id;
	private String woeid;
	private String name;
	private Long humidity;
	private BigDecimal pressure;
	private BigDecimal temp;
	private String text;

	private List<Board> boards;

	public City() {}
	
    public City(
    		Long 		id,
    		String 		woeid,
    		String 		name,
    		Long 		humidity,
    		BigDecimal 	pressure,
    		BigDecimal 	temp,
    		String 		text) {
    	this.setId(id);
    	this.setWoeid(woeid);
    	this.setName(name);
    	this.setHumidity(humidity);
    	this.setPressure(pressure);
    	this.setTemp(temp);
    	this.setText(text);    	
    }
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getWoeid() {
		return woeid;
	}

	public void setWoeid(String woeid) {
		this.woeid = woeid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getHumidity() {
		return humidity;
	}

	public void setHumidity(Long humidity) {
		this.humidity = humidity;
	}

	public BigDecimal getPressure() {
		return pressure;
	}

	public void setPressure(BigDecimal pressure) {
		this.pressure = pressure;
	}

	public BigDecimal getTemp() {
		return temp;
	}

	public void setTemp(BigDecimal temp) {
		this.temp = temp;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
	@ManyToMany(mappedBy = "cities")
	@JsonBackReference
	public List<Board> getBoards() {
		return boards;
	}
	public void setBoards(List<Board> boards) {
		this.boards = boards;
	}

}
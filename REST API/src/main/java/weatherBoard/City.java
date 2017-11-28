/*
	Description: Model class representing a record of Cities table
	History: Class created: 11/17/2017 - Maximiliano Pozzi
*/
package weatherBoard;

import java.math.BigDecimal;

public final class City {

	private byte id;
	private String woeid;
	private String name;
	private byte humidity;
	private BigDecimal pressure;
	private BigDecimal temp;
	private String text;

    public City(
    		byte 		id,
    		String 		woeid,
    		String 		name,
    		byte 		humidity,
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

	public byte getId() {
		return id;
	}

	public void setId(byte id) {
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

	public byte getHumidity() {
		return humidity;
	}

	public void setHumidity(byte humidity) {
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

}
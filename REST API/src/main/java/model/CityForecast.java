package model;

public class CityForecast {
	private String name;
	private Integer minTemp;
	private Integer maxTemp;
	private String text;
	
	public CityForecast() {};
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getMinTemp() {
		return minTemp;
	}
	public void setMinTemp(Integer minTemp) {
		this.minTemp = minTemp;
	}
	public Integer getMaxTemp() {
		return maxTemp;
	}
	public void setMaxTemp(Integer maxTemp) {
		this.maxTemp = maxTemp;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	
}

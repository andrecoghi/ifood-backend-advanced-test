package br.com.ifood.domain.openweather;


import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OpenWeather implements Serializable {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public OpenWeather() {
		super();
	}

	public OpenWeather(String id, String dt, Clouds clouds, Coord coord, Wind wind, String cod, String visibility,
			Sys sys, String name, String base, Weather[] weather, Main main) {
		this();
		this.id = id;
		this.dt = dt;
		this.clouds = clouds;
		this.coord = coord;
		this.wind = wind;
		this.cod = cod;
		this.visibility = visibility;
		this.sys = sys;
		this.name = name;
		this.base = base;
		this.weather = weather;
		this.main = main;
	}

	private String id;
	private String dt;
	private Clouds clouds;
	private Coord coord;
	private Wind wind;
	private String cod;
	private String visibility;
	private Sys sys;
	private String name;
	private String base;
	private Weather[] weather;
	private Main main;

	// GETTERS AND SETTERS
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDt() {
		return dt;
	}

	public void setDt(String dt) {
		this.dt = dt;
	}

	public Clouds getClouds() {
		return clouds;
	}

	public void setClouds(Clouds clouds) {
		this.clouds = clouds;
	}

	public Coord getCoord() {
		return coord;
	}

	public void setCoord(Coord coord) {
		this.coord = coord;
	}

	public Wind getWind() {
		return wind;
	}

	public void setWind(Wind wind) {
		this.wind = wind;
	}

	public String getCod() {
		return cod;
	}

	public void setCod(String cod) {
		this.cod = cod;
	}

	public String getVisibility() {
		return visibility;
	}

	public void setVisibility(String visibility) {
		this.visibility = visibility;
	}

	public Sys getSys() {
		return sys;
	}

	public void setSys(Sys sys) {
		this.sys = sys;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBase() {
		return base;
	}

	public void setBase(String base) {
		this.base = base;
	}

	public Weather[] getWeather() {
		return weather;
	}

	public void setWeather(Weather[] weather) {
		this.weather = weather;
	}

	public Main getMain() {
		return main;
	}

	public void setMain(Main main) {
		this.main = main;
	}

	
	
}


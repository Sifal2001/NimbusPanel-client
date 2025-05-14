package com.Client.WeatherCast;

import java.util.List;

public class HourlyCast {
    private long dt;
    private double temp;
    private double feels_like;
    private int humidity;
    private List<WeatherCondition> weather;
    
	public HourlyCast() {
		
	}
	
	public HourlyCast( long dt, double temp, double feels_like, int humidity, List<WeatherCondition> weather) {
		this.dt = dt;
		this.temp = temp;
		this.feels_like = feels_like;
		this.humidity = humidity;
		this.weather = weather;
	}

	public long getDt() {
		return dt;
	}

	public void setDt(long dt) {
		this.dt = dt;
	}

	public double getTemp() {
		return temp;
	}

	public void setTemp(double temp) {
		this.temp = temp;
	}

	public double getFeels_like() {
		return feels_like;
	}

	public void setFeels_like(double feels_like) {
		this.feels_like = feels_like;
	}

	public int getHumidity() {
		return humidity;
	}

	public void setHumidity(int humidity) {
		this.humidity = humidity;
	}

	public List<WeatherCondition> getWeather() {
		return weather;
	}

	public void setWeather(List<WeatherCondition> weather) {
		this.weather = weather;
	}

	@Override
	public String toString() {
		return "HourlyWeather [dt=" + dt + ", temp=" + temp + ", feels_like=" + feels_like + ", humidity=" + humidity
				+ ", weather=" + weather + "]";
	}

}

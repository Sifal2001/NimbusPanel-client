package com.Client.WeatherCast;

import java.util.List;

public class CurrentCast {
    private long dt;
    private long sunrise;
    private long sunset;
    private double temp;
    private double feels_like;
    private int pressure;
    private int humidity;
    private double dew_point;
    private double uvi;
    private int clouds;
    private int visibility;
    private double wind_speed;
    private int wind_deg;
    private double wind_gust;
    private List<WeatherCondition> weather;
    
	public CurrentCast() {
		
	}
	
	public CurrentCast( long dt, long sunrise, long sunset, double temp, double feels_like, int pressure,
			int humidity, double dew_point, double uvi, int clouds, int visibility, double wind_speed, int wind_deg,
			double wind_gust, List<WeatherCondition> weather, WeatherCast weatherCast) {
		this.dt = dt;
		this.sunrise = sunrise;
		this.sunset = sunset;
		this.temp = temp;
		this.feels_like = feels_like;
		this.pressure = pressure;
		this.humidity = humidity;
		this.dew_point = dew_point;
		this.uvi = uvi;
		this.clouds = clouds;
		this.visibility = visibility;
		this.wind_speed = wind_speed;
		this.wind_deg = wind_deg;
		this.wind_gust = wind_gust;
		this.weather = weather;
	}

	public long getDt() {
		return dt;
	}

	public void setDt(long dt) {
		this.dt = dt;
	}

	public long getSunrise() {
		return sunrise;
	}

	public void setSunrise(long sunrise) {
		this.sunrise = sunrise;
	}

	public long getSunset() {
		return sunset;
	}

	public void setSunset(long sunset) {
		this.sunset = sunset;
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

	public int getPressure() {
		return pressure;
	}

	public void setPressure(int pressure) {
		this.pressure = pressure;
	}

	public int getHumidity() {
		return humidity;
	}

	public void setHumidity(int humidity) {
		this.humidity = humidity;
	}

	public double getDew_point() {
		return dew_point;
	}

	public void setDew_point(double dew_point) {
		this.dew_point = dew_point;
	}

	public double getUvi() {
		return uvi;
	}

	public void setUvi(double uvi) {
		this.uvi = uvi;
	}

	public int getClouds() {
		return clouds;
	}

	public void setClouds(int clouds) {
		this.clouds = clouds;
	}

	public int getVisibility() {
		return visibility;
	}

	public void setVisibility(int visibility) {
		this.visibility = visibility;
	}

	public double getWind_speed() {
		return wind_speed;
	}

	public void setWind_speed(double wind_speed) {
		this.wind_speed = wind_speed;
	}

	public int getWind_deg() {
		return wind_deg;
	}

	public void setWind_deg(int wind_deg) {
		this.wind_deg = wind_deg;
	}

	public double getWind_gust() {
		return wind_gust;
	}

	public void setWind_gust(double wind_gust) {
		this.wind_gust = wind_gust;
	}

	public List<WeatherCondition> getWeather() {
		return weather;
	}

	public void setWeather(List<WeatherCondition> weather) {
		this.weather = weather;
	}

	@Override
	public String toString() {
		return "CurrentWeather [ dt=" + dt + ", sunrise=" + sunrise + ", sunset=" + sunset + ", temp="
				+ temp + ", feels_like=" + feels_like + ", pressure=" + pressure + ", humidity=" + humidity
				+ ", dew_point=" + dew_point + ", uvi=" + uvi + ", clouds=" + clouds + ", visibility=" + visibility
				+ ", wind_speed=" + wind_speed + ", wind_deg=" + wind_deg + ", wind_gust=" + wind_gust + ", weather="
				+ weather + "]";
	}
	
	
	
}

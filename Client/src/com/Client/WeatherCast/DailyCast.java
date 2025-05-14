package com.Client.WeatherCast;

import java.util.List;


public class DailyCast {
    private long dt;
    private long sunrise;
    private long sunset;
    private long moonrise;
    private long moonset;
    private double moon_phase;
    private Temperature temp;
    private Temperature feels_like;
    private int pressure;
    private int humidity;
    private double dew_point;
    private double wind_speed;
    private int wind_deg;
    private double wind_gust;
    private int clouds;
    private double pop;
    private double rain;
    private double uvi;
    private List<WeatherCondition> weather;
	
	public DailyCast() {
		
	}
	
	public DailyCast(long dt, long sunrise, long sunset, long moonrise, long moonset, double moon_phase,
			Temperature temp, Temperature feels_like, int pressure, int humidity, double dew_point, double wind_speed,
			int wind_deg, double wind_gust, List<WeatherCondition> weather, int clouds, double pop, double rain,
			double uvi) {
		this.dt = dt;
		this.sunrise = sunrise;
		this.sunset = sunset;
		this.moonrise = moonrise;
		this.moonset = moonset;
		this.moon_phase = moon_phase;
		this.temp = temp;
		this.feels_like = feels_like;
		this.pressure = pressure;
		this.humidity = humidity;
		this.dew_point = dew_point;
		this.wind_speed = wind_speed;
		this.wind_deg = wind_deg;
		this.wind_gust = wind_gust;
		this.weather = weather;
		this.clouds = clouds;
		this.pop = pop;
		this.rain = rain;
		this.uvi = uvi;
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

	public long getMoonrise() {
		return moonrise;
	}

	public void setMoonrise(long moonrise) {
		this.moonrise = moonrise;
	}

	public long getMoonset() {
		return moonset;
	}

	public void setMoonset(long moonset) {
		this.moonset = moonset;
	}

	public double getMoon_phase() {
		return moon_phase;
	}

	public void setMoon_phase(double moon_phase) {
		this.moon_phase = moon_phase;
	}

	public Temperature getTemp() {
		return temp;
	}

	public void setTemp(Temperature temp) {
		this.temp = temp;
	}

	public Temperature getFeels_like() {
		return feels_like;
	}

	public void setFeels_like(Temperature feels_like) {
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

	public int getClouds() {
		return clouds;
	}

	public void setClouds(int clouds) {
		this.clouds = clouds;
	}

	public double getPop() {
		return pop;
	}

	public void setPop(double pop) {
		this.pop = pop;
	}

	public double getRain() {
		return rain;
	}

	public void setRain(double rain) {
		this.rain = rain;
	}

	public double getUvi() {
		return uvi;
	}

	public void setUvi(double uvi) {
		this.uvi = uvi;
	}

	@Override
	public String toString() {
		return "DailyWeather [dt=" + dt + ", sunrise=" + sunrise + ", sunset=" + sunset + ", moonrise="
				+ moonrise + ", moonset=" + moonset + ", moon_phase=" + moon_phase + ", pressure=" + pressure
				+ ", humidity=" + humidity + ", dew_point=" + dew_point + ", wind_speed=" + wind_speed + ", wind_deg="
				+ wind_deg + ", wind_gust=" + wind_gust + ", weather=" + weather + ", clouds=" + clouds + ", pop=" + pop
				+ ", rain=" + rain + ", uvi=" + uvi + "]";
	}
	
}


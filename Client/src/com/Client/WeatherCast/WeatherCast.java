package com.Client.WeatherCast;

import java.util.List;

public class WeatherCast {

	private CurrentCast current;
	private List<HourlyCast> hourly;
	private List<DailyCast> daily;
	
	public WeatherCast() {
		
	}
	
	public WeatherCast(CurrentCast current, List<HourlyCast> hourly, List<DailyCast> daily) {
		this.current = current;
		this.hourly = hourly;
		this.daily= daily;
		
	}

	public CurrentCast getCurrent() {
		return current;
	}

	public void setCurrent(CurrentCast current) {
		this.current = current;
	}

	public List<HourlyCast> getHourly() {
		return hourly;
	}

	public void setHourly(List<HourlyCast> hourly) {
		this.hourly = hourly;
	}

	public List<DailyCast> getDaily() {
		return daily;
	}

	public void setDaily(List<DailyCast> daily) {
		this.daily = daily;
	}

	@Override
	public String toString() {
		return "WeatherCast [current=" + current + ", hourly=" + hourly + ", daily=" + daily + "]";
	}
	
}

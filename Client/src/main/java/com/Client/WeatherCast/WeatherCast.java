package com.Client.WeatherCast;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class WeatherCast {

	private CurrentCast current;
	private List<HourlyCast> hourly;
	private List<DailyCast> daily;
	@SerializedName("timezone_offset")
	private int timeZoneOffset;
	
	public WeatherCast() {
		
	}
	
	public WeatherCast(CurrentCast current, List<HourlyCast> hourly, List<DailyCast> daily, int timeZoneOffset) {
	    this.current = current;
	    this.hourly = hourly;
	    this.daily = daily;
	    this.timeZoneOffset = timeZoneOffset;
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
	
	public int getTimeZoneOffset() {
	    return timeZoneOffset;
	}

	public void setTimeZoneOffset(int timeZoneOffset) {
	    this.timeZoneOffset = timeZoneOffset;
	}

	@Override
	public String toString() {
		return "WeatherCast [current=" + current + ", hourly=" + hourly + ", daily=" + daily + " timeZoneOffset=" + timeZoneOffset +"]";
	}
	
}

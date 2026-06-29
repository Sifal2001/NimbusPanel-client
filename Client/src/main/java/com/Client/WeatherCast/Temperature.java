package com.Client.WeatherCast;

public class Temperature {
	private Long id;
    private double day;
    private double min;
    private double max;
    private double night;
    private double eve;
    private double morn;
    
    public Temperature() {
    	
    }

	public Temperature(Long id, double day, double min, double max, double night, double eve, double morn) {
		this.id = id;
		this.day = day;
		this.min = min;
		this.max = max;
		this.night = night;
		this.eve = eve;
		this.morn = morn;
	}
	
	public Temperature( double day, double min, double max, double night, double eve, double morn) {
		this.day = day;
		this.min = min;
		this.max = max;
		this.night = night;
		this.eve = eve;
		this.morn = morn;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public double getDay() {
		return day;
	}

	public void setDay(double day) {
		this.day = day;
	}

	public double getMin() {
		return min;
	}

	public void setMin(double min) {
		this.min = min;
	}

	public double getMax() {
		return max;
	}

	public void setMax(double max) {
		this.max = max;
	}

	public double getNight() {
		return night;
	}

	public void setNight(double night) {
		this.night = night;
	}

	public double getEve() {
		return eve;
	}

	public void setEve(double eve) {
		this.eve = eve;
	}

	public double getMorn() {
		return morn;
	}

	public void setMorn(double morn) {
		this.morn = morn;
	}

	@Override
	public String toString() {
		return "Tempurature [id=" + id + ", day=" + day + ", min=" + min + ", max=" + max + ", night=" + night
				+ ", eve=" + eve + ", morn=" + morn + "]";
	}  
}

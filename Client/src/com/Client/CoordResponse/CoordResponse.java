package com.Client.CoordResponse;

public class CoordResponse {
	private Coord coord;
	
	public CoordResponse() {
	}
	
	public CoordResponse( Coord coord) {
		this.coord = coord;
	}

	public Coord getCoord() {
		return coord;
	}

	public void setCoord(Coord coord) {
		this.coord = coord;
	}

	@Override
	public String toString() {
		return "CoordResponse [coord=" + coord + "]";
	}
}

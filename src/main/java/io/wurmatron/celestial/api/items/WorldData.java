package io.wurmatron.celestial.api.items;

public class WorldData {

	private String name;
	private int x;
	private int y;
	private int z;
	private int dim;

	public WorldData (String name,int x,int y,int z,int dim) {
		this.name = name;
		this.x = x;
		this.y = y;
		this.z = z;
		this.dim = dim;
	}

	public String getName () {
		return name;
	}

	public int getX () {
		return x;
	}

	public int getY () {
		return y;
	}

	public int getZ () {
		return z;
	}

	public int getDim () {
		return dim;
	}
}
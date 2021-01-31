package com.study.temperature;

public class Person {
	private String name;
	private String temon;
	private String temunder;
	private String time;
	private String locat;

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTemon() {
		return temon;
	}
	public void setTemon(String temon) {
		this.temon = temon;
	}
	public String getTemunder() {
		return temunder;
	}
	public void setTemunder(String temunder) {
		this.temunder = temunder;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getLocat() {
		return locat;
	}
	public void setLocat(String locat) {
		this.locat = locat;
	}
	@Override
	public String toString() {
		return "Person [name=" + name + ", temon=" + temon + ", temunder=" + temunder + ", time=" + time + ", locat="
				+ locat + "]";
	}

}

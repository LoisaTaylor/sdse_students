package org.nypl.journalsystem;

import org.nypl.journalsystem.core.IPublisher;

public class Publisher implements IPublisher{
	String name;
	String location;
	
	public Publisher(String name, String location){
		this.name = name;
		this.location = location;
	}

	@Override
	public String getLocation() {
		return location;
	}

	@Override
	public String getName() {
		return name;
	}
	
	
}

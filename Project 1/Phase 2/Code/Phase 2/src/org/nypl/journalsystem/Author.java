package org.nypl.journalsystem;

import org.nypl.journalsystem.core.IAuthor;

public class Author implements IAuthor{
	int aid;
	String name;
	
	public Author(int aid, String name){
		this.aid = aid;
		this.name = name;
	}

	@Override
	public String getName() {
		return name;
	}
}
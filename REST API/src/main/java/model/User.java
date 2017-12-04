/*
	Description: Representation of a users record
	History: Class created: 11/24/2017 - Maximiliano Pozzi
*/
package model;

import javax.persistence.*;

@Entity
public final class User{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private byte 		id;
	private String 		name;
	
	public User() {}
	
    public User(
    		String	name) {
    	this.name = name;
    }

	public byte getId() {
		return id;
	}

	public void setId(byte id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
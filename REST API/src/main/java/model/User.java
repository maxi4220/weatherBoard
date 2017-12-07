/*
	Description: Representation of a users record
	History: Class created: 11/24/2017 - Maximiliano Pozzi
*/
package model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "users")
public final class User{
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	private Long 		id;
	private String 		name;
	
	public User() {}
	
    public User(String	name) {
    	this.name = name;
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
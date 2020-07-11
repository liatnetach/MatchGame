package model;
import java.io.Serializable;
public class Children implements Serializable,Model{
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
private String name;
private String id;
public Children(String name) {
	this.name=name;
}
public Children(String n,String id) 
{
	name=n;
	this.id=id;
}
public String getName() {return name;}
public String getId() {return id;}

public void setName(String name) { this.name=name;}
public void setId(String id) { this.id=id;}

}

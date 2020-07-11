package model;
import java.util.*;
import java.io.Serializable;


public class GameRecord implements Serializable,Model{
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
private Children child;
private int score;
private Date datePlayed;
private String gameType;
public GameRecord(Children c,int s,String type) 
{
	child=c;
	score=s;
	datePlayed=Calendar.getInstance().getTime();
	gameType=type;
}
public String getGameType() {return gameType;}
public String getChildName() {return child.getName();}
public String getChildID() {return child.getId();}
public int getScore() {return score;}
public Date getGameDate() {return datePlayed;}

public void setGameType(String gameT) { gameType=gameT;}
public void setChild(Children child) { this.child=child;}
public void setScore(int s) { score=s;}
public void setGameDate(Date dateP) { datePlayed=dateP;}
 
}

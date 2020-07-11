package model;

import java.util.Random;
import java.util.Observable;

import javax.swing.ImageIcon;

public abstract class Game extends Observable implements CardsGame{
	protected ImageIcon[] imagePhoto;
	protected ImageIcon imageCover; //the cover photo that will display on all the closed cards
	protected int[] photoIndex;//each cell represent image index- initialize with 2n cards
	protected boolean[] photoFound;//give indication about the cards - if found in a match already
	protected int photoRemaining;//amount of pictures that left unmatched
	protected int[] score;//index 0 score of player 1, index1 combo of player 1, same goes for player 2
	protected int numOfCards;
	public class ScoreCalc{
		int score;
		public int getScore() {	
			return score;
		}
	}
	ScoreCalc scoreCalc=new ScoreCalc();
	public class PhotoIndex
	{
		public int[] photoIndex;
		public PhotoIndex() {}
		public PhotoIndex(int[] arr) {
			photoIndex=arr;
		}
		public int[] getPhotoIndex() {	
			return photoIndex;
		}
		public void setPhotoIndex(int[] arr) { photoIndex=arr;}

	}
	public void setGame() {	}
	
	public void getRemainingPhotoNum(boolean []photoFound)
	{
		setChanged();
		notifyObservers(photoRemaining);
	}
	
	public void getPhotoFound() {	
		setChanged();
		notifyObservers(photoFound); 
	}
	
	public void checkMatch(int firstI,int secondI,int whosTurn) 
	{
		if(photoIndex[firstI]==photoIndex[secondI])
		{
			//scoreCalc() //UI
			if(whosTurn==1) //Combo increment
				score[1]++;
			else if(whosTurn==2)
				score[3]++;
			photoRemaining--;
			photoFound[firstI]=true;
			photoFound[secondI]=true;
			setChanged();
			notifyObservers(true);		}
		else 
		{
			if(whosTurn==1) //Combo reset
				score[1]=0;
			else if(whosTurn==2)
				score[3]=0;
			setChanged();
			notifyObservers(false);		}
	}
	
	public void nRandomIntegers(int n) 
	{
		
		int[] nIntegers = new int[n];
		int temp, s;
		Random sortRandom = new Random(); //Need to import "java.util.Random"
		
		//Init array from 0 to n-1
		for(int i = 0;i<n;i++)
		{
			nIntegers[i]=i;
		}
		
		//I is the number of items remaining in the list
		for(int i=n;i>=1;i--)
		{
			s = sortRandom.nextInt(i);
			temp = nIntegers[s];
			nIntegers[s] = nIntegers[i-1];
			nIntegers[i-1] = temp;
		}
		for(int i=0;i<n;i++)
			if(nIntegers[i]>(n/2)-1)
				nIntegers[i]-=(n/2);
		photoIndex=nIntegers;
	}
	public void  getImagePhotoArr() 
	{	
		setChanged();
		notifyObservers(imagePhoto);}
	public void getImagecover() 
	{	
		setChanged();
		notifyObservers(imageCover);
	}
	
	public void scoreCalc(int whosTurn) 
	{
		if(whosTurn==1)
		{
			score[0]+= 5*(score[1]);
			scoreCalc.score=score[0];
			setChanged();
			notifyObservers(scoreCalc);		}
		else if(whosTurn==2)
		{
			score[2]+=5*(score[3]);
			scoreCalc.score=score[2];
			setChanged();
			notifyObservers(scoreCalc);
		}
	}
}

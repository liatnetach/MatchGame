package model;

import javax.swing.ImageIcon;


public class AgainstRival extends Game{
	public class RivalGameSettings
	{
		String gameLevel;
		String player1;
		String player2;
		ImageIcon cover=new ImageIcon();
		ImageIcon[] photos=new ImageIcon[12];
		int[] photosIndex;
		public RivalGameSettings(){}
		public RivalGameSettings(String level,String p1,String p2) 
		{
			gameLevel=level;
			player1=p1;
			player2=p2;

		}
		public String getDifficulty() {return gameLevel;}
		public String getP1Name() {return player1;}
		public String getP2Name() {return player2;}
		public ImageIcon getCover() {return cover;}
		public ImageIcon[] getPhotos() {return photos;}
		public int[] getPhotosIndex() {return photosIndex;}

	}
	protected RivalGameSettings gameSettings;
	public AgainstRival() {} 

	public AgainstRival(String p1,String p2,int difficulty) 
	{
		
		score = new int[4];//Initialized with 0`s
		imagePhoto = new ImageIcon[12]; //An array of images used to play the game
		imagePhoto[0] = new ImageIcon("PhotoName1.jpeg");
		imagePhoto[1] = new ImageIcon("PhotoName2.jpeg");
		imagePhoto[2] = new ImageIcon("PhotoName3.jpeg");
		imagePhoto[3] = new ImageIcon("PhotoName4.jpeg");
		imagePhoto[4] = new ImageIcon("PhotoName5.jpeg");
		imagePhoto[5] = new ImageIcon("PhotoName6.jpeg");
		imagePhoto[6] = new ImageIcon("PhotoName7.jpeg");
		imagePhoto[7] = new ImageIcon("PhotoName8.jpeg");
		imagePhoto[8] = new ImageIcon("PhotoName9.jpeg");
		imagePhoto[9] = new ImageIcon("PhotoName10.jpeg");
		imagePhoto[10] = new ImageIcon("PhotoName11.jpeg");
		imagePhoto[11] = new ImageIcon("PhotoName12.jpeg");
		
		//imageCover = new ImageIcon("cover.jpeg");
		imageCover = new ImageIcon("coverLogo2.png");
				
		String level;
		//Difficulty level affects only numOfCards
		if(difficulty==0)
		{
			level="Easy";
			numOfCards=12;
		}
		else if(difficulty==1)
		{
			level="Medium";
			numOfCards=16;
		}
		else 
		{
			level="Hard";
			numOfCards=24;
		}
		gameSettings=new RivalGameSettings(level,p1,p2);
		gameSettings.cover=imageCover;
		gameSettings.photos=imagePhoto;
		gameSettings.photosIndex=new int[numOfCards];
		photoIndex = new int[numOfCards];
		nRandomIntegers(numOfCards);
		gameSettings.photosIndex=photoIndex;

		photoFound = new boolean[numOfCards];
		//photoIndexInner=new PhotoIndex(photoIndex);
	}
	public void setGame() {	
		setChanged();
		notifyObservers(gameSettings);
		}
}

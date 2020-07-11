package view;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.Timer;
import javax.swing.JOptionPane;
import java.util.Observable;
import java.net.URL;
import java.applet.*;

public class GeneralGameBuilder extends Observable implements View//extends JFrame
{
	
	JFrame mainWindow = new JFrame();
	
	
	protected JPanel contentPanel;
	protected JPanel gamePanel;
	protected JPanel resultsPanel;
	protected JLabel[] photoLabel;
	protected JTextField[] scoreTextField; //An array to keep the score of 1 or 2 players
	protected JLabel messageLabel; //Used to separate block in results panel bloc
	protected JLabel player1Label; //Player 1 name
	protected JLabel player2Label; //Player 2 name
	protected JPanel buttonsPanel; //Right lower panel for start/stop and exit buttons
	protected JPanel timerPanel; //Time countdown panel
	protected JButton startStopButton;
	protected JButton exitButton;
	protected boolean isAgainstComputer=false;
	protected ImageIcon cover;
	protected ImageIcon[] photos;
	protected int[] photosIndex;
	protected int[] choice = new int[2]; //Keep players selection
	protected int choiceNumber;
	protected int whosTurn=1;
	protected int photosRemaining;
	protected boolean[] whichPhotosFound;
	protected boolean is2Players;
	protected Color backColor = new Color(230,230,250);
	protected Border playerBorder;
	protected int[] prevChoices;
	private MainScreen ms;
	
	AudioClip matchSound;
	AudioClip noMatchSound;
	AudioClip gameOverSound;
	AudioClip timesUp;
	
	protected int labelSelected;
	protected Timer displayTimer;
	protected int delay=600; //600 ms delay between card flips
	protected boolean isClickable=true;
	
	public class GetImageCover{}
	public class GetPhotosArray{}
	public class GetPhotoIndex{}
	public class CompTurn{}
	public class CompWhosTurn{}
	public class RivalWhosTurn{}
	public class GetScoreCalc
	{
		private int whosTurn;
		public GetScoreCalc() {}
		public GetScoreCalc(int playersTurn) {whosTurn=playersTurn;}
		public int getWhosTurn() {return whosTurn;}
	}
	public class GameDetails {
		
		int score;
		String gametype;
		String name;
		
		public GameDetails() {}
		public GameDetails(String gameType, String pName, int score)
		{
			this.gametype = gameType;
			this.name=pName;
			this.score=score;
		}
		public int getScore() {
			return score;
		}
		public String getGametype() {
			return gametype;
		}
		public String getName() {
			return name;
		}
	}
	public class CheckMatch
	{
		private int firstIndex;
		private int secondIndex;
		private int whosTurn;
		
		public CheckMatch() {}
		public CheckMatch(int first,int second,int whoPlays) {firstIndex=first;secondIndex=second;whosTurn=whoPlays;}
		public int getFirstIndex() {return firstIndex;}
		public int getSecondIndex() {return secondIndex;}
		public int getWhosTurn() {return whosTurn;}
	}
	
	public class GetPhotoFound{}
	public GeneralGameBuilder() {}
	
	public GeneralGameBuilder(Builder bld)
	{
		
		
		cover=new ImageIcon(bld.cover.getDescription());
		ms=bld.ms; //Connecting MainScreen instance to hide during game
		mainWindow.getContentPane().setLayout(new GridBagLayout());
		//getContentPane().setLayout(new GridBagLayout());
		GridBagConstraints gridConstraints;
		mainWindow.pack();
		
		mainWindow.setTitle("משחק פעיל"); //Check later how to place it in the center of the screen
		//mainWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		mainWindow.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				ms.setMainScreenEnabled();
				mainWindow.dispose();
			}
		});
		mainWindow.setBounds(400,100,1100,800);//Setting up app window size and placing in the center of my screen
		mainWindow.getContentPane().setBackground(backColor);
		
		gamePanel = new JPanel();
		
		gamePanel.setPreferredSize(new Dimension(625, 530));
		gamePanel.setLayout(new GridBagLayout());
		gamePanel.setBackground(backColor);
		gridConstraints = new GridBagConstraints();
		gridConstraints.insets = new Insets(0, 0, 0, 5);
		
		gridConstraints.gridx = 0;
		gridConstraints.gridy = 0;
		gridConstraints.gridheight = 5;
		mainWindow.getContentPane().add(gamePanel, gridConstraints);
		
		//Players score and names area
		player1Label = new JLabel();
		resultsPanel = new JPanel();
		scoreTextField = new JTextField[2];
		messageLabel = new JLabel();
		
		resultsPanel.setPreferredSize(new Dimension(180,150));
		resultsPanel.setLayout(new GridBagLayout());
		resultsPanel.setBackground(backColor);
		
		gridConstraints = new GridBagConstraints();
		gridConstraints.insets = new Insets(0, 0, 5, 0);
		gridConstraints.gridx = 1;
		gridConstraints.gridy = 0;
		mainWindow.getContentPane().add(resultsPanel, gridConstraints);
		
		//Player1 INFO
		//player1Label.setText(bld.player1Label.getText());
		player1Label.setText(bld.p1Name);
		player1Label.setFont(new Font("Tahoma",Font.BOLD,16));
		playerBorder = BorderFactory.createLineBorder(Color.RED, 2);
		player1Label.setBorder(playerBorder);
		gridConstraints = new GridBagConstraints();
		gridConstraints.gridx=0;
		gridConstraints.gridy=0;
		resultsPanel.add(player1Label,gridConstraints);
		
		scoreTextField[0] = new JTextField();
		scoreTextField[0].setPreferredSize(new Dimension(100,25));
		scoreTextField[0].setText("0");
		scoreTextField[0].setEditable(false);
		scoreTextField[0].setBackground(Color.WHITE);
		scoreTextField[0].setHorizontalAlignment(SwingConstants.CENTER);
		scoreTextField[0].setFont(new Font("Tahoma",Font.PLAIN,16));
		
		gridConstraints = new GridBagConstraints();
		gridConstraints.gridx = 0;
		gridConstraints.gridy = 1;
		resultsPanel.add(scoreTextField[0],gridConstraints);
		
		//Player2 INFO
		if(bld.is2Players==true)
		{
			player2Label = new JLabel();
			player2Label.setText(bld.p2Name);
			//player2Label.setText("מחשב");
			player2Label.setFont(new Font("Tahoma",Font.BOLD,16));
			gridConstraints = new GridBagConstraints();
			gridConstraints.gridx = 0;
			gridConstraints.gridy = 2;
			gridConstraints.insets = new Insets(5,0,0,0);
			resultsPanel.add(player2Label,gridConstraints);
			
			//player2Label.setVisible(true);
					
			scoreTextField[1] = new JTextField();
			scoreTextField[1].setPreferredSize(new Dimension(100,25));
			scoreTextField[1].setText("0");
			scoreTextField[1].setEditable(false);
			scoreTextField[1].setBackground(Color.WHITE);
			scoreTextField[1].setHorizontalAlignment(SwingConstants.CENTER);
			scoreTextField[1].setFont(new Font("Tahoma",Font.PLAIN,16));
			//scoreTextField[1].setVisible(false);
					
			gridConstraints = new GridBagConstraints();
			gridConstraints.gridx = 0;
			gridConstraints.gridy = 3;
			resultsPanel.add(scoreTextField[1],gridConstraints);
			
			if(bld.p2Name=="computer")
			{
				isAgainstComputer=true;
			}
			is2Players=true;
		}
		else
		{
			is2Players=false;
			final long countDownTimer=bld.gameLength*1000*60;
			bld.numOfCards=24;
			
			timerPanel = new JPanel();
			timerPanel.setPreferredSize(new Dimension(160,140));
			timerPanel.setBackground(backColor);
			timerPanel.setBorder(BorderFactory.createTitledBorder("הזמן שנותר"));
			timerPanel.setLayout(new GridBagLayout());
			gridConstraints = new GridBagConstraints();
			gridConstraints.insets = new Insets(10,0,0,0);
			gridConstraints.gridx=1;
			gridConstraints.gridy=3;
			mainWindow.getContentPane().add(timerPanel,gridConstraints);
			
			//countDownTimer = gameLength*1000*60; //timer variable is miliseconds units -> converting integer of "minutes" to miliseconds
			final java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("mm:ss");
			final JLabel clockDown = new JLabel(sdf.format(new Date(countDownTimer)), new ImageIcon("ClockIcon.png"),JLabel.CENTER);
			clockDown.setFont(new Font("Arial",Font.BOLD,20));
			
			//int tempTimerVar = 0;
			ActionListener al;
			al = new ActionListener() {
				long tempTimerVar = countDownTimer - 1000;
				public void actionPerformed(ActionEvent evt)
				{
					clockDown.setText(sdf.format(new Date(tempTimerVar)));
					if(tempTimerVar==0)
					{
						timesUp.play();
						JOptionPane.showMessageDialog(null, "נגמר הזמן", "סוף המשחק", JOptionPane.WARNING_MESSAGE); // Pop up message
						//clockDown.setText("00:00");
						 ((Timer)evt.getSource()).stop(); //Stops the timer
						 
						 //Add stop game functions later here
						 mainWindow.dispose();
					}
					else tempTimerVar-=1000;
				}
			};
			new javax.swing.Timer(1000,  al).start();
			timerPanel.add(clockDown);
		}
		Border msgBorder = BorderFactory.createLineBorder(new Color(64,64,64));
		messageLabel.setPreferredSize(new Dimension(160,40));//160,40
		messageLabel.setOpaque(true);
		messageLabel.setBackground(new Color(176,196,222));
		messageLabel.setHorizontalAlignment(SwingConstants.CENTER);
		messageLabel.setBorder(msgBorder);
		//messageLabel.setText("");
		messageLabel.setText(player1Label.getText() + " בוחר / ת קלף"); //Updating players pick
		messageLabel.setFont(new Font("Tahoma",Font.PLAIN, 12));
				
		gridConstraints = new GridBagConstraints();
		gridConstraints.gridx = 0;
		gridConstraints.gridy = 10;
		gridConstraints.insets = new Insets(5,0,0,0);
		resultsPanel.add(messageLabel,gridConstraints);
		
		
		//Setting up cards as labels on the window
		photoLabel = new JLabel[bld.numOfCards];
		
		for (int i = 0; i < bld.numOfCards; i++)
		{
			// create a line border with the specified color and width
	        //Border cardBorder = BorderFactory.createLineBorder(Color.lightGray, 2);
	        Border cardBorder = BorderFactory.createLineBorder(new Color(64,64,64));
			photoLabel[i] = new JLabel();
			photoLabel[i].setPreferredSize(new Dimension(120, 75));//Cards size 120x75 pix
			photoLabel[i].setOpaque(true);
			photoLabel[i].setBackground(Color.DARK_GRAY);
			photoLabel[i].setIcon(bld.cover);
			photoLabel[i].setBorder(cardBorder);
			
			gridConstraints = new GridBagConstraints();
			gridConstraints.gridx = i % 4;
			gridConstraints.gridy = i / 4;
			gridConstraints.insets = new Insets(5, 5, 0, 0);
			
			if (gridConstraints.gridx == 3)
				gridConstraints.insets = new Insets(5, 5, 0, 5);
			
			if (gridConstraints.gridy == 4)
				gridConstraints.insets = new Insets(5, 5, 5, 0);
			
			if (gridConstraints.gridx == 3 && gridConstraints.gridy == 4)
				gridConstraints.insets = new Insets(5, 5, 5, 5);
		
			gamePanel.add(photoLabel[i], gridConstraints);
		
			photoLabel[i].addMouseListener(new MouseAdapter()
			{
				public void mousePressed(MouseEvent e)
				{
					photoLabelMousePressed(e);
				}
			});
		}
			
		photosRemaining=bld.numOfCards/2;
		
		buttonsPanel = new JPanel();
		startStopButton = new JButton();
		exitButton = new JButton();
		
		buttonsPanel.setPreferredSize(new Dimension(140,50));
		buttonsPanel.setLayout(new GridBagLayout());
		buttonsPanel.setBackground(Color.white);
		gridConstraints = new GridBagConstraints();
		gridConstraints.gridx = 1;
		gridConstraints.gridy = 4;
		mainWindow.getContentPane().add(buttonsPanel, gridConstraints);
	
		
		exitButton.setText("Exit");
		exitButton.setIcon(new ImageIcon("ExitButton1.png"));
		exitButton.setPreferredSize(new Dimension(180,60));
		exitButton.setEnabled(true); //Disabled as long as game in progress
		gridConstraints = new GridBagConstraints();
		gridConstraints.gridx = 0;
		gridConstraints.gridy = 1;
		gridConstraints.insets = new Insets (10,0,0,0);
		buttonsPanel.add(exitButton, gridConstraints);
		
		prevChoices = new int[2];
		
		exitButton.addActionListener(new ActionListener()
				{
			public void actionPerformed(ActionEvent evt)
			{
				exitButtonActionPerformed(evt);
				
			}
				});
		choiceNumber=1;//Initializing choiceNumber to 1 to indicate first selection of the card
		
		try
		{
			matchSound = Applet.newAudioClip(new URL("file:" + "card_match.wav"));
			noMatchSound = Applet.newAudioClip(new URL("file:" + "card_flip2.wav"));
			gameOverSound = Applet.newAudioClip(new URL("file:" + "victory.wav"));
			timesUp = Applet.newAudioClip(new URL("file:" + "times_up.wav"));
		}
		catch (Exception ex)
		{
			System.out.println("Error loading sound files");
		}
		whichPhotosFound = new boolean[bld.numOfCards];
		photos=new ImageIcon[12];
		for(int i=0;i<12;i++)
		{
			photos[i]=bld.photos[i];
		}
		photosIndex=new int[bld.numOfCards];
		for(int i=0;i<bld.numOfCards;i++)
		{
			photosIndex[i]=bld.photosIndex[i];
		}
		if(bld.p2Name=="מחשב")
			isAgainstComputer=true;
		
		displayTimer = new Timer(delay, new ActionListener()
				{
			public void actionPerformed(ActionEvent evt)
			{
				displayTimerActionPerformed(evt);
			}
				});
		mainWindow.setVisible(true);
	}
	
	
	private void photoLabelMousePressed(MouseEvent e)
	{
		Point p = e.getComponent().getLocation();
		
		if(isClickable==true) //Check if allowed to click to avoid illegal card flips
		{
			for(labelSelected=0;labelSelected<photoLabel.length;labelSelected++)
			{
				if(p.x==photoLabel[labelSelected].getX() && p.y==photoLabel[labelSelected].getY())
				{
					if(prevChoices[0]==whosTurn && prevChoices[1]==labelSelected)
					{
						break;
					}
					else
					{	
						if(choiceNumber==2)
						{
							prevChoices[1]=-1;
						}
						else
						{
							prevChoices[0]=whosTurn;
							prevChoices[1]=labelSelected;
						}						
						setChanged();
						notifyObservers(new GetPhotoFound());
						break;
					}
				}
			}
		}
	}
	private void exitButtonActionPerformed(ActionEvent evt)
	{
		//Hide window operation
	
		ms.setMainScreenEnabled();
		mainWindow.dispose();
	}
	private void displayTimerActionPerformed(ActionEvent evt)
	{
		displayTimer.stop();
		
		
		if(isClickable==false)
			isClickable=true;
		if(isAgainstComputer==true && whosTurn==2)
		{
			choiceNumber=1;
			
			setChanged();
			notifyObservers(new CheckMatch(choice[0],choice[1],whosTurn));
		}
		else
		{
			if(choiceNumber==1)
			{
				choice[0]=labelSelected;
				choiceNumber=2;
				if(whosTurn==1)
				{
					messageLabel.setText(player1Label.getText() + " בוחר / ת קלף"); //Updating players pick
					if(is2Players==true)
					{
						player2Label.setBorder(null);
						player1Label.setBorder(playerBorder);
					}
				}
				else 
					{
						messageLabel.setText(player2Label.getText() + " בוחר / ת קלף"); //Updating players pick
						if(is2Players==true)
						{
							player1Label.setBorder(null);
							player2Label.setBorder(playerBorder);
						}
					}
			
				//Check if 1 or 2 players game, and act accordingly
			}
			else
			{
				choice[1]=labelSelected;
				choiceNumber=1;
				
				setChanged();
				notifyObservers(new CheckMatch(choice[0],choice[1],whosTurn));
			}
		}
	}
	
	public void getCompMove(int[] compChoice)//Array is of size 2, containing 1st and 2nd pick
	{
		//Reveal the 2 cards the computer selected
		this.photoLabel[compChoice[0]].setIcon(this.photos[photosIndex[compChoice[0]]]);	
		this.photoLabel[compChoice[1]].setIcon(this.photos[photosIndex[compChoice[1]]]);
		choice[0]=compChoice[0];
		choice[1]=compChoice[1];
		
		displayTimer.start();
	}
	
//	public void whosTurnAnswer() {}
	public void getPhotoFound(boolean[] photoFound)
	{
		//whichPhotosFound = photoFound;
		if(!photoFound[labelSelected])
		{
			showSelectedLabel();
		}
	}
	public void scoreCalcAnswer(int score) 
	{
		if(whosTurn==1)
		{
			scoreTextField[0].setText(Integer.toString(score));
		}
		else
		{
			scoreTextField[1].setText(Integer.toString(score));
		}
	}
	public void checkMatchResult(boolean result)
	{
		if(result==true) //Its a match
		{
			matchSound.play();
			photoLabel[choice[0]].setBackground(null);
			photoLabel[choice[0]].setIcon(null);
			photoLabel[choice[1]].setBackground(null);
			photoLabel[choice[1]].setIcon(null);
			
			//Update score - updated in checkMatch, need to update display
			setChanged();
			notifyObservers(new GetScoreCalc(whosTurn));
			
			photosRemaining--;
			if(whosTurn==1)
			{
				messageLabel.setText(player1Label.getText() + " בוחר / ת קלף"); //Updating players pick
				if(is2Players==true)
				{
					player2Label.setBorder(null);
					player1Label.setBorder(playerBorder);
				}
			}
			else
			{
				messageLabel.setText(player2Label.getText() + " בוחר / ת קלף"); //Updating players pick
				if(is2Players==true)
				{
					player1Label.setBorder(null);
					player2Label.setBorder(playerBorder);
				}
			}
			
			if(photosRemaining==0)
			{
				if(is2Players==true && isAgainstComputer==false)//AgainstRival
				{
					GameDetails p1Record = new GameDetails("אחד נגד השני",player1Label.getText(),Integer.parseInt(scoreTextField[0].getText()));
					GameDetails p2Record = new GameDetails("אחד נגד השני",player2Label.getText(),Integer.parseInt(scoreTextField[1].getText()));
					
					setChanged();
					notifyObservers(p1Record);
					
					setChanged();
					notifyObservers(p2Record);
					
					gameOverSound.play();
					new EndGameScreen.Builder().setP1Name(player1Label.getText()).setP1Score(Integer.parseInt(scoreTextField[0].getText())).setP2Name(player2Label.getText()).setP2Score(Integer.parseInt(scoreTextField[1].getText())).build();
					ms.setMainScreenEnabled();
					mainWindow.dispose();
				}
				else if(is2Players==true && isAgainstComputer==true)//AgainstComputer
				{
					GameDetails record = new GameDetails("נגד המחשב",player1Label.getText(),Integer.parseInt(scoreTextField[0].getText()));
					
					setChanged();
					notifyObservers(record);
					
					gameOverSound.play();
					//new EndGameScreen.Builder().setP1Name(player1Label.getText()).setP1Score(Integer.parseInt(scoreTextField[0].getText())).build();
					new EndGameScreen.Builder().setP1Name(player1Label.getText()).setP1Score(Integer.parseInt(scoreTextField[0].getText())).setP2Name(player2Label.getText()).setP2Score(Integer.parseInt(scoreTextField[1].getText())).build();
					ms.setMainScreenEnabled();
					mainWindow.dispose();
					//System.exit(0);
				}
				else 	//AgainstTime
				{
					GameDetails record = new GameDetails("נגד הזמן",player1Label.getText(),Integer.parseInt(scoreTextField[0].getText()));
				
					setChanged();
					notifyObservers(record);
					
					gameOverSound.play();
					new EndGameScreen.Builder().setP1Name(player1Label.getText()).setP1Score(Integer.parseInt(scoreTextField[0].getText())).build();
					ms.setMainScreenEnabled();
					mainWindow.dispose();
				}
			}
			if(isAgainstComputer==true && whosTurn==2)
			{
				//Set a delay before next turn
				
				isClickable=false;
				setChanged();
				notifyObservers(new CompTurn());
			}
		}
		else
		{
			//In case theres no match, return the cards to be covered
			noMatchSound.play();
			photoLabel[choice[0]].setIcon(cover);
			photoLabel[choice[1]].setIcon(cover);
			//Change players turn
			if(is2Players==true)
			{
				if(whosTurn==1 && isAgainstComputer==false) //AgainstRival
				{
					whosTurn=2;
					messageLabel.setText(player2Label.getText() + " בוחר / ת קלף"); //Updating players pick
					player1Label.setBorder(null);
					player2Label.setBorder(playerBorder);
				}
				else if(whosTurn==2 && isAgainstComputer==false) //AgainstRival
				{
					whosTurn=1;
					messageLabel.setText(player1Label.getText() + " בוחר / ת קלף"); //Updating players pick
					player2Label.setBorder(null);
					player1Label.setBorder(playerBorder);
				}
				else if(whosTurn==1 && isAgainstComputer==true) //AgainstComputer
				{
					whosTurn=2;
					messageLabel.setText(player2Label.getText() + " בוחר / ת קלף");
					player1Label.setBorder(null);
					player2Label.setBorder(playerBorder);
					//Set a delay before next turn
					
					isClickable=false;
					setChanged();
					notifyObservers(new CompTurn());
				}
				else  //AgainstTime
				{
					whosTurn=1;
					messageLabel.setText(player1Label.getText() + " בוחר / ת קלף"); //Updating players pick
					player2Label.setBorder(null);
					player1Label.setBorder(playerBorder);
				}
			}
		}
	}
	private void showSelectedLabel()
	{
		if(isClickable==true)
		{
			this.photoLabel[labelSelected].setIcon(this.photos[photosIndex[labelSelected]]);
			
			isClickable=false;
			displayTimer.start();
		}
	}
	public static class Builder
	{
		private String p1Name;
		private String p2Name;
		private String difficulty;
		private int gameLength;
		private boolean is2Players=false;
		private int numOfCards;
		private ImageIcon cover;
		private int[] photosIndex;
		private ImageIcon[] photos;
		private MainScreen ms;
		
		
		public GeneralGameBuilder build()
		{
			return new GeneralGameBuilder(this);
		}
		public Builder setDifficulty(String difficulty)
		{
			this.difficulty=difficulty;
			if(this.difficulty=="Easy")
			{
				this.numOfCards=12;
			//	this.gameLength=15;
			}
			else if(this.difficulty=="Medium")
			{
				this.numOfCards=16;
			//	this.gameLength=10;
			}
			else if(this.difficulty=="Hard")
			{
				this.numOfCards=24;
			//	this.gameLength=5;
			}
			return this;
		}
		public Builder FirstPlayersName(String name)
		{
			this.p1Name = name;
			return this;
		}
		public Builder SecondPlayersName(String name)
		{
			this.is2Players=true;
			if(name=="computer")
				this.p2Name="מחשב";
			else this.p2Name = name;
			return this;
		}
		public Builder setGameLength(int num)
		{
			this.gameLength=num;
			return this;
		}
		
		public Builder setImageCover(ImageIcon cover)
		{
			this.cover=new ImageIcon();
			this.cover=cover;
			return this;
		}
		public Builder setPhotosIndex(int[] photosIndex)
		{
			this.photosIndex=new int[this.numOfCards];
			this.photosIndex=photosIndex;
			return this;
		}
		public Builder setPhotos(ImageIcon[] photos)
		{
			this.photos = new ImageIcon[12];
			this.photos=photos;
			return this;
		}
		public Builder setMainScreenInst(MainScreen ms)
		{
			this.ms=ms;
			return this;
		}
	}
}

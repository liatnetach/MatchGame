package controller;
import java.util.Observable;
import model.*;
import view.*;

public class GameController implements Controller{
	
	private Game model;//
	private AgainstComputer computerModel;
	private MainScreen mainView; //
	private GeneralGameBuilder gameScreenView; // 
	private DataController data;
	
	public GameController(MainScreen view,GeneralGameBuilder gameScreenView,AgainstComputer compM,DataController dataCont)
	{
		this.mainView=view;
		this.data=dataCont;
		this.gameScreenView=gameScreenView;
		this.gameScreenView.addObserver(this);
		this.computerModel=compM;
	}
	@Override
	public void update(Observable o, Object arg) 
	{	
		if(o instanceof MainScreen)//VIEW TO MODEL
		{
			mainView=(MainScreen)o;
			if(arg instanceof MainScreen.GameSettings)//Receive the game settings from the user(instructions how to build the game screen)
			{
				MainScreen.GameSettings gs=mainView.new GameSettings();
				gs=(MainScreen.GameSettings)arg;
				if(gs.getGameType()==0)
				{
				model=new AgainstRival(gs.getPlayer1(),gs.getPlayer2(),gs.getDifficulty());	
				}
				else if(gs.getGameType()==1)
				{
				model=new AgainstTime(gs.getPlayer1(),gs.getDifficulty());	
				}
				else if(gs.getGameType()==2)
				{
				model=new AgainstComputer(gs.getPlayer1(),gs.getDifficulty());
				}
				model.addObserver(this);
				model.setGame();
			}
		}
		//responses from the model
		else if(o instanceof Game) //MODEL TO VIEW 
		{
			model=(Game)o;
			 if(arg instanceof AgainstTime.TimeGameSettings) //in case that we need to build againstTime game
			 {
				 AgainstTime at=new AgainstTime();
				 AgainstTime.TimeGameSettings timeGS=at.new TimeGameSettings();
				 timeGS=(AgainstTime.TimeGameSettings)arg;//
				 GeneralGameBuilder gameScreenView = new GeneralGameBuilder.Builder().setMainScreenInst(mainView).setPhotos(timeGS.getPhotos()).setPhotosIndex(timeGS.getPhotosIndex()).setImageCover(timeGS.getCover()).FirstPlayersName(timeGS.getP1Name()).setDifficulty(timeGS.getDifficulty()).setGameLength(timeGS.getTime()).build();
				 data.setGs(gameScreenView);
				 gameScreenView.addObserver(this);
			 }
			 else if(arg instanceof AgainstComputer.CompGameSettings) //in case that we need to build againstComp game
			 {
				 AgainstComputer ac=new AgainstComputer();
				 AgainstComputer.CompGameSettings compGS=ac.new CompGameSettings();
				 compGS=(AgainstComputer.CompGameSettings)arg;//
				 GeneralGameBuilder gameScreenView = new GeneralGameBuilder.Builder().setMainScreenInst(mainView).setPhotos(compGS.getPhotos()).setPhotosIndex(compGS.getPhotosIndex()).setImageCover(compGS.getCover()).FirstPlayersName(compGS.getP1Name()).SecondPlayersName(compGS.getP2Name()).setDifficulty(compGS.getDifficulty()).build();
				 data.setGs(gameScreenView);
				 gameScreenView.addObserver(this);
			 }
			 else if(arg instanceof AgainstRival.RivalGameSettings) //in case that we need to build againstRival game
			 {
				 AgainstRival ar=new AgainstRival();
				 AgainstRival.RivalGameSettings rivalGS=ar.new RivalGameSettings();
				 rivalGS=(AgainstRival.RivalGameSettings)arg;//
				 GeneralGameBuilder gameScreenView = new GeneralGameBuilder.Builder().setMainScreenInst(mainView).setPhotos(rivalGS.getPhotos()).setPhotosIndex(rivalGS.getPhotosIndex()).setImageCover(rivalGS.getCover()).FirstPlayersName(rivalGS.getP1Name()).SecondPlayersName(rivalGS.getP2Name()).setDifficulty(rivalGS.getDifficulty()).build();
				 data.setGs(gameScreenView);
				 gameScreenView.addObserver(this);
			 }
		     // 
		    else if(arg instanceof Boolean)
	      	{
		    	gameScreenView.checkMatchResult((Boolean)arg);
	     	}// checkMatchResult 

	    	else if(arg instanceof int[]) 
		    {
	    		gameScreenView.getCompMove((int[])arg);
	     	}//response about the computer move
		
		   else if(arg instanceof Game.ScoreCalc)
		    {
			   Game.ScoreCalc scoreC=model.new ScoreCalc();
			   scoreC=(Game.ScoreCalc)arg;
			   gameScreenView.scoreCalcAnswer(scoreC.getScore());
		    } //score calculation answer
		
			else if(arg instanceof boolean[])
	    	{
	    		gameScreenView.getPhotoFound((boolean[]) arg);
	    	}//response of the photo-found array
		}    
		else if(o instanceof GeneralGameBuilder) //VIEW TO MODEL
		    {
        	gameScreenView=(GeneralGameBuilder)o;

			if(arg instanceof GeneralGameBuilder.CheckMatch)
			{
				 GeneralGameBuilder ggb=new GeneralGameBuilder();
				 GeneralGameBuilder.CheckMatch checkingM=ggb.new CheckMatch();
				 checkingM=( GeneralGameBuilder.CheckMatch)arg;//
				 model.checkMatch(checkingM.getFirstIndex(),checkingM.getSecondIndex(), checkingM.getWhosTurn());
			} //request for check if there is a match
			else if (arg instanceof GeneralGameBuilder.GetImageCover)//
			{
				model.getImagecover();		
			}
			 //request for the image cover
			else if (arg instanceof GeneralGameBuilder.GetPhotosArray)//  
			{
				model.getImagePhotoArr();		
			}//request for the photos array
			else if(arg instanceof GeneralGameBuilder.CompTurn)//
			{
				computerModel=(AgainstComputer)model;
				computerModel.compTurn();
			}//request for the computer turn
		
			else if(arg instanceof GeneralGameBuilder.GetScoreCalc)//
			{
				GeneralGameBuilder.GetScoreCalc scoreC=gameScreenView.new GetScoreCalc();
				scoreC=( GeneralGameBuilder.GetScoreCalc)arg;//
				model.scoreCalc(scoreC.getWhosTurn());
			}//request for score calculation for specific player
			
        	else if(arg instanceof GeneralGameBuilder.GetPhotoFound)
 			{
 				model.getPhotoFound();
 			}//request for the photo-found array
		
		}
	}
}

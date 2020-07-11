package model;

import org.junit.jupiter.api.Test;

public class AgainstComputerTest extends junit.framework.TestCase {
	AgainstComputer ac1;
	public AgainstComputerTest() {setUp();}
	
	public void setUp()
	{
		ac1 = new AgainstComputer("Rom",2);
	}
	@Test
	public void testSetGame() {
		assertTrue(ac1.gameSettings.player1=="Rom");//Test inner GameSettings object init values-according to the specific input
		assertTrue(ac1.gameSettings.gameLevel=="Hard");//Test inner GameSettings object init values-according to the specific input
		assertTrue(ac1.gameSettings.player2=="computer");//Test inner GameSettings object init values-according to the specific input
		assertTrue(ac1.numOfCards==24);//Test inner GameSettings object init values-according to the specific input
		assertTrue(ac1.gameSettings.cover==ac1.imageCover);//Test inner GameSettings object init values-according to the specific input
		assertTrue(ac1.gameSettings.photos==ac1.imagePhoto);//Test inner GameSettings object init values-according to the specific input
		assertTrue(ac1.gameSettings.photosIndex==ac1.photoIndex);//Test inner GameSettings object init values-according to the specific input
	}

	@Test
	public void testCompTurn() 
	{		
		ac1.compTurn();	
		assertTrue(ac1.choosePhotoLabel[0]>=0);
		assertTrue(ac1.choosePhotoLabel[1]>=0);
		assertTrue(ac1.choosePhotoLabel[0]!=ac1.choosePhotoLabel[1]);
		assertTrue(ac1.choosePhotoLabel[0]<ac1.numOfCards);
		assertTrue(ac1.choosePhotoLabel[1]<ac1.numOfCards);
		assertTrue(ac1.photoFound[ac1.choosePhotoLabel[0]]==false);
		assertTrue(ac1.photoFound[ac1.choosePhotoLabel[1]]==false);
			for(int i=2;i<ac1.numOfCards;i++)
				ac1.photoFound[i]=true;
		ac1.photoFound[0]=false;
		ac1.photoFound[1]=false;
		ac1.compTurn();
		assertTrue((ac1.choosePhotoLabel[0]==0 && ac1.choosePhotoLabel[1]==1) || (ac1.choosePhotoLabel[0]==1 && ac1.choosePhotoLabel[1]==0));
		
	}

	@Test
	public void testNRandomIntegers() {
		int[] counterArray=new int[ac1.numOfCards/2];//create counter array
		for(int i=0;i<ac1.numOfCards;i++)
		{
			assertTrue(ac1.photoIndex[i]<ac1.numOfCards/2);//Test if the given number is in the range
			assertTrue(ac1.photoIndex[i]>=0);//Test if the given number is positive
			counterArray[ac1.photoIndex[i]]++;
		}
		for(int i=0;i<ac1.numOfCards/2;i++)
		{
			assertTrue(counterArray[i]==2);//Test if every cardIndex inserted exactly twice as should
		}	
	}

	@Test
	public void testScoreCalc() {
		ac1.scoreCalc(-1); //Can be tested with any value except for "1"/"2", to verify nothing has changed inside score array
		assertTrue(ac1.score[0]==0); //Test init of score1 value
		assertTrue(ac1.score[1]==0); //Test init of combo1 value
		assertTrue(ac1.score[2]==0); //Test init of score2 value
		assertTrue(ac1.score[3]==0); //Test init of combo2 value
		
		ac1.score[1]=1;
		ac1.scoreCalc(1);//first player
		assertTrue(ac1.score[0]==5); //Combo is zero
		
		ac1.score[2]=5;//update manually the first match 
		ac1.score[3]=2;
		ac1.scoreCalc(2);//second player
		assertTrue(ac1.score[2]==15);//calculating score for p2 after first combo
		
		ac1.scoreCalc(3); //score[1] value is not changed because it can only update score for value=1
		assertTrue(ac1.score[0]==5);
		assertTrue(ac1.score[2]==15); //Combo is zero*
	}

}

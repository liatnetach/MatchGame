package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class AgainstRivalTest extends junit.framework.TestCase{
	AgainstRival ar1;
	public AgainstRivalTest() {setUp();}
	
	public void setUp()
	{
		ar1 = new AgainstRival("Orel","Liat",1);
	}
	@Test
	public void testSetGame() {
		assertTrue(ar1.gameSettings.player1=="Orel");//Test inner GameSettings object init values-according to the specific input
		assertTrue(ar1.gameSettings.gameLevel=="Medium");//Test inner GameSettings object init values-according to the specific input
		assertTrue(ar1.gameSettings.player2=="Liat");//Test inner GameSettings object init values-according to the specific input
		assertTrue(ar1.numOfCards==16);//Test inner GameSettings object init values-according to the specific input
		assertTrue(ar1.gameSettings.cover==ar1.imageCover);//Test inner GameSettings object init values-according to the specific input
		assertTrue(ar1.gameSettings.photos==ar1.imagePhoto);//Test inner GameSettings object init values-according to the specific input
		assertTrue(ar1.gameSettings.photosIndex==ar1.photoIndex);//Test inner GameSettings object init values-according to the specific input
	}

	@Test
	public void testNRandomIntegers() {
		int[] counterArray=new int[ar1.numOfCards/2];//create counter array
		for(int i=0;i<ar1.numOfCards;i++)
		{
			assertTrue(ar1.photoIndex[i]<ar1.numOfCards/2);//Test if the given number is in the range
			assertTrue(ar1.photoIndex[i]>=0);//Test if the given number is positive
			counterArray[ar1.photoIndex[i]]++;
		}
		for(int i=0;i<ar1.numOfCards/2;i++)
		{
			assertTrue(counterArray[i]==2);//Test if every cardIndex inserted exactly twice as should
		}	
	}

	@Test
	public void testScoreCalc() {
		ar1.scoreCalc(4); //Can be tested with any value except for "1"/"2", to verify nothing has changed inside score array
		assertTrue(ar1.score[0]==0); //Test init of score1 value
		assertTrue(ar1.score[1]==0); //Test init of combo1 value
		assertTrue(ar1.score[2]==0); //Test init of score2 value
		assertTrue(ar1.score[3]==0); //Test init of combo2 value
		
		ar1.score[1]=1;
		ar1.scoreCalc(1);//first player
		assertTrue(ar1.score[0]==5); //Combo is zero
		
		ar1.score[2]=5;//update manually the first match 
		ar1.score[3]=2;
		ar1.scoreCalc(2);//second player
		assertTrue(ar1.score[2]==15);//calculating score for p2 after first combo
		
		ar1.scoreCalc(3); //score[1] value is not changed because it can only update score for value=1
		assertTrue(ar1.score[0]==5);  //Verify value not affected
		assertTrue(ar1.score[2]==15); //Verify value not affected
	}

}

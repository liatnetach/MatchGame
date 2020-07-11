package model;

import org.junit.Test;
//import org.junit.jupiter.api.Test;

//import junit.framework.Assert;

public class AgainstTimeTest  extends junit.framework.TestCase{

	AgainstTime at1;
	public AgainstTimeTest() {setUp();}
	
	public void setUp()
	{
		at1 = new AgainstTime("Kiril",0);
	}
	
	@Test
	public void testSetGame() {
		assertTrue(at1.gameSettings.player1=="Kiril");//Test inner GameSettings object init values-according to the specific input
		assertTrue(at1.gameSettings.gameLevel=="Easy");//Test inner GameSettings object init values-according to the specific input
		assertTrue(at1.gameSettings.time==15);//Test inner GameSettings object init values-according to the specific input
		assertTrue(at1.gameSettings.cover==at1.imageCover);//Test inner GameSettings object init values-according to the specific input
		assertTrue(at1.gameSettings.photos==at1.imagePhoto);//Test inner GameSettings object init values-according to the specific input
		assertTrue(at1.gameSettings.photosIndex==at1.photoIndex);//Test inner GameSettings object init values-according to the specific input
	}

	@Test
	public void testScoreCalc() {
		
		at1.scoreCalc(0); //Can be tested with any value except for "1", to verify nothing has changed inside score array
		assertTrue(at1.score[0]==0); //Test init of score value
		assertTrue(at1.score[1]==0); //Test init of combo value
		
		at1.score[1]=1;
		at1.scoreCalc(1);
		assertTrue(at1.score[0]==5); //Combo is zero
		
		at1.score[1]=2;
		at1.scoreCalc(1);
		assertTrue(at1.score[0]==15);
		
		at1.scoreCalc(2); //score[1] value is not changed because it can only update score for value=1
		assertTrue(at1.score[0]==15); //Combo is zero
	}

	@Test
	public void testNRandomIntegers() {
		int[] counterArray=new int[at1.numOfCards/2];//create counter array
		for(int i=0;i<at1.numOfCards;i++)
		{
			assertTrue(at1.photoIndex[i]<at1.numOfCards/2);//Test if the given number is in the range
			assertTrue(at1.photoIndex[i]>=0); //Test if the given number is positive
			counterArray[at1.photoIndex[i]]++;
		}
		for(int i=0;i<at1.numOfCards/2;i++)
		{
			assertTrue(counterArray[i]==2);//Test if every cardIndex inserted exactly twice as should
		}
	}

}

package model;

import java.util.Calendar;
import java.util.Date;

import org.apache.poi.ss.usermodel.Cell;
import org.junit.jupiter.api.Test;

public class DataTest extends junit.framework.TestCase{
	 Data data1;
	public DataTest() {setUp();}
	public void setUp()
	{
		data1 = new Data();
	}
	@Test
	public void testAddChild() {
		int vectorSize =data1.children.size();
		int currRow=data1.childrenList.getLastRowNum();
		Children child=new Children("Testing","0012345678900");
		data1.addChild(child);
		assertTrue(data1.children.size()==vectorSize+1);//after adding a child the vector and the excel should updated
		assertTrue(data1.children.elementAt(vectorSize)=="Testing");//after adding a child the vector and the excel should updated
		assertTrue(data1.childrenList.getLastRowNum()==currRow+1);
		String cell2Str = data1.formatter.formatCellValue(data1.childrenList.getRow(currRow+1).getCell(0));
		assertTrue(cell2Str=="Testing");//after adding a child the vector and the excel should updated
		cell2Str = data1.formatter.formatCellValue(data1.childrenList.getRow(currRow+1).getCell(1));
		assertTrue(cell2Str=="0012345678900");
		data1.addChild(new Children("Test","0012345678900"));//verify that we doesn't allowed 2 same id
		assertTrue(data1.childrenList.getLastRowNum()==currRow+1);//Test that the children sheet didn't updated with new child
		assertTrue(data1.children.size()==vectorSize+1);//Test that the vector didn't updated with new child
		data1.addChild(new Children("Testing","6789"));//verify that we doesn't allowed 2 same name exactly
		assertTrue(data1.childrenList.getLastRowNum()==currRow+1);//Test that the children sheet didn't updated with new child
		assertTrue(data1.children.size()==vectorSize+1);//Test that the vector didn't updated with new child
		data1.deleteChild(vectorSize);//delete the last child on the list (the  one that we added before)
	}

	@Test
	public void testSaveGameDetails() {
		int currRow=data1.gameHistory.getLastRowNum();
		data1.saveGameDetails(new GameRecord(new Children("Testing","0012345678900"),55,"AgainstTest"));//insert data
		assertTrue(data1.gameHistory.getLastRowNum()==currRow+1);
		String cell2Str = data1.formatter.formatCellValue(data1.gameHistory.getRow(currRow+1).getCell(0));
		assertTrue(cell2Str=="Testing");//after adding a child the vector and the excel should updated
		Cell date=data1.gameHistory.getRow(currRow+1).getCell(1);//getting the date
    	Date playedAt=date.getDateCellValue();
    	playedAt.setTime(0);
    	Date current=Calendar.getInstance().getTime();
    	current.setTime(0);
    	assertTrue(current.compareTo(playedAt)==0);
		cell2Str = data1.formatter.formatCellValue(data1.gameHistory.getRow(currRow+1).getCell(2));
		assertTrue(cell2Str=="AgainstTest");
		Cell scoreCell=data1.gameHistory.getRow(currRow+1).getCell(3);
		double score=scoreCell.getNumericCellValue();
		assertTrue(score==55);
	}

	@Test
	public void testDeleteChild() {
		Children child1=new Children("Testing1","0012345678900");
		data1.addChild(child1);
		data1.saveGameDetails(new GameRecord(child1,55,"AgainstTest"));//insert data
		data1.saveGameDetails(new GameRecord(child1,55,"AgainstTest"));//insert data
		int vectorSize =data1.children.size();//after adding 1 child
		int currRowCL=data1.childrenList.getLastRowNum();//after adding 1 child
		int currRowGH=data1.gameHistory.getLastRowNum();//after adding 2 game records
		data1.deleteChild(vectorSize-1);//delete the last child on the list (the  one that we added before)
		assertTrue(data1.gameHistory.getLastRowNum()==currRowGH-2);
		if(vectorSize-1>0)
		{
			assertTrue(data1.children.size()==vectorSize-1);//after adding a child the vector and the excel should updated
			assertTrue(data1.children.get(vectorSize-2).toString()!="Testing1");//after adding a child the vector and the excel should updated
			assertTrue(data1.childrenList.getLastRowNum()==currRowCL-1);
			String cell2Str = data1.formatter.formatCellValue(data1.childrenList.getRow(currRowCL-1).getCell(0));
			assertTrue(cell2Str!="Testing1");//after adding a child the vector and the excel should updated
			cell2Str = data1.formatter.formatCellValue(data1.childrenList.getRow(currRowCL-1).getCell(1));
			assertTrue(cell2Str!="0012345678900");//verify that the children really deleted
		}
	}

}

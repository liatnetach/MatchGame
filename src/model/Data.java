package model;
import java.io.File;  
import java.io.FileInputStream;  
import java.io.FileOutputStream;
import java.util.*;
import org.apache.poi.ss.usermodel.*;  
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.JTable;


public class Data extends Observable implements Model
{
int rowNumCL,rowNumGH;
Sheet childrenList,gameHistory;
private static String[] childSheetColumns = {"שם מלא", "תעודת זהות"};
private static String[] gamesSheetColumns = {"שם מלא","תאריך המשחק","סוג המשחק","ניקוד"};
Workbook dataFile;//this will be the file that will contain the two sheets of data we want to save
CellStyle dateCellStyle;
Vector<String> children = new Vector<String>(); // Create vector object
FileOutputStream fileOut;
FileInputStream inputStream;
String excelFilePath="DataFile.xlsx";
File f=new File(excelFilePath);
boolean update=false;
DataFormatter formatter = new DataFormatter();
Cell cell2compare;

public Data()
{
	if(f.exists()) //in case that the DataFile already exists we just update it
	{
		update=true;
		try {
			inputStream=new FileInputStream(f);
			dataFile=WorkbookFactory.create(inputStream);
			childrenList=dataFile.getSheetAt(0);
			gameHistory=dataFile.getSheetAt(1);
			rowNumCL=childrenList.getLastRowNum();
			rowNumGH=gameHistory.getLastRowNum();
			rowNumCL++;
			rowNumGH++;
			Row row;
			for(int i=1;i<rowNumCL;i++) //update children vector according to childrenList 
			{
				row=childrenList.getRow(i);
				cell2compare=row.getCell(0);
				String strValue2 = formatter.formatCellValue(cell2compare);
				children.add(strValue2);
			}
		    CreationHelper createHelper = dataFile.getCreationHelper();
			 dateCellStyle = dataFile.createCellStyle();
			    dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd-MM-yyyy"));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	else//there is no existing DataFile yet-> we will create it 
	{
		dataFile=new XSSFWorkbook();
	    CreationHelper createHelper = dataFile.getCreationHelper();
		childrenList=dataFile.createSheet("רשימת ילדים");//creating new sheet
		gameHistory=dataFile.createSheet("היסטוריית משחקים");//creating new sheet
		 // Create a Font for styling header cells
	    Font headerFont = dataFile.createFont();
	    headerFont.setBold(true);
	    headerFont.setFontHeightInPoints((short) 14);
	    headerFont.setColor(IndexedColors.RED.getIndex());
	
	    // Create a CellStyle with the font
	    CellStyle headerCellStyle = dataFile.createCellStyle();
	    headerCellStyle.setFont(headerFont);
	    
	    // Create a Row for each sheet
	    Row headerRowCL = childrenList.createRow(0);
	    Row headerRowGH = gameHistory.createRow(0);
	
	    // Create cells in both Sheets
	    for(int i = 0; i < childSheetColumns.length; i++) {
	        Cell cell = headerRowCL.createCell(i);
	        cell.setCellValue(childSheetColumns[i]);
	        cell.setCellStyle(headerCellStyle);
	    }
	    for(int i = 0; i < gamesSheetColumns.length; i++) {
	        Cell cell = headerRowGH.createCell(i);
	        cell.setCellValue(gamesSheetColumns[i]);
	        cell.setCellStyle(headerCellStyle);
	    }
	    
	 // Create Cell Style for formatting Date
	    dateCellStyle = dataFile.createCellStyle();
	    dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd-MM-yyyy"));
	    
		for(int i = 0; i < gamesSheetColumns.length; i++) {
			gameHistory.autoSizeColumn(i);}
			
	    rowNumCL=1;
	    rowNumGH=1;
    }

}
public void addChild(Children child) 
{
	Row row;
	boolean isExist=false;
	for(int j=0;j<childrenList.getLastRowNum();j++)//check if the ID exist in the children vector
	{
		row=childrenList.getRow(j+1);//+1 because of the header row
		cell2compare=row.getCell(1);//getting the id value
		String strValue2 = formatter.formatCellValue(cell2compare);
		if(Objects.equals(child.getId(), strValue2))//the id already exist in the DataFile
		{
			setChanged();
			notifyObservers(new String("ישנו ילד עם אותו מספר תעודת זהות!"));
			isExist=true;
		}
	}
	if(!isExist)
	{
		for(int j=0;j<children.size();j++)//check if the name exist in the children vector
		{
			if(Objects.equals(child.getName(), children.get(j).toString())) //if we found another child with same name we will check the id
			{	
				isExist=true;
				setChanged();
			 	notifyObservers(new String("ישנם שני ילדים בעלי אותו שם אך מספר תעודת זהות שונה, בבקשה הכנס מזהה נוסף לשם הנוכחי"));
			}
		}
	}
	
	if(!isExist)//add the child if there isn't child with same id/name
	{
		row=childrenList.createRow(rowNumCL++);
		row.createCell(0).setCellValue(child.getName());
		row.createCell(1).setCellValue(child.getId());
		for(int i = 0; i < childSheetColumns.length; i++) {
			childrenList.autoSizeColumn(i);
		}
		children.add(child.getName());
		setChanged();
		notifyObservers(new String(child.getName()+" התווסף בהצלחה למאגר הנתונים!"));
	}
}
public void saveGameDetails(GameRecord gameR)
{
	Row row=gameHistory.createRow(rowNumGH++);
	row.createCell(0).setCellValue(gameR.getChildName());
	Cell dateCell=row.createCell(1);
	dateCell.setCellValue(gameR.getGameDate());
	dateCell.setCellStyle(dateCellStyle);
	row.createCell(2).setCellValue(gameR.getGameType());
	row.createCell(3).setCellValue(gameR.getScore());
	for(int i = 0; i < gamesSheetColumns.length; i++) {
		gameHistory.autoSizeColumn(i);}
}
public void getChildrenList()
{
	setChanged();
	notifyObservers(children);
}

public void deleteChild(int index)
{
	int rowDeleted=0;
	if(!children.isEmpty())
	{
		Row row;
		String child=children.get(index).toString();
		row=childrenList.getRow(index+1);//+1 because of the header row
		childrenList.removeRow(row);
		if((index+1)!=(rowNumCL-1))
		{
			childrenList.shiftRows(index+2, rowNumCL-1, -1);
		}
		rowNumCL--;
		children.remove(index);
		if(rowNumGH!=1)
		{	
			for(int i=1;i<rowNumGH;i++)
			{
				row=gameHistory.getRow(i);
				cell2compare=row.getCell(0);
				String strValue2 = formatter.formatCellValue(cell2compare);
				if(Objects.equals(child, strValue2))
				{
					gameHistory.removeRow(row);
					if(i!=(rowNumGH-1))
					{
						gameHistory.shiftRows(i+1, rowNumGH-1, -1);
						i--;
					}
					rowNumGH--;
				}
			}
		}
		setChanged();
		notifyObservers(new String(child+" והרשומות המקושרות אליו נמחקו ממאגר הנתונים!"));
	}
}

public void closeFile() // when closing tha app -> close the in/output files 
{
	try
	{
		if(update) inputStream.close();
		fileOut = new FileOutputStream("DataFile.xlsx"); /// else create new file!!!!!
		dataFile.write(fileOut);
		fileOut.close();
		dataFile.close();
	}
	catch(Exception e) {e.printStackTrace();}
	}

public void makeLinearStatsPerChild(int index,Date startDate,Date endDate ) {
		String child=children.get(index).toString();
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
	    Row row;
	    Cell cell2compare;
	    DataFormatter formatter = new DataFormatter();
		Cell date,score;
		Integer progress=1;
	    for(int i=1;i<=gameHistory.getLastRowNum();i++)
	    {
	    	row=gameHistory.getRow(i);
			cell2compare=row.getCell(0);
			String strValue2 = formatter.formatCellValue(cell2compare);
			if(Objects.equals(child, strValue2))
			{
				date=row.getCell(1);
		    	Date current=date.getDateCellValue();
		    	current.setTime(0);
		    	if(!current.after(endDate) && !current.before(startDate))
		    	{
			    	score=row.getCell(3);
		    		String scorestr=formatter.formatCellValue(score);
		    		dataset.addValue( Integer.parseInt(scorestr) , child , (progress++).toString());
		    	}
		    }
	    }
	    setChanged();
	    notifyObservers(dataset);	
}

public void makeGeneralScoreStats(Date startDate,Date endDate) {
	if(rowNumGH!=1)
	{	
		Row row;
		Cell date,score,gametype;
		String [][] data= new String[gameHistory.getLastRowNum()][4]; 
		String [] record=new String[4];
		int k=0;
	    for(int i=1;i<=gameHistory.getLastRowNum();i++)
	    {
	    	row=gameHistory.getRow(i);
	    	date=row.getCell(1);
	    	Date current=date.getDateCellValue();
	    	if(!current.after(endDate) && !current.before(startDate)) 
	    	{
				cell2compare=row.getCell(0);
				date=row.getCell(1);
		    	score=row.getCell(3);
		    	gametype=row.getCell(2);
				record[0]=formatter.formatCellValue(cell2compare);
				record[1]=formatter.formatCellValue(date);
				record[2]=formatter.formatCellValue(gametype);
				record[3]=formatter.formatCellValue(score);
				data[k++]=record.clone();
			}
	    }   
		 String column[]={"שם מלא","תאריך","סוג משחק","ניקוד"};         
		 JTable jt=new JTable(data,column);    
		 jt.setBounds(30,40,200,300);
		 setChanged();
		 notifyObservers(jt);
	}
}

	
}
package controller;
import javax.swing.*;

import org.jfree.data.category.DefaultCategoryDataset;
import model.*;
import view.*;
import java.util.Observable;
import java.util.Vector;

public class DataController implements Controller{
	
	private Data data;
	private MainScreen Ms;
	private GeneralGameBuilder Gs;
	private StatisticsScreen Ss;
	private ChildManagementScreen Cms;
	private String view;
	
	public DataController(Data d,MainScreen ms,GeneralGameBuilder gs,StatisticsScreen ss,ChildManagementScreen cms)
	{
		this.data=d;
		this.Ms=ms;
		this.Gs=gs;
		this.Ss=ss;
		this.Cms=cms;
	}
	
	public void setGs(GeneralGameBuilder gs) {
		Gs=gs;
		Gs.addObserver(this);
	}
	@SuppressWarnings("unchecked")
	@Override
	public void update(Observable o, Object arg) {
		
		if(o instanceof Data)
		{
			data=(Data)o;
			if(arg instanceof DefaultCategoryDataset)
			{
		    	Ss.Showgraph((DefaultCategoryDataset) arg);
			}
		    else if(arg instanceof JTable)
			{
				Ss.Showtable((JTable) arg);
			}
		    else if(arg instanceof String)
			{
				Cms.ShowAddChildResponse((String) arg);
			}
		    else if(arg instanceof Vector)
			{
		    	if(view == "MainScreen")
				{
					Ms.updateList((Vector<String>) arg);
				}
				else if(view =="ChildManagementScreen")
				{
					Cms.updateList((Vector<String>) arg);
				}
				else if(view== "StatisticsScreen")
				{
					Ss.updateList((Vector<String>) arg);
				}
			}
			
		}
		else if(o instanceof MainScreen)
		{
			Ms=(MainScreen)o;
			if(arg instanceof MainScreen.ExitEvent)// inner class
			{
			
				data.closeFile();
			}
			else if(arg instanceof MainScreen.StartGame)// inner class
			{
				view="MainScreen";
				data.getChildrenList();
			}
		}
		else if(o instanceof StatisticsScreen)
		{
			Ss=(StatisticsScreen)o;
			if(arg instanceof StatisticsScreen.UpdateList)// inner class
			{
				view="StatisticsScreen";
				data.getChildrenList();
			}
			else if(arg instanceof StatisticsScreen.LinearStats)//inner class
			{
				StatisticsScreen.LinearStats graph=Ss.new LinearStats();
				graph=(StatisticsScreen.LinearStats)arg;
				data.makeLinearStatsPerChild(graph.getIndex(),graph.getfirstD(),graph.getSecondD());
			}
			else if(arg instanceof StatisticsScreen.TableStats)//inner class
			{
				StatisticsScreen.TableStats table=Ss.new TableStats();
				table=(StatisticsScreen.TableStats)arg;
				data.makeGeneralScoreStats(table.getfirstD(),table.getSecondD());
			}
		}
		else if(o instanceof ChildManagementScreen)
		{
			Cms=(ChildManagementScreen)o;
			if(arg instanceof ChildManagementScreen.UpdateList)//inner class
			{
				view= "ChildManagementScreen";
				data.getChildrenList();
			}
			else if(arg instanceof ChildManagementScreen.Children)// inner class
			{
				ChildManagementScreen child=new ChildManagementScreen();
				ChildManagementScreen.Children ch=child.new Children();
				ch=(ChildManagementScreen.Children)arg;//
				data.addChild(new Children(ch.getName(),ch.getId()));
			}
			else
			{
				data.deleteChild((int) arg);
			}
		}
		else if(o instanceof GeneralGameBuilder)
		{
			if(arg instanceof GeneralGameBuilder.GameDetails)// inner class
			{
				GeneralGameBuilder.GameDetails details=Gs.new GameDetails();
				details=(GeneralGameBuilder.GameDetails)arg;//
				data.saveGameDetails(new GameRecord(new Children(details.getName()),details.getScore(),details.getGametype()));
			}
		}
	}
} 

package driver;
import model.*;
import view.*;
import controller.*;

public class MemoryCards 
{
	public static void main(String[] args) 
	{		
		Data model=new Data();
		AgainstComputer compM=new AgainstComputer();
		GeneralGameBuilder gameWindow = new GeneralGameBuilder();
		StatisticsScreen statpage=new StatisticsScreen();
		ChildManagementScreen managechild=new ChildManagementScreen();
		MainScreen view = new MainScreen(statpage,managechild);
		DataController controller1=new DataController(model,view,gameWindow,statpage,managechild);
		Controller controller = new GameController(view, gameWindow, compM,controller1);
		//connect the observables to their observers
		view.addObserver(controller);
		compM.addObserver(controller);
		gameWindow.addObserver(controller);
		view.addObserver(controller1);
		model.addObserver(controller1);
		statpage.addObserver(controller1);
		managechild.addObserver(controller1);
		gameWindow.addObserver(controller1);
	}
}


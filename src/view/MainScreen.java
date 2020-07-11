package view;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import java.awt.Font;
import javax.swing.SwingConstants;

import javax.swing.JButton;
import javax.swing.JComboBox;
import java.awt.Color;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.*;
public class MainScreen extends Observable implements View{

	public class GameSettings
	{
		int gameType;
		int gameLevel;
		String player1;
		String player2;
		public GameSettings(){}
		public GameSettings(int type,int level,String p1,String p2) 
		{
			gameType=type;
			gameLevel=level;
			player1=p1;
			player2=p2;
		}
		public int getDifficulty() {return gameLevel;}
		public int getGameType() {return gameType;}
		public String getPlayer1() {return player1;}
		public String getPlayer2() {return player2;}
	}
	public class ExitEvent{}
	public class StartGame{}
	private GameSettings gameSettings;
	private JFrame frame;
	private final ButtonGroup buttonGroup_kind = new ButtonGroup();
	private final ButtonGroup buttonGroup_number = new ButtonGroup();
	private final ButtonGroup buttonGroup_level = new ButtonGroup();
	private JPanel panel;
	private JLabel titleL,secondPNameL,pNames,firstPNameL,pNum,gKind,dLevelL;
	private JRadioButton twoPlayersRadioButton,onePlayerRadioButton,againstTimeRadioButton,againstCompRadioButton,medLevel,hardLevel
	,easLevel,oneOnOne;	
	private JButton adminButton,startGame;
	private JComboBox<String> p2List;
	public JComboBox<String> p1List;
	private Vector<String> childrenList1 = new Vector<String>();
	private static Boolean oneP=false,twoP=false;
	private ChildManagementScreen childScreen;
	private JButton childrenManagementButton;
	private StatisticsScreen adminScreen;
	/**
	 * Launch the application.
	 */
	 
	/*public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainScreen window = new MainScreen();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
	}*/

	/**
	 * Create the application.
	 */
	public MainScreen()
	{
		
	}
	
	public MainScreen(StatisticsScreen statpage,ChildManagementScreen managechild) {
		this.adminScreen=statpage;
		this.childScreen=managechild;
	   
		initialize();
		gameSettings=new GameSettings();
		try {
			this.frame.setVisible(true);
		
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("זכור את הפעל");
		frame.setBounds(100, 100, 787, 643);
		//add special closing window behavior
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				setChanged();
				notifyObservers(new ExitEvent());
				System.exit(0);
			}
		});
		
		panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		//Setting background photo
		ImagePanel panel = new ImagePanel(new ImageIcon("BackgroundPic.jpg").getImage());
		//panel.setBackground(Color.WHITE);
		panel.setBounds(0,0, 1100, 800);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		
		//gameName title
		titleL = new JLabel("\u05D6\u05DB\u05D5\u05E8 \u05D0\u05EA \u05D4\u05E4\u05D5\u05E2\u05DC");
		titleL.setForeground(new Color(153,50,204));
		titleL.setHorizontalAlignment(SwingConstants.RIGHT);
		titleL.setFont(new Font("Tahoma",Font.BOLD,34));
		titleL.setBounds(252, 42, 252, 39);
		panel.add(titleL);

		//2 players radioB
		twoPlayersRadioButton = new JRadioButton("2");
		twoPlayersRadioButton.setFont(new Font("Tahoma", Font.PLAIN, 18));
		twoPlayersRadioButton.setHorizontalTextPosition(JRadioButton.LEADING);
		twoPlayersRadioButton.setHorizontalAlignment(JRadioButton.TRAILING);
		twoPlayersRadioButton.setOpaque(false);//Make radio button background transparent
		twoPlayersRadioButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				twoPlayersRadioButtonActionPerformed(e);
			}
		});
		buttonGroup_number.add(twoPlayersRadioButton);
		twoPlayersRadioButton.setBounds(690, 156, 59, 25);
		panel.add(twoPlayersRadioButton);
		
		//1 player radioB
		onePlayerRadioButton = new JRadioButton("1");
		onePlayerRadioButton.setFont(new Font("Tahoma", Font.PLAIN, 18));
		onePlayerRadioButton.setHorizontalTextPosition(JRadioButton.LEADING);
		onePlayerRadioButton.setHorizontalAlignment(JRadioButton.TRAILING);
		onePlayerRadioButton.setOpaque(false);
		onePlayerRadioButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onePlayerRadioButtonActionPerformed(e);
			}
		});
		buttonGroup_number.add(onePlayerRadioButton);
		onePlayerRadioButton.setBounds(690, 126, 59, 25);
		panel.add(onePlayerRadioButton);
		
		//number Title
		pNum = new JLabel("\u05DE\u05E1\u05E4\u05E8 \u05E9\u05D7\u05E7\u05E0\u05D9\u05DD:");
		pNum.setHorizontalAlignment(SwingConstants.RIGHT);
		pNum.setFont(new Font("Tahoma", Font.BOLD, 17));
		pNum.setBounds(614, 104, 135, 21);
		panel.add(pNum);
		
		//statistics Button
		adminButton = new JButton("\u05E1\u05D5\u05E4\u05E8-\u05DE\u05E9\u05EA\u05DE\u05E9");
		adminButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		adminButton.setIcon(new ImageIcon("AdminButton.png"));
		adminButton.setBounds(12, 13, 149, 25);
		panel.add(adminButton);
		adminButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				openAdmin(e);
			}
		});
		
		//kind Title
		gKind = new JLabel("\u05E1\u05D5\u05D2 \u05DE\u05E9\u05D7\u05E7:");
		gKind.setHorizontalAlignment(SwingConstants.RIGHT);
		gKind.setFont(new Font("Tahoma", Font.BOLD, 17));
		gKind.setBounds(614, 190, 135, 21);
		gKind.setEnabled(false);
		panel.add(gKind);
		
		//againstTime Mode
		againstTimeRadioButton = new JRadioButton("\u05DE\u05E9\u05D7\u05E7 \u05E0\u05D2\u05D3 \u05D4\u05D6\u05DE\u05DF");
		againstTimeRadioButton.setFont(new Font("Tahoma", Font.PLAIN, 18));
		againstTimeRadioButton.setHorizontalTextPosition(JRadioButton.LEADING);
		againstTimeRadioButton.setHorizontalAlignment(JRadioButton.TRAILING);
		againstTimeRadioButton.setOpaque(false);
		againstTimeRadioButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onAgainstTime(e);			}
		});
		buttonGroup_kind.add(againstTimeRadioButton);
		againstTimeRadioButton.setBounds(587, 214, 162, 25);
		againstTimeRadioButton.setEnabled(false);
		panel.add(againstTimeRadioButton);

		//againstComp Mode		
		againstCompRadioButton = new JRadioButton("\u05DE\u05E9\u05D7\u05E7 \u05E0\u05D2\u05D3 \u05D4\u05DE\u05D7\u05E9\u05D1");
		againstCompRadioButton.setFont(new Font("Tahoma", Font.PLAIN, 18));
		buttonGroup_kind.add(againstCompRadioButton);
		againstCompRadioButton.setHorizontalTextPosition(JRadioButton.LEADING);
		againstCompRadioButton.setHorizontalAlignment(JRadioButton.TRAILING);
		againstCompRadioButton.setOpaque(false);
		againstCompRadioButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onAgainstComp(e);			}
		});
		againstCompRadioButton.setBounds(570, 244, 179, 25);
		againstCompRadioButton.setEnabled(false);
		panel.add(againstCompRadioButton);
		
		//name Title
		pNames = new JLabel("\u05E9\u05DE\u05D5\u05EA \u05D4\u05E9\u05D7\u05E7\u05E0\u05D9\u05DD:");
		pNames.setHorizontalAlignment(SwingConstants.RIGHT);
		pNames.setFont(new Font("Tahoma", Font.BOLD, 17));
		pNames.setBounds(597, 422, 149, 21);
		pNames.setEnabled(false);
		panel.add(pNames);
		
		//p1 nameLabel		
		firstPNameL = new JLabel("\u05E9\u05D7\u05E7\u05DF 1:");
		firstPNameL.setFont(new Font("Tahoma", Font.PLAIN, 18));
		firstPNameL.setHorizontalAlignment(SwingConstants.RIGHT);
		firstPNameL.setBounds(676, 453, 70, 25);
		firstPNameL.setEnabled(false);
		panel.add(firstPNameL);
		
		//p2 nameLabel
		secondPNameL = new JLabel("\u05E9\u05D7\u05E7\u05DF 2:");
		secondPNameL.setFont(new Font("Tahoma", Font.PLAIN, 18));
		secondPNameL.setHorizontalAlignment(SwingConstants.RIGHT);
		secondPNameL.setBounds(676, 487, 70, 25);
		secondPNameL.setEnabled(false);
		panel.add(secondPNameL);
		
		//level Title
		dLevelL = new JLabel("\u05E8\u05DE\u05EA \u05D4\u05E7\u05D5\u05E9\u05D9:");
		dLevelL.setHorizontalAlignment(SwingConstants.RIGHT);
		dLevelL.setFont(new Font("Tahoma", Font.BOLD, 17));
		dLevelL.setBounds(626, 308, 123, 21);
		dLevelL.setEnabled(false);
		panel.add(dLevelL);
		
		//med level radioB
		medLevel = new JRadioButton("\u05D1\u05D9\u05E0\u05D5\u05E0\u05D9");
		medLevel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		buttonGroup_level.add(medLevel);
		medLevel.setHorizontalTextPosition(JRadioButton.LEADING);
		medLevel.setHorizontalAlignment(JRadioButton.TRAILING);
		medLevel.setOpaque(false);
		medLevel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onAnylevelButtonActionPerformed(e);
			}
		});
		medLevel.setBounds(667, 361, 82, 25);
		medLevel.setEnabled(false);
		panel.add(medLevel);
		
		//easy level radioB
		easLevel = new JRadioButton("\u05E7\u05DC");
		easLevel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		buttonGroup_level.add(easLevel);
		easLevel.setHorizontalTextPosition(JRadioButton.LEADING);
		easLevel.setHorizontalAlignment(JRadioButton.TRAILING);
		easLevel.setOpaque(false);
		easLevel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onAnylevelButtonActionPerformed(e);
			}
		});
		easLevel.setBounds(693, 331, 56, 25);
		easLevel.setEnabled(false);
		panel.add(easLevel);
		
		//hard level radioB
		hardLevel = new JRadioButton("\u05E7\u05E9\u05D4");
		hardLevel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		buttonGroup_level.add(hardLevel);
		hardLevel.setHorizontalTextPosition(JRadioButton.LEADING);
		hardLevel.setHorizontalAlignment(JRadioButton.TRAILING);
		hardLevel.setOpaque(false);
		hardLevel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onAnylevelButtonActionPerformed(e);
			}
		});
		hardLevel.setBounds(679, 388, 70, 25);
		hardLevel.setEnabled(false);
		panel.add(hardLevel);
		
		//one-on-one radioB
		oneOnOne = new JRadioButton("\u05D0\u05D7\u05D3 \u05E2\u05DC \u05D0\u05D7\u05D3");
		oneOnOne.setFont(new Font("Tahoma", Font.PLAIN, 18));
		oneOnOne.setHorizontalTextPosition(JRadioButton.LEADING);
		oneOnOne.setHorizontalAlignment(JRadioButton.TRAILING);
		oneOnOne.setOpaque(false);
		buttonGroup_kind.add(oneOnOne);
		oneOnOne.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onAgainstRival(e);
			}
		});
		oneOnOne.setBounds(597, 274, 152, 25);
		oneOnOne.setEnabled(false);
		panel.add(oneOnOne);

		//start-game button
		startGame = new JButton("\u05D4\u05EA\u05D7\u05DC \u05DE\u05E9\u05D7\u05E7!");
		startGame.setIcon(new ImageIcon("StartGame.png"));
		startGame.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				onStartGameButtonActionPerformed(e);
			}
		});
		startGame.setFont(new Font("Guttman Kav-Light", Font.PLAIN, 24));
		startGame.setForeground(new Color(255, 0, 0));
		startGame.setBackground(new Color(0, 206, 209));
		startGame.setBounds(31, 467, 162, 43);
		startGame.setEnabled(false);
		panel.add(startGame);
	
		
		childrenManagementButton = new JButton("\u05E0\u05D4\u05DC \u05D0\u05EA \u05E8\u05E9\u05D9\u05DE\u05EA \u05D4\u05D9\u05DC\u05D3\u05D9\u05DD");
		childrenManagementButton.setIcon(new ImageIcon("ManageChildrenButton.png"));
		childrenManagementButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				openScreen(e);
			}
		});
		childrenManagementButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		childrenManagementButton.setBounds(12, 52, 213, 25);
		panel.add(childrenManagementButton);
		
		p1List = new JComboBox<>(new Vector<>());
		p1List.setBounds(554, 456, 113, 30);
		p1List.setEnabled(false);
		p1List.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				firstPlayerChoosed(e);
			}
			});
		panel.add(p1List);


		p2List = new JComboBox<>(new Vector<>());
		p2List.setBounds(554, 490, 113, 30);
		p2List.setEnabled(false);
		panel.add(p2List);
	}

	protected void onAgainstRival(ActionEvent e) {
		onAnyKindButtonActionPerformed(e);
		gameSettings.gameType=0;//AgainstRival selected - against rival
	}
	protected void onAgainstComp(ActionEvent e) {
		onAnyKindButtonActionPerformed(e);
		gameSettings.gameType=2;//AgainstComp selected - against rival
		
	}
	protected void onAgainstTime(ActionEvent e) {
		onAnyKindButtonActionPerformed(e);
		gameSettings.gameType=1;//AgainstTime selected - against rival
	}

	protected void openScreen(MouseEvent e) {
		if(!childScreen.frame.isVisible())
		{
			String inputValue = JOptionPane.showInputDialog("הכנס סיסמת מנהל מערכת");
			if(Objects.equals(inputValue,"2301")) {
				childScreen.setMainRef(this);
				childScreen.display();
			}
			else JOptionPane.showMessageDialog(null, "סיסמה לא נכונה", "שגיאה", JOptionPane.ERROR_MESSAGE);
		}
		else childScreen.display();

	}
	
	protected void openAdmin(MouseEvent e) {
		if(!adminScreen.frame.isVisible())
		{
			String inputValue = JOptionPane.showInputDialog("הכנס סיסמת מנהל מערכת");
			if(Objects.equals(inputValue,"2301"))adminScreen.display();
			else JOptionPane.showMessageDialog(null, "סיסמא לא נכונה", "שגיאה", JOptionPane.ERROR_MESSAGE);
		}
		else adminScreen.display();
	}


	protected void onStartGameButtonActionPerformed(MouseEvent e) {
		if(startGame.isEnabled()==true)
		{
			if(easLevel.isSelected()) gameSettings.gameLevel=0;
			else if(medLevel.isSelected())gameSettings.gameLevel=1;
			else gameSettings.gameLevel=2;//hardLevel selected
			gameSettings.player1=p1List.getItemAt(p1List.getSelectedIndex());
			gameSettings.player2=p2List.getItemAt(p2List.getSelectedIndex());
			setChanged();
			notifyObservers(gameSettings);
			frame.setEnabled(false);
		}
	}
	public void setMainScreenEnabled()
	{
		frame.setEnabled(true);
	}
	public void firstPlayerChoosed(ActionEvent e) {
		Vector<String> childrenList2 = new Vector<String>();
		childrenList2.addAll(childrenList1);
		if(childrenList1.size()>1)
		{
			for(int i=0;i<childrenList2.size();i++) 
			{
				if(i==p1List.getSelectedIndex())
				{
					childrenList2.remove(i);
				}
			}
			p2List.setModel(new DefaultComboBoxModel<>(childrenList2));
			p2List.setSelectedIndex(0);
		}
	}
	
	protected void onAnylevelButtonActionPerformed(ActionEvent e) {
		pNames.setEnabled(true);
		firstPNameL.setEnabled(true);
		p1List.setEnabled(true);
		if(twoP==true) {
			secondPNameL.setEnabled(true);
			p2List.setEnabled(true);	
		}
		else 
		{
			p2List.setEnabled(false);
			secondPNameL.setEnabled(false);
		}
		notifyObsToUpdate();
	}
	public void notifyObsToUpdate() {
		setChanged();
	notifyObservers(new StartGame());
	}
	protected void onAnyKindButtonActionPerformed(ActionEvent e) {
		dLevelL.setEnabled(true);
		easLevel.setEnabled(true);
		medLevel.setEnabled(true);
		hardLevel.setEnabled(true);
	}

	protected void twoPlayersRadioButtonActionPerformed(ActionEvent e) {
		if(gKind.isEnabled())
		{
			againstTimeRadioButton.setEnabled(false);
			againstCompRadioButton.setEnabled(false);
			oneP=false;
			oneOnOne.setSelected(true);
			onAgainstRival(e);
			onAnyKindButtonActionPerformed(e);
		}
		else
		{
			gKind.setEnabled(true);
		}
		oneOnOne.setEnabled(true);
		twoP=true;
		if(pNames.isEnabled())
			onAnylevelButtonActionPerformed(e);
	}

	protected void onePlayerRadioButtonActionPerformed(ActionEvent e) {
		if(gKind.isEnabled())
		{
			oneOnOne.setEnabled(false);
			twoP=false;
			againstTimeRadioButton.setSelected(true);
			onAgainstTime(e);
		}
		else
		{
			gKind.setEnabled(true);	
		}
		againstTimeRadioButton.setEnabled(true);
		againstCompRadioButton.setEnabled(true);
		oneP=true;
		if(pNames.isEnabled())
			onAnylevelButtonActionPerformed(e);
	}
	public void updateList(Vector<String> arg) {
		if(!arg.isEmpty())
		{
			childrenList1=arg;
			p1List.setModel(new DefaultComboBoxModel<String>(arg));
			p1List.setSelectedIndex(0);//select the first name on the list
			if(oneP)startGame.setEnabled(true);
			else if(twoP && arg.size()>1)startGame.setEnabled(true);
		}
	}
}
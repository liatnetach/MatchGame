package view;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.SwingConstants;

import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JTextPane;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Observable;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class ChildManagementScreen extends Observable implements View{

	public class Children {
		public Children() {}
		public Children(String n,String id) 
		{
			name=n;
			this.id=id;
		}
		private String name;
		private String id;
		public String getName() {return name;}
		public String getId() {return id;}
	}

	public class UpdateList {}
	private MainScreen mainRef;
	protected JFrame frame;
	private JLabel label,selectChildName,fullnameLabel,idLabel;
	private JComboBox<String> addOrRemoveC;
	JComboBox<String> childList;
	private JTextPane fullNameText,idText;
	private JButton completeTheAction;
//	Data list;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ChildManagementScreen window = new ChildManagementScreen();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ChildManagementScreen() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("ניהול מאגר ילדים");
		frame.setBounds(100, 100, 626, 377);
		frame.getContentPane().setLayout(null);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setBackground(new Color(230,230,250));
		
		label = new JLabel("\u05E0\u05D9\u05D4\u05D5\u05DC \u05E8\u05E9\u05D9\u05DE\u05EA \u05D4\u05D9\u05DC\u05D3\u05D9\u05DD");
		label.setHorizontalAlignment(SwingConstants.RIGHT);
		label.setFont(new Font("Guttman Kav-Light", Font.PLAIN, 34));
		label.setBounds(163, 13, 270, 26);
		frame.getContentPane().add(label);
		
		addOrRemoveC = new JComboBox<>();
		addOrRemoveC.setToolTipText("\u05E4\u05E2\u05D5\u05DC\u05D4");
		addOrRemoveC.setMaximumRowCount(2);
		addOrRemoveC.setModel(new DefaultComboBoxModel<>(new String[] {"\u05D4\u05D5\u05E1\u05E4\u05EA \u05D9\u05DC\u05D3", "\u05DE\u05D7\u05D9\u05E7\u05EA \u05D9\u05DC\u05D3"}));
		addOrRemoveC.setBounds(452, 54, 129, 33);
		frame.getContentPane().add(addOrRemoveC);
		addOrRemoveC.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectAction(e);
			}
		});
		
		fullNameText = new JTextPane();
		fullNameText.setBounds(335, 127, 246, 26);
		frame.getContentPane().add(fullNameText);
		
		idText = new JTextPane();
		idText.setBounds(61, 127, 262, 26);
		frame.getContentPane().add(idText);
		
		idLabel = new JLabel("\u05EA\u05E2\u05D5\u05D3\u05EA \u05D4\u05D6\u05D4\u05D5\u05EA \u05E9\u05DC \u05D4\u05D9\u05DC\u05D3:");
		idLabel.setBounds(172, 98, 151, 16);
		frame.getContentPane().add(idLabel);
		
		fullnameLabel = new JLabel("\u05E9\u05DD \u05D4\u05D9\u05DC\u05D3:");
		fullnameLabel.setBounds(522, 98, 59, 16);
		frame.getContentPane().add(fullnameLabel);
	
		
		selectChildName = new JLabel("\u05D1\u05D7\u05E8 \u05D9\u05DC\u05D3 \u05DC\u05DE\u05D7\u05D9\u05E7\u05D4:");
		selectChildName.setBounds(473, 166, 108, 16);
		frame.getContentPane().add(selectChildName);
		selectChildName.setEnabled(false);
		
		completeTheAction = new JButton("\u05D4\u05E9\u05DC\u05DD \u05D0\u05EA \u05D4\u05E4\u05E2\u05D5\u05DC\u05D4");
		completeTheAction.setIcon(new ImageIcon("CompleteActionButton.png"));
		completeTheAction.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				addOrRemove(e);
			}
		});
		completeTheAction.setBounds(228, 271, 151, 26);
		frame.getContentPane().add(completeTheAction);
		
		childList = new JComboBox<>(new Vector<>());
		//childList.setToolTipText("\u05E4\u05E2\u05D5\u05DC\u05D4");
		childList.setBounds(452, 196, 129, 33);
		frame.getContentPane().add(childList);
		childList.setEnabled(false);
	}

	protected void addOrRemove(MouseEvent e) {
		if(addOrRemoveC.getSelectedItem()=="מחיקת ילד")
		{
			int indexSelected=childList.getSelectedIndex();
			setChanged();
			notifyObservers(indexSelected);			
		}
		else
		{
		    Pattern p = Pattern.compile( "[0-9]" );
		    Matcher m = p.matcher( fullNameText.getText() );
			if(idText.getText().matches("[0-9]+")&&fullNameText.getText().length()!=0&&m.find()==false&&idText.getText().length()==9)
			{
				setChanged();
				notifyObservers(new Children(fullNameText.getText(),idText.getText()));
				idText.setText("");
				fullNameText.setText("");
			}
			else JOptionPane.showMessageDialog(null, "אנא וודא כי ערכי השדות תקינים", "שגיאה", JOptionPane.ERROR_MESSAGE);
		}
		setChanged();
		notifyObservers(new UpdateList());		
		mainRef.notifyObsToUpdate();
		mainRef.p1List.setSelectedIndex(0);//the children list updated
	}
	
	public void display(){frame.setVisible(true);}
	public void undisplay(){frame.setVisible(false);}
	protected void selectAction(ActionEvent e) {
		if(addOrRemoveC.getSelectedItem()=="מחיקת ילד")
		{
			selectChildName.setEnabled(true);
			childList.setEnabled(true);
			fullnameLabel.setEnabled(false);
			idLabel.setEnabled(false);
			idText.setEnabled(false);
			idText.setText("");
			fullNameText.setEnabled(false);
			fullNameText.setText("");
			setChanged();
			notifyObservers(new UpdateList());
		}
		else
		{
			selectChildName.setEnabled(false);
			childList.setEnabled(false);
			fullnameLabel.setEnabled(true);
			idLabel.setEnabled(true);
			idText.setEnabled(true);
			fullNameText.setEnabled(true);
		}

	}

	public void updateList(Vector<String> arg) {
		
		childList.setModel(new DefaultComboBoxModel<>(arg));
		
	}

	public void ShowAddChildResponse(String arg) {
		
		JOptionPane.showMessageDialog(null, arg, "הוספת/מחיקת ילד", JOptionPane.INFORMATION_MESSAGE); // Pop up message
	}

	public void setMainRef(MainScreen mainScreen) {
		mainRef=mainScreen;
	}
}
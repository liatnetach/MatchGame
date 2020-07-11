package view;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.*;    
import java.awt.event.*;
import java.util.Date;
import java.util.Observable;
import java.util.Vector;

import com.toedter.calendar.JDateChooser;

import org.jfree.data.category.DefaultCategoryDataset;

public class StatisticsScreen extends Observable implements View{

	protected JFrame frame;
	JRadioButton allkids,onekid;
	JLabel startdateall,enddateall,startdateone,enddateone,namekid;
	JDateChooser chooserdatestartall,chooserdateendall,chooserdatestartone,chooserdateendone;
	ButtonGroup bp;
	private JComboBox<String> combobox;
	JButton showtable,showgraph;
	StatisticsScreen graphing;
	private LinearStats linear=new LinearStats();
	private TableStats table=new TableStats();
	public class UpdateList{};
	public class LinearStats
	{	
		int index;
		Date start;
		Date end;
		public int getIndex() {
			return index;
		}
		public void setIndex(int index)
		{
			this.index=index;
		}
		public Date getfirstD() {
			return start;
		}
		public Date getSecondD() {
			return end;
		}
	};
	public class TableStats{
		Date start;
		Date end;
		public Date getfirstD() {
			return start;
		}
		public Date getSecondD() {
			return end;
		}
	};
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					StatisticsScreen window = new StatisticsScreen();
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
	public StatisticsScreen() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(170, 100, 517, 339);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setTitle( "מסך סטיסטיקות");
		frame.getContentPane().setBackground(new Color(230,230,250));
		
		JLabel lblNewLabel = new JLabel("סטטיסטיקות");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 22));
		lblNewLabel.setForeground(new Color(153,50,204));
		lblNewLabel.setBounds(210, 0, 150, 38);

		frame.getContentPane().add(lblNewLabel);
		
		JLabel informationL = new JLabel("אנא בחר טווח תאריכים וסוג סטטיסטיקה אותה  תרצה להציג");
		informationL.setFont(new Font("Tahoma", Font.PLAIN,12));
		informationL.setForeground(Color.RED);
		informationL.setBounds(160, 243, 330, 38);

		frame.getContentPane().add(informationL);
		
		JLabel lblNewLabel_1 = new JLabel("סוג הדוח להצגה:");
		lblNewLabel_1.setFont(new Font("Tahoma",Font.PLAIN,10));
		lblNewLabel_1.setBounds(370, 36, 115, 24);
		frame.getContentPane().add(lblNewLabel_1);
	
		allkids = new JRadioButton("היסטוריית משחקים");
		allkids.setHorizontalTextPosition(JRadioButton.LEADING);
		allkids.setHorizontalAlignment(JRadioButton.TRAILING);
		allkids.setBackground(new Color(230,230,250));
		allkids.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showgraph.setEnabled(false);
				showtable.setEnabled(true);
				bp=new ButtonGroup();
				bp.add(allkids);bp.add(onekid);
				if(allkids.isSelected())
				{
					chooserdatestartall.setEnabled(true);
					chooserdateendall.setEnabled(true);
					startdateall.setEnabled(true);
					enddateall.setEnabled(true);
		    	}
				else
				{
					chooserdatestartall.setEnabled(false);
					chooserdateendall.setEnabled(false);
					startdateall.setEnabled(false);
					enddateall.setEnabled(false);
				}
				if(onekid.isSelected())
				{
					chooserdatestartone.setEnabled(true);
					chooserdateendone.setEnabled(true);
					namekid.setEnabled(true);
					startdateone.setEnabled(true);
					enddateone.setEnabled(true);
					combobox.setEnabled(true);
				}
				else
				{
					chooserdatestartone.setEnabled(false);
					chooserdateendone.setEnabled(false);
					namekid.setEnabled(false);
					startdateone.setEnabled(false);
					enddateone.setEnabled(false);
					combobox.setEnabled(false);
				}
			}});
		allkids.setBounds(333, 63, 130, 23);
		frame.getContentPane().add(allkids);
		
		onekid = new JRadioButton("גרף התקדמות");
		onekid.setHorizontalTextPosition(JRadioButton.LEADING);
		onekid.setHorizontalAlignment(JRadioButton.TRAILING);
		onekid.setBackground(new Color(230,230,250));
		onekid.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showgraph.setEnabled(true);
				showtable.setEnabled(false);
				bp=new ButtonGroup();
				bp.add(allkids);bp.add(onekid);
				if(onekid.isSelected())
				{
					chooserdatestartone.setEnabled(true);
					chooserdateendone.setEnabled(true);
					namekid.setEnabled(true);
					startdateone.setEnabled(true);
					enddateone.setEnabled(true);
					combobox.setEnabled(true);
				}
				else
				{
					chooserdatestartone.setEnabled(false);
					chooserdateendone.setEnabled(false);
					namekid.setEnabled(false);
					startdateone.setEnabled(false);
					enddateone.setEnabled(false);
					combobox.setEnabled(false);
				}
				if(allkids.isSelected())
				{
					chooserdatestartall.setEnabled(true);
					chooserdateendall.setEnabled(true);
					startdateall.setEnabled(true);
					enddateall.setEnabled(true);
		    	}
				else
				{
					chooserdatestartall.setEnabled(false);
					chooserdateendall.setEnabled(false);
					startdateall.setEnabled(false);
					enddateall.setEnabled(false);
				}
				setChanged();
				notifyObservers(new UpdateList());
			}
		});
		onekid.setBounds(333, 83, 130, 23);
		frame.getContentPane().add(onekid);
		
		showgraph = new JButton("הצג את ההתקדמות");
		showgraph.setBounds(205, 181, 119, 23);
		showgraph.setIcon(new ImageIcon("ShowGraphButton.png"));
		frame.getContentPane().add(showgraph);
		showgraph.setEnabled(false);
		showgraph.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				linear.start=chooserdatestartone.getDate();
				linear.end=chooserdateendone.getDate();
				if(linear.start!=null&&linear.end!=null&&combobox.getSelectedItem()!=null)
				{				
					linear.start.setTime(0);
					linear.end.setTime(0);
					setChanged();
					notifyObservers(linear);
				}
				else JOptionPane.showMessageDialog(null, "אנא וודא כי הזנת תאריך התחלה ותאריך סוף ושבחרת ילד", "חלה טעות", JOptionPane.INFORMATION_MESSAGE); // Pop up message
			}
			});
		
		
		showtable = new JButton("הצג היסטוריית משחקים");
		showtable.setBounds(190, 215, 155, 23);
		showtable.setIcon(new ImageIcon("ShowTableButton.png"));
		frame.getContentPane().add(showtable);
		showtable.setEnabled(false);
		showtable.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				table.start=chooserdatestartall.getDate();
				table.end=chooserdateendall.getDate();
				if(table.start!=null&&table.end!=null)
				{
					setChanged();
					notifyObservers(table);
				}
				else JOptionPane.showMessageDialog(null, "אנא וודא כי הזנת תאריך התחלה ותאריך סוף", "לא נבחר טווח תאריכים", JOptionPane.INFORMATION_MESSAGE); // Pop up message
			}
			});
		
		startdateall = new JLabel("תאריך התחלה:");
		startdateall.setEnabled(false);
		startdateall.setBounds(250, 45, 72, 14);
		frame.getContentPane().add(startdateall);
		
		enddateall = new JLabel("תאריך סוף:");
		enddateall.setEnabled(false);
		enddateall.setBounds(265, 88, 64, 14);
		frame.getContentPane().add(enddateall);
		
		chooserdatestartall = new JDateChooser();
		chooserdatestartall.setEnabled(false);
		chooserdatestartall.setBounds(204, 67, 126, 19);
		frame.getContentPane().add(chooserdatestartall);
		
		
		chooserdateendall = new JDateChooser();
		chooserdateendall.setEnabled(false);
		chooserdateendall.setBounds(204, 104, 126, 19);
		frame.getContentPane().add(chooserdateendall);
		
		namekid = new JLabel("בחר ילד:");
		namekid.setEnabled(false);
		namekid.setBounds(108, 37, 46, 14);
		frame.getContentPane().add(namekid);
		
		combobox= new JComboBox<>(new Vector<>());
		combobox.setEnabled(false);
		combobox.setBounds(27,60,126,20);
		frame.getContentPane().add(combobox);
		
		combobox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				combo(e);
			}
			});
		startdateone = new JLabel("תאריך התחלה:");
		startdateone.setEnabled(false);
		startdateone.setBounds(80, 85, 100, 14);
		frame.getContentPane().add(startdateone);
		
		chooserdatestartone = new JDateChooser();
		chooserdatestartone.setEnabled(false);
		chooserdatestartone.setBounds(27, 100, 126, 19);
		frame.getContentPane().add(chooserdatestartone);
		
		enddateone = new JLabel("תאריך סוף:");
		enddateone.setEnabled(false);
		enddateone.setBounds(95, 120, 100, 14);
		frame.getContentPane().add(enddateone);
		
		chooserdateendone = new JDateChooser();
		chooserdateendone.setEnabled(false);
		chooserdateendone.setBounds(27, 135, 126, 19);
		frame.getContentPane().add(chooserdateendone);
		frame.setVisible(false);
	}

	public void updateList(Vector<String> arg) 
	{
		
		combobox.setModel(new DefaultComboBoxModel<>(arg));
	}

	public void Showgraph(DefaultCategoryDataset arg) 
	{	
		 SwingUtilities.invokeLater(() -> {  
		      LineChart example = new LineChart(arg);
		      example.setAlwaysOnTop(true);  
		      example.pack();  
		      example.setSize(600, 400);  
		      example.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);  
		      example.setVisible(true);  
		    });  
	}

	public void Showtable(JTable jt) {
		 JFrame f;
		 f=new JFrame(); 	
		JScrollPane sp=new JScrollPane(jt);    
		 f.add(sp);          
		 f.setSize(300,400);    
		 f.setVisible(true);
	}
	
	public void combo(ActionEvent e)
	{
		int indexSelected=combobox.getSelectedIndex();
		linear.setIndex(indexSelected);
	}
	
	public void display(){frame.setVisible(true);}
	public void undisplay(){frame.setVisible(false);}
}


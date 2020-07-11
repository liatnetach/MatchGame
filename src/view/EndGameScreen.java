package view;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.SwingConstants;

public class EndGameScreen
{
	private JFrame frame;
	private JLabel p1Name;
	private JLabel p2Name;
	private JLabel p1Score;
	private JLabel p2Score;
	private JLabel congratsLabel;
	//private JLabel winnerIs;
	
	public EndGameScreen(Builder bld) 
	{
		frame = new JFrame();
		frame.setBounds(400,100,1000,800);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setResizable(false);
		
		ImagePanel panel = new ImagePanel(new ImageIcon("end_game_photo1.jpg").getImage());
		//ImagePanel panel = new ImagePanel(new ImageIcon("BackgroundPic.jpg").getImage());
		//panel.setBackground(Color.WHITE);
		panel.setBounds(0,0, 1100, 800);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		//Player 1 details
		p1Name = new JLabel(bld.p1Name);
		p1Name.setPreferredSize(new Dimension(400,100));
		p1Name.setHorizontalAlignment(SwingConstants.RIGHT);
		p1Name.setFont(new Font("Tahoma", Font.PLAIN, 23));
		p1Name.setBounds(300, 80, 254, 43);
		panel.add(p1Name);
		
		p1Score = new JLabel(Integer.toString(bld.p1Score));
		p1Score.setHorizontalAlignment(SwingConstants.RIGHT);
		p1Score.setFont(new Font("Tahoma", Font.PLAIN, 23));
		p1Score.setBounds(535, 92, 69, 20);
		panel.add(p1Score);
		
		//Player 2 details
		if(bld.is2Players==true)
		{
			p2Name = new JLabel(bld.p2Name);
			p2Name.setHorizontalAlignment(SwingConstants.RIGHT);
			p2Name.setFont(new Font("Tahoma", Font.PLAIN, 23));
			p2Name.setBounds(300, 130, 254, 33);
			p2Name.setPreferredSize(new Dimension(400,100));
			panel.add(p2Name);
			
			p2Score = new JLabel(Integer.toString(bld.p2Score));
			p2Score.setHorizontalAlignment(SwingConstants.RIGHT);

			p2Score.setFont(new Font("Tahoma", Font.PLAIN, 23));
			p2Score.setBounds(535, 135, 69, 20);
			panel.add(p2Score);
		}
		
		congratsLabel = new JLabel("כל הכבוד !!!");
		congratsLabel.setForeground(new Color(0, 100, 0));
		congratsLabel.setBounds(385, 650, 230, 43);
		congratsLabel.setFont(new Font("Tahoma", Font.BOLD, 30));
		panel.add(congratsLabel);
		
		//JLabel lblWinner = new JLabel("Winner is:");
		
		/*winnerIs = new JLabel("The winner is");
		lblWinner.setBounds(381, 346, 169, 40);
		panel.add(lblWinner);
		lblWinner.setFont(new Font("Tahoma", Font.PLAIN, 24));*/
		
		frame.setVisible(true);
	}
	
	public static class Builder
	{
		private String p1Name;
		private String p2Name;
		private int p1Score;
		private int p2Score;
		private boolean is2Players=false;
		
		public EndGameScreen build()
		{
			return new EndGameScreen(this);
		}
		public Builder setP1Name(String name)
		{
			this.p1Name=name;
			return this;
		}
		public Builder setP2Name(String name)
		{
			this.p2Name=name;
			is2Players=true;
			return this;
		}
		public Builder setP1Score(int score)
		{
			this.p1Score=score;
			return this;
		}
		public Builder setP2Score(int score)
		{
			is2Players=true;
			this.p2Score=score;
			return this;
		}
	}
}
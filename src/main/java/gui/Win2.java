package gui;

import java.awt.Font;
import java.awt.GridLayout;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Win2 extends JFrame{
	public static Font fontCN = new Font("宋体", Font.PLAIN, 16); // 中文字体
	public static Font fontEN = new Font("Times New Roman", Font.PLAIN,25); //英文字体
	private JButton jbt = new JButton("识别句子");
	private JTextField jtf = new JTextField("i*i+i");
	JTextArea jta = new JTextArea("");
	JPanel panel = new JPanel();
	JScrollPane scroll = new JScrollPane(jta);
	JTextField[][] grid = null;
	int tableRow = 6;
	int tableCol = 8;
	
	public Win2() throws IOException
	{

		this.setSize(700,1100);
		this.setVisible(true);
		this.setLayout(null);
		JLabel l1 = new JLabel("消除左递归，提取左公因子");
		l1.setFont(fontCN);
		l1.setBounds(15,15,700,30);
		this.add(l1);
		
		jta.setText(getNewGrammarProduction());
		scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		jta.setFont(fontEN);
		scroll.setBounds(15, 60, 650, 150);
		//jta.setBounds(15, 60, 650, 150);
		this.add(scroll);
		
		JLabel l2 = new JLabel("输入一个句子：");
		l2.setFont(fontCN);
		l2.setBounds(15, 210+15, 7*16, 30);
		this.add(l2);
		
		jtf.setBounds(7*16+15+15, 210+15, 150, 30);
		jtf.setFont(fontEN);
		this.add(jtf);
		
		jbt.setBounds(7*16+30+15+150, 210+15,150, 30);
		this.add(jbt);
		
		panel.setLayout(new GridLayout(tableRow,tableCol));
		panel.setBounds(15, 260+15, 650,700);
		grid = new JTextField[tableRow][tableCol];
		for(int i=0;i<grid.length;i++)
		{
			for(int j=0;j<grid[i].length;j++)
			{
				grid[i][j] = new JTextField(" ");
				grid[i][j].setFont(fontEN);
				panel.add(grid[i][j]);
			}
		}
		this.add(panel);
		this.showTable();
	}
	
	private String getNewGrammarProduction() throws IOException
	{
		BufferedReader br = new BufferedReader(new FileReader("tmpfile.txt"));
		String res = "";
		String s = "";
		while((s=br.readLine())!=null)
			res+=s+"\n";
		return res;
	}
	
	private String[][] getTable() throws IOException
	{
		String[][] table = new String[tableRow][tableCol];
		for(int i=0;i<tableRow;i++)
		{
			for(int j=0;j<tableCol;j++)
				table[i][j] = "";
		}
		BufferedReader br = new BufferedReader(new FileReader("table.txt"));
		String line = "";
		int i=0;
		while((line = br.readLine()) != null)
		{
			table[i++] = line.split("\\ ");
		}
//		for(String[] a:table)
//		{
//			for(String s:a)
//				System.out.print(s);
//		}
		return table;
	}
	
	private void showTable() throws IOException
	{
		String[][] table = this.getTable();
		for(int i=0;i<table.length;i++)
		{
			for(int j=0;j<table[i].length;j++)
			{
				if(!table[i][j].equals("@"))
					grid[i][j].setText(table[i][j]);
				else if(i!=0)
					grid[i][j].setText("Error");
			}
		}
	}
}

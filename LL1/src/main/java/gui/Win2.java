package gui;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Win2 extends JFrame{
	public static Font fontCN = new Font("宋体", Font.PLAIN, 16); // 中文字体
	public static Font biggerfontCN = new Font("宋体", Font.PLAIN, 25); // 中文字体
	public static Font fontEN = new Font("Times New Roman", Font.PLAIN,25); //英文字体
	private JButton jbt = new JButton("识别句子");
	private JTextField jtf = new JTextField("i*i+i");
	private JPanel stackPanel = new JPanel();
	private JTextArea stackArea = new JTextArea();
	JTextArea jta = new JTextArea("");
	JPanel panel = new JPanel();
	JScrollPane scroll = new JScrollPane(jta);
	JTextField[][] grid = null;
	int tableRow = 6;
	int tableCol = 8;
	
	JTextArea jta2 = new JTextArea("");
	JScrollPane jsp = new JScrollPane(jta2);
	
	public Win2() throws IOException
	{
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setSize(1300, 950);
		this.setVisible(true);
		this.setLayout(null);
		JLabel l1 = new JLabel("消除左递归，提取左公因子");
		l1.setFont(fontCN);
		l1.setBounds(15,15,700,30);
		this.add(l1);
		int down = 200;
		jta.setText(getNewGrammarProduction());
		scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		jta.setFont(fontEN);
		scroll.setBounds(15, 60, 650, 150);
		//jta.setBounds(15, 60, 650, 150);
		this.add(scroll);
		
		stackArea.setBounds(15+650+50, 60, 500, 800);
		stackArea.setBackground(getBackground());
		stackArea.setFont(biggerfontCN);
		stackArea.setBorder(BorderFactory.createTitledBorder(""));
		this.add(stackArea);
		
		JLabel l3 = new JLabel("First集与Follow集合");
		l3.setBounds(15,60+150,600,30);
		l3.setFont(fontCN);
		this.add(l3);
		
		jta2.setText(this.getFirstSetString()+this.getFollowString());
		jsp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		jsp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		jta2.setFont(fontEN);
		jsp.setBounds(15,210+30+15, 650, 150);
		this.add(jsp);
		
		JLabel l2 = new JLabel("输入一个句子：");
		l2.setFont(fontCN);
		l2.setBounds(15, 210+15+down, 7*16, 30);
		this.add(l2);
		
		jtf.setBounds(7*16+15+15, 210+15+down, 150, 30);
		jtf.setFont(fontEN);
		this.add(jtf);
		
		jbt.setBounds(7*16+30+15+150, 210+15+down,150, 30);
		this.add(jbt);
		jbt.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				try {
					String s = getStack();
					stackArea.setText(s);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
		panel.setLayout(new GridLayout(tableRow,tableCol));
		panel.setBounds(15, 260+15+down, 650,400);
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
	private String getStack() throws IOException
	{
		BufferedReader br = new BufferedReader(new FileReader("stack.txt"));
		String s = "";
		String res = "栈变化过程\n";
		while((s = br.readLine()) != null)
		{
			res+=s+"\n";
			System.out.println(s);
		}
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
	
	private String getFirstSetString() throws IOException
	{
		BufferedReader br = new BufferedReader(new FileReader("first.txt"));
		String res = "";
		String s = "";
		while((s = br.readLine())!=null)
		{
			String[] tmp = s.split("\\ ");
			res += this.firstFomat(tmp)+"\n";
		}
		System.out.println(res);
		return res;
	}
	
	private String getFollowString() throws IOException
	{
		String res = "";
		BufferedReader br = new BufferedReader(new FileReader("follow.txt"));
		String s = "";
		while((s=br.readLine())!=null)
		{
			String[] tmp = s.split("\\ ");
			res += this.followFormat(tmp)+"\n";
		}
		
		return res;
	}
	
	private String firstFomat(String[] t)
	{
		String res = "First("+t[0]+")={";
		for(int i=1;i<t.length;i++)
		{
			res+=t[i]+",";
		}
		res = res.substring(0, res.length()-1);
		res+="}";
		return res;
	}
	
	private String followFormat(String[] t)
	{
		String res = "Follow("+t[0]+")={";
		for(int i=0;i<t[1].length();i++)
		{
			res += String.valueOf(t[1].charAt(i))+",";
		}
		res = res.substring(0,res.length()-1);
		res+="}";
		return res;
	}
}

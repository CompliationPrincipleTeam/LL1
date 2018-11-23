package gui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import dataStructure.Production;

public class MainWin extends JFrame{
	public static Font fontCN = new Font("宋体", Font.PLAIN, 16); // 中文字体
	public static Font fontEN = new Font("Times New Roman", Font.PLAIN,30); //英文字体
	ArrayList<String> vn = new ArrayList<String>();
	ArrayList<String> vt = new ArrayList<String>();
	ArrayList<Production> production1 = new ArrayList<Production>();
	ArrayList<Production> production2 = new ArrayList<Production>();
	String filename = "grammar1.txt";
	
	private JTextField inputFile = new JTextField(filename);
	private JButton convertJbt = new JButton("转换");
	private JButton loadJbt = new JButton("加载文件");
	public MainWin() throws IOException
	{
		this.readGrammar(filename);
		this.setLayout(null);
		this.setTitle("LL(1) Grammar");
		this.setSize(700, 700);
		this.setVisible(true);
		JLabel label1 = new JLabel("输入文件名：");
		label1.setBounds(15,15,100,30);
		this.add(label1);
		
		inputFile.setBounds(115, 15, 200, 30);
		inputFile.setFont(fontEN);
		this.add(inputFile);
		
		this.loadJbt.setFont(fontCN);
		this.loadJbt.setBounds(315+20,15,100,30);
		this.add(loadJbt);
		
		JLabel label2 = new JLabel("终结符集合:");
		label2.setBounds(15,45+15, 100, 30);
		this.add(label2);
		
		final JTextField jtf1 = new JTextField("");
		jtf1.setFont(fontEN);
		jtf1.setBounds(115, 60, 400, 30);
		this.add(jtf1);
		
		JLabel label3 = new JLabel("非终结符集合:");
		label3.setBounds(15,90+15, 100, 30);
		this.add(label3);
		
		final JTextField jtf2 = new JTextField("");
		jtf2.setFont(fontEN);
		jtf2.setBounds(115, 105, 400, 30);
		this.add(jtf2);
		
		JLabel label4 = new JLabel("产生式");
		label4.setBounds(15,150,100,30);
		this.add(label4);
		
		
		final JTextArea jta = new JTextArea("");
		jta.setFont(fontEN);
		//jta.setBackground(getBackground());
		jta.setBounds(15, 200, 600, 300);
		this.add(jta);
		
		convertJbt.setFont(fontCN);
		convertJbt.setBounds(15, 510, 200, 100);
		this.add(convertJbt);
		
		convertJbt.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				try {
					Win2 win2 = new Win2();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
		loadJbt.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				filename = inputFile.getText();
				try {
					readGrammar(filename);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				jtf1.setText("{"+list2String(vt)+"}");
				jtf2.setText("{"+list2String(vn)+"}");
				String ps = "";
				for(Production p: production1)
				{
					ps = ps + p.leftPart + "->" +p.rightPart + "\n";
				}
				jta.setText(ps);
				
			}
		});
		
		
		
	}
	private String list2String(ArrayList<String> list)
	{
		String res = "";
		for(String s:list)
			res=res+s+",";
		res = res.substring(0,res.length()-1);
		return res;
	}
	private void readGrammar(String filename) throws IOException
	{
		vt.clear();
		vn.clear();
		this.production1.clear();
		this.production2.clear();
		BufferedReader br = new BufferedReader(new FileReader(filename));
		String s = "";
		s = br.readLine();	//non-terminal
		String[] t = s.split("\\ ");
		for(int i=0;i<t.length;i++)
		{
			this.vn.add(t[i]);
		}
		
		s = br.readLine();	//terminal
		t = s.split("\\ ");
		for(int i=0;i<t.length;i++)
		{
			this.vt.add(t[i]);
		}
		
		while((s=br.readLine())!=null)
		{
			t = s.split("\\ ");
			String left = t[0];
			String right = t[1];
			this.production1.add(new Production(left,right));
			t = right.split("\\|");
			for(int i=0;i<t.length;i++)
				this.production2.add(new Production(left,t[i]));
		}
		
	}
	
	public static void main(String[] args) throws IOException
	{
		MainWin win = new MainWin();
		
	}

}

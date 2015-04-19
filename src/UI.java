import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;


public class UI extends JFrame{
	
	JTable table;
	JPanel buttonPanel;
	JScrollPane pane;
	TableModel model;
	String[] clomnName={"Name","Price","URL"};
	JButton add;
	JButton search;
	JButton showAll;
	
	UI()
	{
		Bussiness temp=new Bussiness();
		List<CellPhone> info=temp.GetAllInfo();
		String[][] data=new String[info.size()][];
		for(int i=0;i<info.size();i++)
		{
			data[i]=new String[3];
			data[i][0]=info.get(i).getName();
			data[i][1]=info.get(i).getPrice();
			data[i][2]=info.get(i).getURL();
		}
		
		model=new DefaultTableModel(data, clomnName){
			public boolean isCellEditable(int r, int c)
			{
				return false;
			}
		};
        this.setLayout(new BorderLayout());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container contentPanel=this.getContentPane();
		table=new JTable(model);
		buttonPanel=new JPanel();
		pane=new JScrollPane(table);
		pane.setViewportView(table);
		add=new JButton("Add");
		search=new JButton("Search");
		showAll=new JButton("Show All");
		contentPanel.add(pane);
		this.buttonPanel.add(this.add);
		this.buttonPanel.add(this.search);
		this.buttonPanel.add(this.showAll);
		contentPanel.add(this.buttonPanel, BorderLayout.PAGE_END);
		this.setSize(500, 500);
		
		this.setVisible(true);
		
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2)

				{

					int row = ((JTable) e.getSource()).rowAtPoint(e.getPoint()); 

					int col = ((JTable) e.getSource()).columnAtPoint(e.getPoint()); 

					if(col==2)
					{
						String URIt=(model.getValueAt(row, col).toString());
						try {
							URI uri = new URI(URIt);
							Desktop.getDesktop().browse(uri);
							} 
						catch (URISyntaxException ex) {
							ex.printStackTrace();
							}
						catch (IOException ex) {
							ex.printStackTrace();
							}
					}

				} else
					return;
			}
		});
			


		add.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String URL = JOptionPane.showInputDialog(null,
						"Input URL you wanna add:", "URL",
						JOptionPane.PLAIN_MESSAGE);
				try {
					Spider ph = new Spider(
							"?",
							URL);

					Thread search = new Thread(ph);
					search.start();
					search.join();
					String result[]=new String[3];
					result=ph.getResult();
					Bussiness newB=new Bussiness();
					if(result[0]!=null&&result[1]!=null){
					newB.Ins(result);
					System.out.println("success");
					updateTable(newB.GetAllInfo());
					}
					else{
						System.out.println("flase");
					}
					updateTable(newB.GetAllInfo());
				} catch (Exception ex) {
//					JOptionPane.showMessageDialog(null, "Invalid URL",
//							"Please provide a valid URL",
//							JOptionPane.ERROR_MESSAGE);
				}
			}

		});
		search.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				String key = JOptionPane.showInputDialog(null,
						"Input the keyword you wanna search:", "URL",
						JOptionPane.PLAIN_MESSAGE);
				if(key==null)
				{
					JOptionPane.showMessageDialog(null, "No keyword",
					"Please provide a keyword",
					JOptionPane.ERROR_MESSAGE);
				}
				else{
					Bussiness newB=new Bussiness();
					updateTable(newB.Search(key));
				}
				
			}
			
		});
		showAll.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				Bussiness newB=new Bussiness();
				updateTable(newB.GetAllInfo());
				
			}
			
		});
		
	}
	public void updateTable(List<CellPhone> info)
	{
		
		
		String[][] datat=new String[info.size()][];
		for(int i=0;i<info.size();i++)
		{
			datat[i]=new String[3];
			datat[i][0]=info.get(i).getName();
			datat[i][1]=info.get(i).getPrice();
			datat[i][2]=info.get(i).getURL();
		}
		model=new DefaultTableModel(datat, clomnName){
			public boolean isCellEditable(int r, int c)
			{
				return false;
			}
		};
		table.setModel(model);
		table.updateUI();
	}

}

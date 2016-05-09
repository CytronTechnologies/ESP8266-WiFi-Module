import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;



public class MainClass extends JFrame{
	
	private static final String KBSEXT = "htm";
	public String filePath;
	public String getString;
	JTextField text;
	
	public MainClass()
	{
		JPanel mainPanel = new JPanel();
		setContentPane(mainPanel);
		mainPanel.setLayout(new BorderLayout());
		
		text = new JTextField();
		text.setText("");
		JButton fileOpen = new JButton("...");
		fileOpen.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				filePath = searchFile();
				text.setText(filePath);
				
			}
		});
		
		JButton fileGen = new JButton("Generate H file");
		fileGen.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				String temp;
				String filename = filePath.replace(".htm", "");
				
				try {
					getString = readFileToString(filePath);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				temp = "#ifndef "+"header_h\r\n";
				temp +="#define "+"header_h\r\n\r\n";
				
				temp +="String file1=";
				//temp += "\""+getString+"\";";
				temp += getString+";";
				temp += "\r\n\r\n#endif";
				
				writeFile(temp,filename);
				
			}
		});
		
		JPanel panel1 = new JPanel();
		panel1.setLayout(new BorderLayout());
		
		JPanel labelPanel = new JPanel();
		JLabel setName = new JLabel("Select a file:");
		labelPanel.add(setName);
		
		panel1.add(labelPanel,BorderLayout.LINE_START);

		JPanel openFilePanel = new JPanel();
		openFilePanel.setLayout(new BoxLayout(openFilePanel,BoxLayout.LINE_AXIS));
		openFilePanel.add(Box.createHorizontalGlue());
		openFilePanel.add(text);		
		openFilePanel.add(fileOpen);	
		
		
		panel1.add(openFilePanel,BorderLayout.PAGE_END);
		
		JPanel genFilePanel = new JPanel();
		genFilePanel.add(fileGen);
		mainPanel.add(panel1,BorderLayout.PAGE_START);
		mainPanel.add(genFilePanel,BorderLayout.PAGE_END);
		
		setTitle("Convert HTML to String");
		setResizable(false);
		setSize(400, 120);
		setVisible(true);
		//pack();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		

	}
	
	
	public static void main(String[] args){

		new MainClass();

	}
	
	public static String searchFile()
	{
		JFileChooser fc = new JFileChooser();		
		File f = new File("");
		String path = f.getAbsolutePath();
		fc.setCurrentDirectory(new File(path));

		fc.setMultiSelectionEnabled(false);
		fc.setFileFilter(new FileNameExtensionFilter(".htm", KBSEXT));

		String knowledgePath = "";

			//JOptionPane.showMessageDialog(null, "Please select ." + KBSEXT + " file.", "Info", JOptionPane.PLAIN_MESSAGE);
			int returnVal = fc.showOpenDialog(null);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				knowledgePath = fc.getSelectedFile().getAbsolutePath();
				if(!knowledgePath.contains(KBSEXT))
				{
					knowledgePath = "";
					JOptionPane.showMessageDialog(null, "Please select ." + KBSEXT + " file.", "Info", JOptionPane.PLAIN_MESSAGE);
					searchFile();
				}
		}
		
		return knowledgePath;
		
	}
	
	
	private String readFileToString(String filePath) throws IOException
	{

		        StringBuffer fileData = new StringBuffer(1000);
				
		        String readData ="";
		        BufferedReader reader = new BufferedReader(
		                new FileReader(filePath));
		        
		        while((readData=reader.readLine()) != null){
		        	
//		        	if(readData.contains(";"))
//		        		readData = readData.replaceAll(";","\\;");
//		        	if(readData.contains("\""))
//		        		readData = readData.replaceAll("\"","\\\"");	
		        	
		        	//readData= readData.trim();
		        	if(readData.contains("\\"))
			        	readData = readData.replace("\\","\\\\");
		        	if(readData.contains("\""))
			        	readData = readData.replace("\"","\\\"");
		        	readData += "\\r\\n";
//		        	if(readData.contains(";"))
//			        	readData = readData.replace(";","\\;");
		        			
		            fileData.append("\r\n"+"\""+readData+"\"");
		        }

		        reader.close();
		        
		        System.out.println(fileData);
		        return fileData.toString();
		    }
	
	
	public void writeFile(String exp,String filename){
		try {
			
			File file = new File(filename+".h");

			// if file doesn't exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}
			if(exp!=null){
				FileWriter fw = new FileWriter(file.getAbsoluteFile());
				BufferedWriter bw = new BufferedWriter(fw);
				bw.write(exp);
				bw.close();
			}
		} 
		
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	

}



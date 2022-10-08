package is_lab1;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

public class Main {

	static List<String> headers = Arrays.asList(
		"Producent",
		"wielkość matrycy",
		"rozdzielczość",
		"typ matrycy",
		"czy dotykowy ekran",
		"procesor",
		"liczba rdzeni fizyczynch",
		"taktowanie",
		"RAM",
		"pojemność dysku",
		"typ dysku",
		"karta graficzna",
		"pamięć karty graficznej",
		"system operacyjny",
		"Napęd optyczny");
	
    public static void main(String args[]) {
        JFrame frame = new JFrame("Integracja systemów - Ryszard Rogalski");
        
        JLabel selected_file = new JLabel();
        
        frame.setSize(1920, 1080);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
        //frame.setLayout(new GridLayout(2,2));

        JButton btn_textFile = new JButton("Select File (*.txt)");
        btn_textFile.setSize(100, 100);
        JButton btn_xmlFile = new JButton("Select File (*.xml)");
        btn_xmlFile.setSize(100, 100);

        JButton btn_save_textFile = new JButton("Save File (*.txt)");
        btn_save_textFile.setSize(100, 100);

        JButton btn_save_xmlFile = new JButton("Save File (*.xml)");
        btn_save_xmlFile.setSize(100, 100);

        
        JPanel buttonPanel = new JPanel();
        buttonPanel.setPreferredSize(new Dimension(750, 50));
        buttonPanel.setMaximumSize(buttonPanel.getPreferredSize()); 
        buttonPanel.setMinimumSize(buttonPanel.getPreferredSize());
        
        buttonPanel.setLayout(new GridLayout(1, 4));
        buttonPanel.setSize(1920, 150);
        buttonPanel.setBorder(BorderFactory.createTitledBorder("Actions"));
        buttonPanel.add(btn_textFile);
        buttonPanel.add(btn_xmlFile);
        buttonPanel.add(btn_save_textFile);
        buttonPanel.add(btn_save_xmlFile);
        
        frame.add(buttonPanel);
        
        
        JPanel panel = new JPanel();   
        panel.setLayout(new GridLayout(0, 1));
       
        panel.setBorder(BorderFactory.createTitledBorder("Data"));
        
        JTable table = new JTable();
        JScrollPane sp = new JScrollPane(table);
        
        //frame.add(selected_file);
        panel.add(sp);
        frame.add(panel);
        
        DefaultTableModel initModel = new DefaultTableModel();

        for(String header : headers) {
        	System.out.println(header);
        	initModel.addColumn(header);
        }
        table.setModel(initModel);
        
        
        panel.add(table.getTableHeader(), BorderLayout.CENTER);

        btn_textFile.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser filechooser = new JFileChooser();

                int i = filechooser.showOpenDialog(null);
                if (i == JFileChooser.APPROVE_OPTION) {
                    File f = filechooser.getSelectedFile();
                    String filepath = f.getPath();
                    String fi = f.getName();
                    //Parsing CSV Data
                    System.out.print(filepath);
                    selected_file.setText(fi);
                    DefaultTableModel csv_data = new DefaultTableModel();

                    try {

                        int start = 0;
                        InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(filepath));
                        CSVParser csvParser = CSVFormat.EXCEL.withDelimiter(';').parse(inputStreamReader);
  
                        for(String header : headers) {
                        	System.out.println(header);
                        	csv_data.addColumn(header);
                        }
                        
                        for (CSVRecord csvRecord : csvParser) {
                        	Vector<String> row = new Vector<String>();
                        	for(Integer r = 0 ; r < 15 ; r++) {
                        		 row.add(csvRecord.get(r));
                        	}
                            System.out.println(row);
                            csv_data.addRow(row);
                        }
                    } catch (Exception ex) {
                    	System.out.println(ex.getMessage());
                        System.out.println("Error in Parsing CSV File");
                    }
                    System.out.println(csv_data);
                    table.setModel(csv_data);
                    
                }
            }
        });
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}





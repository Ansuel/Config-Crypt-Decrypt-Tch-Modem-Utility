package decrypt_config;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.io.File;

import javax.swing.*;

public class gui_construct extends JFrame {

	private static final String titolo = "Crypt / Decrypt config utiliy";
	private File filename;
	private key_pair key;
	
	private static final JLabel head = new JLabel("Utility to decrypt and encrypt config file from modem");
	private static final JPanel headPanel = new JPanel();
	
	button_listners listner = new button_listners(this);
	
	JPanel inputPanel = new JPanel(new GridLayout(3, 1));
	JPanel fileSubPanel = new JPanel(); 
	JPanel keyInputPanel = new JPanel(new GridLayout(2, 1));
	//JPanel progressbarPanel = new JPanel();
	JPanel buttonPanel = new JPanel();
	JLabel keyinfotext = new JLabel("Insert the 128bit hex key extracted from the modem");
	JTextField inputkeyField = new JTextField(64);
	JTextField inputtextField = new JTextField(50);
	//JProgressBar progressbar = new JProgressBar();
	JButton analyze = new JButton("Analyze");
	JButton decryptFile = new JButton("Decrypt");
	JButton encryptFile = new JButton("Encrypt");

    JButton input = new JButton("Select File");

    JFileChooser file_choser = new JFileChooser();
	
	public gui_construct() {
		super(titolo);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		headPanel.add(head);
		
		inputtextField.setEditable(false);
		fileSubPanel.add(inputtextField);
		fileSubPanel.add(input);
		keyInputPanel.add(keyinfotext);
		keyInputPanel.add(inputkeyField);
		
		//progressbarPanel.add(progressbar);
		inputPanel.add(fileSubPanel);
		inputPanel.add(keyInputPanel);
		//inputPanel.add(progressbarPanel);
		buttonPanel.add(analyze);
		
		input.addActionListener(new button_listners(this));
		analyze.addActionListener(new button_listners(this));
		decryptFile.addActionListener(new button_listners(this));
		encryptFile.addActionListener(new button_listners(this));
		
		inputPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		
		Container frmContentPane = this.getContentPane();
		frmContentPane.add(headPanel, BorderLayout.NORTH);
		frmContentPane.add(inputPanel, BorderLayout.CENTER);
		frmContentPane.add(buttonPanel, BorderLayout.SOUTH);
		
		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
	
	public void setFilename(File filename) {
		this.filename = filename;
	}
	
	public void setKeyPair(String key) {
		key_pair inizialized_key = new key_pair(key);
		this.key = inizialized_key;
	}
	
	public File getFilename() {
		return filename;
	}
	
	public key_pair getKeyPair() {
		return key;
	}
}

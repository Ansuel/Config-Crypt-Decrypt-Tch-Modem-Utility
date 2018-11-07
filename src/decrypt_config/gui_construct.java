package decrypt_config;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.io.File;
import javax.swing.*;
import javax.swing.border.TitledBorder;

public class gui_construct extends JFrame {

	private static final long serialVersionUID = 1L;
	private static final String titolo = "Technicolor modem Crypt / Decrypt config utiliy";
	private static final Exception Exception = null;
	private File filename;
	private key_pair key;
	
	JPanel buttonPanel;
	JButton decryptFile;
	JButton encryptFile;
	JButton analyzeFile;
	
	JButton input;
	JTextField inputtextField;
	JTextField inputkeyField;
	JTextArea log;
	JTextArea headerFile;
	
	JLabel BoardType;
	JLabel ProductName;
	JLabel MAC;
	JLabel SerialNumber;
	JLabel BuildVersion;
	JLabel Signed;
	JLabel Crypted;
	JLabel IV;

    JFileChooser file_choser = new JFileChooser();
	
	public gui_construct() throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
		super(titolo);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		Container frmContentPane = getContentPane();
		frmContentPane.add(Header(), BorderLayout.NORTH);
		frmContentPane.add(ContentPanel(), BorderLayout.CENTER);
		frmContentPane.add(ButtonPanel(), BorderLayout.SOUTH);
		
		pack();
		setLocationRelativeTo(null);
		
		setVisible(true);
	}
	
	private JPanel Header() {
		
		final JPanel headPanel = new JPanel();
		
		final JLabel head = new JLabel("Utility to decrypt and encrypt config file from modem");
		headPanel.add(head);
		
		return headPanel;
	}
	
	private JPanel ContentPanel() {
		JPanel contentPanel = new JPanel(new BorderLayout());
		
	    contentPanel.add(InputFilePanel(), BorderLayout.NORTH);
	    contentPanel.add(InputKeyPairPanel(), BorderLayout.CENTER);
		contentPanel.add(InfoLogPanel(), BorderLayout.PAGE_END);
		contentPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		
		return contentPanel;
	}
	
	private JPanel InputFilePanel() {
		
		JPanel fileSubPanel = new JPanel(); 
		fileSubPanel.setBorder(new TitledBorder("Config File"));
		inputtextField = new JTextField(50);
		inputtextField.setEditable(false);
		input = new JButton("Select File");
		
		fileSubPanel.add(inputtextField);
		fileSubPanel.add(input);
		
		input.addActionListener(new button_listners(this));
		
		return fileSubPanel;
	}
	
	private JPanel InputKeyPairPanel() {
		
		JPanel keyInputPanel = new JPanel(new BorderLayout());
		keyInputPanel.setBorder(new TitledBorder("Key Settings"));
		JLabel keyinfotext = new JLabel("Insert the 128bit hex key extracted from the modem");
		inputkeyField = new JTextField(64);
		
		keyInputPanel.add(keyinfotext,BorderLayout.NORTH);
		keyInputPanel.add(inputkeyField,BorderLayout.SOUTH);
		
		return keyInputPanel;
	}
	
	private JPanel PopulateHeaderValue(){
		JPanel headerValuePanel = new JPanel(new GridLayout(8, 1));
		
		BoardType = new JLabel();
		ProductName = new JLabel();
		MAC = new JLabel();
		SerialNumber = new JLabel();
		BuildVersion = new JLabel();
		Signed = new JLabel();
		Crypted = new JLabel();
		IV = new JLabel();
		
		headerValuePanel.add(BoardType);
		headerValuePanel.add(ProductName);
		headerValuePanel.add(MAC);
		headerValuePanel.add(SerialNumber);
		headerValuePanel.add(BuildVersion);
		headerValuePanel.add(Signed);
		headerValuePanel.add(Crypted);
		headerValuePanel.add(IV);
		
		return headerValuePanel;
	}
	
	private JPanel InfoLogPanel() {
		JPanel infoPanelP = new JPanel(new BorderLayout());
		infoPanelP.setBorder(new TitledBorder("Log Info"));
		JPanel infoPanel = new JPanel(new GridLayout(1, 2));
		JPanel headerPanel = new JPanel(new GridLayout(1, 2));
		JPanel headerLegend = new JPanel(new GridLayout(8, 1));
		
		headerPanel.setBorder(new TitledBorder("Config Info"));

		JLabel BoardType = new JLabel("BoardType: ");
		JLabel ProductName = new JLabel("ProductName: ");
		JLabel MAC = new JLabel("MAC: ");
		JLabel SerialNumber = new JLabel("SerialNumber: ");
		JLabel BuildVersion = new JLabel("BuildVersion: ");
		JLabel Signed = new JLabel("Must be signed: ");
		JLabel Crypted = new JLabel("Must be crypted: ");
		JLabel IV = new JLabel("IV: ");
		
		headerLegend.add(BoardType);
		headerLegend.add(ProductName);
		headerLegend.add(MAC);
		headerLegend.add(SerialNumber);
		headerLegend.add(BuildVersion);
		headerLegend.add(Signed);
		headerLegend.add(Crypted);
		headerLegend.add(IV);
		
		headerPanel.add(headerLegend);
		headerPanel.add(PopulateHeaderValue());
		
		log = new JTextArea(5,25);
		headerFile = new JTextArea(20,40);
		JScrollPane logPanel = new JScrollPane(log);
		
		log.setEditable(false);
		headerFile.setEditable(false);
		
		infoPanel.add(logPanel);
		infoPanel.add(headerPanel);
		
		infoPanelP.add(infoPanel,BorderLayout.CENTER);
		
		return infoPanelP;
	}
	
	private JPanel ButtonPanel() {
		buttonPanel = new JPanel();
		
		analyzeFile = new JButton("Analyze");
		decryptFile = new JButton("Decrypt");
		encryptFile = new JButton("Encrypt");
		
		analyzeFile.addActionListener(new button_listners(this));
		decryptFile.addActionListener(new button_listners(this));
		encryptFile.addActionListener(new button_listners(this));
		
		buttonPanel.add(analyzeFile);
		
		return buttonPanel;
	}
	
	public void setFilename(File filename) {
		this.filename = filename;
	}
	
	public void setKeyPair(String key) throws Exception {
		if ( key.length() != 128 ) {
			throw Exception;
		}
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

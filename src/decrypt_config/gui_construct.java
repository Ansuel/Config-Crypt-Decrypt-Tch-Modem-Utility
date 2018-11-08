package decrypt_config;
import java.io.File;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class gui_construct {

	private Scene scene;
	
	private static final Exception Exception = null;
	
	private File filename;
	private key_pair key;
	
	TextField FileInputText;
	TextField KeyInputField;
	TextArea log;

	StackPane buttonPanel;
	Button decryptFile;
	Button encryptFile;
	Button analyzeFile;

	Label BoardType;
	Label ProductName;
	Label MAC;
	Label SerialNumber;
	Label BuildVersion;
	Label Signed;
	Label Crypted;
	Label IV;

	 
	public gui_construct() {
		VBox root = new VBox();
		root.setPadding(new Insets(20, 10, 10, 10));
		
        this.scene = new Scene(root);
        
        root.getChildren().add(Header());
        root.getChildren().add(InputFilePanel());
        root.getChildren().add(InputKeyPairPanel());
        root.getChildren().add(InfoLogPanel());
        root.getChildren().add(ButtonPanel());
	}
    
	button_listners Listners = new button_listners(this);
    
    private Node Header() {
		
		final StackPane headPanel = new StackPane();
		headPanel.setPadding(new Insets(0 , 0 , 20 , 0 ));
		
		final Label head = new Label("Utility to decrypt and encrypt config file from modem");
		head.setFont(Font.font("Arial", FontWeight.NORMAL, 20));
		headPanel.getChildren().add(head);
		
		return headPanel;
	}
    
    private Node InputFilePanel() {
		
		final HBox fileSubPanel = new HBox();
		
		FileInputText = new TextField();
		FileInputText.setEditable(false);
		Button input = new Button("Select File");
		input.setOnAction(Listners.getInputFileEventHandler());

		fileSubPanel.getChildren().add(FileInputText);
		fileSubPanel.getChildren().add(input);
		
		HBox.setHgrow(FileInputText, Priority.ALWAYS);
		
		TitledPane filePanel = new TitledPane("Config File", fileSubPanel); 
		filePanel.setCollapsible(false);
		filePanel.setPadding(new Insets(0,0,10,0));
		
		return filePanel;
	}
    
    
    private Node InputKeyPairPanel() {
		
		VBox keyInputSubPanel = new VBox();
		
		Label keyinfotext = new Label("Insert the 128bit hex key extracted from the modem");
		KeyInputField = new TextField();
		
		keyInputSubPanel.getChildren().add(keyinfotext);
		keyInputSubPanel.getChildren().add(KeyInputField);
		
		TitledPane keyInputPanel = new TitledPane("Key Settings", keyInputSubPanel); 
		keyInputPanel.setCollapsible(false);
		keyInputPanel.setPadding(new Insets(0,0,10,0));
		
		return keyInputPanel;
	}
    
    private VBox PopulateHeaderValue(){
		VBox headerValuePanel = new VBox();
		
		BoardType = new Label();
		ProductName = new Label();
		MAC = new Label();
		SerialNumber = new Label();
		BuildVersion = new Label();
		Signed = new Label();
		Crypted = new Label();
		IV = new Label();
		
		headerValuePanel.getChildren().add(BoardType);
		headerValuePanel.getChildren().add(ProductName);
		headerValuePanel.getChildren().add(MAC);
		headerValuePanel.getChildren().add(SerialNumber);
		headerValuePanel.getChildren().add(BuildVersion);
		headerValuePanel.getChildren().add(Signed);
		headerValuePanel.getChildren().add(Crypted);
		headerValuePanel.getChildren().add(IV);
		
		return headerValuePanel;
	}
    
    private Node InfoLogPanel() {
		//HBox headerPanel = new HBox();
    	VBox headerLegend = new VBox();
		
		Label BoardType = new Label("BoardType: ");
		Label ProductName = new Label("ProductName: ");
		Label MAC = new Label("MAC: ");
		Label SerialNumber = new Label("SerialNumber: ");
		Label BuildVersion = new Label("BuildVersion: ");
		Label Signed = new Label("Must be signed: ");
		Label Crypted = new Label("Must be crypted: ");
		Label IV = new Label("IV: ");
		
		headerLegend.getChildren().add(BoardType);
		headerLegend.getChildren().add(ProductName);
		headerLegend.getChildren().add(MAC);
		headerLegend.getChildren().add(SerialNumber);
		headerLegend.getChildren().add(BuildVersion);
		headerLegend.getChildren().add(Signed);
		headerLegend.getChildren().add(Crypted);
		headerLegend.getChildren().add(IV);
		
		HBox HeaderSubPanel = new HBox();
		HeaderSubPanel.getChildren().add(headerLegend);
		HeaderSubPanel.getChildren().add(PopulateHeaderValue());
		
		log = new TextArea();
		log.setEditable(false);
		
		TitledPane logPanel = new TitledPane("Log", new ScrollPane(log)); 
		logPanel.setCollapsible(false);
		logPanel.setPadding(new Insets(0,10,10,10));
		
		TitledPane HeaderPanel = new TitledPane("Config Info", HeaderSubPanel); 
		HeaderPanel.setCollapsible(false);
		HeaderPanel.setPadding(new Insets(0,10,10,10));
		
		HBox infoPanel = new HBox();
		
		infoPanel.getChildren().add(logPanel);
		infoPanel.getChildren().add(HeaderPanel);
		
		
		return infoPanel;
	}
    
    private Node ButtonPanel() {
		buttonPanel = new StackPane();
		
		analyzeFile = new Button("Analyze");
		decryptFile = new Button("Decrypt");
		encryptFile = new Button("Encrypt");
		
		analyzeFile.setOnAction(Listners.getAnalyzeFileEventHandler());
		decryptFile.setOnAction(Listners.getDecryptFileEventHandler());
		encryptFile.setOnAction(Listners.getEncryptFileEventHandler());

		buttonPanel.getChildren().add(analyzeFile);
		
		return buttonPanel;
	}

	public void setFilename(File filepath) {
		filename = filepath;
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
	
	public Scene getScene() {
		return scene;
	}

}

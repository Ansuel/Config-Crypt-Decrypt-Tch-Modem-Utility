package decrypt_config;

import java.io.File;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;

public class button_listners {
	
	private gui_construct Scene;
	private EventHandler<ActionEvent> inputFile;
	private EventHandler<ActionEvent> analyzeFile;
	private EventHandler<ActionEvent> decryptFile;
	private EventHandler<ActionEvent> encryptFile;
	
	button_listners(gui_construct scene) {
		this.Scene = scene;
		this.inputFile = new EventHandler<ActionEvent>() {
			@Override
	    	public void handle(ActionEvent event) {
	    		FileChooser File_Choser = new FileChooser();
				File file = File_Choser.showOpenDialog(gui_inizializer.stage);
				if (file != null)
					Scene.FileInputText.setText(file.toString());
				Scene.setFilename(file);
				ResetButton(Scene);
				event.consume();
	    	}
		};
		
		this.decryptFile = new EventHandler<ActionEvent>() {
			@Override
	    	public void handle(ActionEvent event) {
				Scene.log.appendText("Starting decryption...\n");
				try {
					Scene.setKeyPair(Scene.KeyInputField.getText());
				} catch (Exception e1) {
					KeyException(Scene);
				}
				File file = Scene.getFilename();
				byte[] chiper = Scene.getKeyPair().getChiper();
				try {
					StringBuilder content = aes_utility.decrypt(file, chiper);
					
					Scene.log.appendText("Decryption done.\n");
					
					File outputfile = OutputFileDialog();
					file_util.write_file(outputfile.toString(), content);
					
					Scene.log.appendText("You can find the decrypted file here: "+outputfile.toString()+"\n");
				} catch (Exception e1) {
					Scene.log.appendText("Decryption error.Wrong key provided?\n");
					e1.printStackTrace();
				}
				event.consume();
	    	}
			
		};
		
		this.encryptFile = new EventHandler<ActionEvent>() {
			@Override
	    	public void handle(ActionEvent event) {
				Scene.log.appendText("Starting encryption...\n");
				try {
					Scene.setKeyPair(Scene.KeyInputField.getText());
				} catch (Exception e1) {
					KeyException(Scene);
				}
				File file = Scene.getFilename();
				byte[] chiper = Scene.getKeyPair().getChiper();
				byte[] signature = Scene.getKeyPair().getSignature();
				try {
					byte[] content = aes_utility.encrypt(file, chiper);
					Scene.log.appendText("Encryption done.\n");
					
					File outputfile = OutputFileDialog();
					file_util.write_file_raw_signed(outputfile.toString(), content,signature);
					
					Scene.log.appendText("You can find the ecrypted file here: "+outputfile.toString()+"\n");
				} catch (Exception e1) {
					Scene.log.appendText("Encryption error.Wrong key provided?\n");
					e1.printStackTrace();
				}
				event.consume();
	    	}
			
		};
		
		this.analyzeFile = new EventHandler<ActionEvent>() {
			@Override
	    	public void handle(ActionEvent event) {
				File file = Scene.getFilename();
				InizializeInfoFile(file);
				event.consume();
	    	}
			
		};
	}
	
	private void ResetButton(gui_construct Scene) {
		Scene.buttonPanel.getChildren().clear();
		Scene.buttonPanel.getChildren().add(Scene.analyzeFile);
		//Scene.buttonPanel.revalidate();
		//Scene.buttonPanel.repaint();
	}
	
	private void KeyException(gui_construct frame) {
		Scene.KeyInputField.setText("");
		ResetButton(Scene);
		Scene.log.appendText("Key not valid!\n");
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Error");
		alert.setContentText("The key is too short or too long!");
		alert.showAndWait();
	}
	
	private void InizializeInfoFile(File file) {
		try {
			Scene.buttonPanel.getChildren().clear();
			
			read_header file_header = new read_header(file);
			file_analyze file_scanned = new file_analyze(file);
			
			String file_type = file_scanned.getFileType();
			String file_iv = file_scanned.getIv();

			if (file_type.equals("encrypted"))
				Scene.buttonPanel.getChildren().add(Scene.decryptFile);
			else
				Scene.buttonPanel.getChildren().add(Scene.encryptFile);
			
			Scene.BoardType.setText(file_header.getBoardType());
			Scene.ProductName.setText(file_header.getProductName());
			Scene.MAC.setText(file_header.getMAC());
			Scene.SerialNumber.setText(file_header.getSerialNumber());
			Scene.BuildVersion.setText(file_header.getBuildVersion());
			if ( file_header.getSigned() ) { 
				Scene.Signed.setText("True");
				Scene.Signed.setTextFill(Color.RED);
			} else {
				Scene.Signed.setText("False");
				Scene.Signed.setTextFill(Color.GREEN);
			}
			if ( file_header.getCrypted() ) { 
				Scene.Crypted.setText("True");
				Scene.Crypted.setTextFill(Color.RED);
			} else {
				Scene.Crypted.setText("False");
				Scene.Crypted.setTextFill(Color.GREEN);
			}
			if ( file_iv != null ) { 
				Scene.IV.setText("Detected");
				Scene.IV.setTextFill(Color.GREEN);
			} else {
				Scene.IV.setText("Not Present. Decrypted file!");
				Scene.IV.setTextFill(Color.GRAY);
			}
			
			Scene.log.appendText("Detected a valid config file.\n");
			Scene.log.appendText("The file scanned is "+file_type+".\n");

			//Scene.log.setCaretPosition(Scene.log.getDocument().getLength());
		} catch (Exception e1) {
			e1.printStackTrace();
			ResetButton(Scene);
			if ( Scene.FileInputText.getText().length() == 0 ) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error");
				alert.setContentText("Provide a config file to check!");
				alert.showAndWait();
			} else {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error");
				alert.setContentText("You load an invalid config!");
				alert.showAndWait();
			}
		}
	}
	
	private File OutputFileDialog() {
		FileChooser File_Choser = new FileChooser();
		File file = File_Choser.showSaveDialog(gui_inizializer.stage);
		
		return file;
	}

	
	public EventHandler<ActionEvent> getInputFileEventHandler() {
		return inputFile;
	}
	
	public EventHandler<ActionEvent> getAnalyzeFileEventHandler() {
		return analyzeFile;
	}
	
	public EventHandler<ActionEvent> getDecryptFileEventHandler() {
		return decryptFile;
	}
	
	public EventHandler<ActionEvent> getEncryptFileEventHandler() {
		return encryptFile;
	}
}

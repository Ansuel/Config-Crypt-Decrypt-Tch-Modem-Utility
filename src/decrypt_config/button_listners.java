package decrypt_config;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JOptionPane;

public class button_listners implements ActionListener {

		private gui_construct frame;
		
		button_listners(gui_construct frame) {
			this.frame = frame;
		}
		
		private void ResetButton(gui_construct frame) {
			frame.buttonPanel.removeAll();
			frame.buttonPanel.add(frame.analyzeFile);
			frame.buttonPanel.revalidate();
			frame.buttonPanel.repaint();
		}
		
		private void InizializeInfoFile(File file) {
			try {
				frame.buttonPanel.removeAll();
				
				read_header file_header = new read_header(file);
				file_analyze file_scanned = new file_analyze(file);
				
				String file_type = file_scanned.getFileType();
				String file_iv = file_scanned.getIv();

				if (file_type.equals("encrypted"))
					frame.buttonPanel.add(frame.decryptFile);
				else
					frame.buttonPanel.add(frame.encryptFile);
				
				frame.BoardType.setText(file_header.getBoardType());
				frame.ProductName.setText(file_header.getProductName());
				frame.MAC.setText(file_header.getMAC());
				frame.SerialNumber.setText(file_header.getSerialNumber());
				frame.BuildVersion.setText(file_header.getBuildVersion());
				if ( file_header.getSigned() ) { 
					frame.Signed.setText("True");
					frame.Signed.setForeground(Color.red);
				} else {
					frame.Signed.setText("False");
					frame.Signed.setForeground(Color.green);
				}
				if ( file_header.getCrypted() ) { 
					frame.Crypted.setText("True");
					frame.Crypted.setForeground(Color.red);
				} else {
					frame.Crypted.setText("False");
					frame.Crypted.setForeground(Color.green);
				}
				if ( file_iv != null ) { 
					frame.IV.setText("Detected");
					frame.IV.setForeground(Color.green);
				} else {
					frame.IV.setText("Not Present. Decrypted file!");
					frame.IV.setForeground(Color.gray);
				}
				
				frame.log.append("Deected a valid config file.\n");
				frame.log.append("The file scanned is "+file_type+".\n");

				frame.log.setCaretPosition(frame.log.getDocument().getLength());
				frame.buttonPanel.revalidate();
				frame.buttonPanel.repaint();
			} catch (Exception e1) {
				e1.printStackTrace();
				ResetButton(frame);
				if ( frame.inputtextField.getText().length() == 0 )
					JOptionPane.showMessageDialog(frame, "Provide a config file to check!", "Error", 0);
				else
					JOptionPane.showMessageDialog(frame, "You load an invalid config!", "Error", 0);
			}
		}
		
		private void KeyException(gui_construct frame) {
			frame.inputkeyField.setText("");
			ResetButton(frame);
			frame.log.append("Key not valid!\n");
			JOptionPane.showMessageDialog(frame, "The key is too short or too long!", "Error", 0);
		}

		public void actionPerformed(ActionEvent e){
			if (e.getSource() == frame.input) {
				frame.file_choser.showOpenDialog(frame);
				File file = frame.file_choser.getSelectedFile();
				if (file != null)
					frame.inputtextField.setText(file.toString());
				frame.setFilename(file);
				ResetButton(frame);
			} else if (e.getSource() == frame.decryptFile) {
				frame.log.append("Starting decryption...\n");
				try {
					frame.setKeyPair(frame.inputkeyField.getText());
				} catch (Exception e1) {
					KeyException(frame);
				}
				File file = frame.getFilename();
				byte[] chiper = frame.getKeyPair().getChiper();
				try {
					StringBuilder content = aes_utility.decrypt(file, chiper);
					file_util.write_file(file.toString()+".dec", content);
					frame.log.append("Decryption done.\n");
					frame.log.append("You can find the decrypted file here: "+file.toString()+".dec\n");
				} catch (Exception e1) {
					frame.log.append("Decryption error.Wrong key provided?\n");
					e1.printStackTrace();
				}
			} else if (e.getSource() == frame.encryptFile) {
				frame.log.append("Starting encryption...\n");
				try {
					frame.setKeyPair(frame.inputkeyField.getText());
				} catch (Exception e1) {
					KeyException(frame);
				}
				File file = frame.getFilename();
				byte[] chiper = frame.getKeyPair().getChiper();
				byte[] signature = frame.getKeyPair().getSignature();
				try {
					byte[] content = aes_utility.encrypt(file, chiper);
					file_util.write_file_raw_signed(file.toString()+".enc", content,signature);
					frame.log.append("Encryption done.\n");
					frame.log.append("You can find the ecrypted file here: "+file.toString()+".enc\n");
				} catch (Exception e1) {
					frame.log.append("Encryption error.Wrong key provided?\n");
					e1.printStackTrace();
				}
			} else if (e.getSource() == frame.analyzeFile) {
				File file = frame.getFilename();
				InizializeInfoFile(file);
			}

		}
}

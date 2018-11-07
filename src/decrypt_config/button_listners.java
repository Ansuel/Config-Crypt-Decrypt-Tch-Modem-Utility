package decrypt_config;

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
			frame.log.setText("");
			frame.buttonPanel.removeAll();
			frame.buttonPanel.add(frame.analyzeFile);
			frame.buttonPanel.validate();
		}
		
		private void InizializeInfoFile(File file) {
			frame.buttonPanel.removeAll();
			try {
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
				frame.Signed.setText(file_header.getSigned() ? "True" : "False");
				frame.Crypted.setText(file_header.getCrypted() ? "True" : "False");
				frame.IV.setText(file_iv != null ? string_util.toHex(file_iv) : "Not Present");
				
				frame.log.append("The file scanned is "+file_type+"\n");

				frame.log.setCaretPosition(frame.log.getDocument().getLength());
				frame.buttonPanel.validate();
			} catch (Exception e1) {
				e1.printStackTrace();
				ResetButton(frame);
				JOptionPane.showMessageDialog(frame, "You load an invalid config!", "Error", 0);
			}
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
				
				frame.setKeyPair(frame.inputkeyField.getText());
				File file = frame.getFilename();
				byte[] chiper = frame.getKeyPair().getChiper();
				try {
					StringBuilder content = aes_utility.decrypt(file, chiper);
					file_util.write_file(file.toString()+".dec", content);
					System.out.println("Done");
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} else if (e.getSource() == frame.encryptFile) {

				frame.setKeyPair(frame.inputkeyField.getText());
				File file = frame.getFilename();
				byte[] chiper = frame.getKeyPair().getChiper();
				byte[] signature = frame.getKeyPair().getSignature();
				try {
					byte[] content = aes_utility.encrypt(file, chiper);
					file_util.write_file_raw_signed(file.toString()+".enc", content,signature);
					System.out.println("Done");
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} else if (e.getSource() == frame.analyzeFile) {
				File file = frame.getFilename();
				InizializeInfoFile(file);
			}

		}
}

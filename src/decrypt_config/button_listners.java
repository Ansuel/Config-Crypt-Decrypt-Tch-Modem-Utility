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
		
		private void InizializeInfoFile(File file) {
			try {
				String file_type = new file_analyze(file).getFileType();
				if (file_type.equals("enc"))
					frame.buttonPanel.add(frame.decryptFile);
				else
					frame.buttonPanel.add(frame.encryptFile);
				frame.buttonPanel.validate();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
		public void actionPerformed(ActionEvent e){
			if (e.getSource() == frame.input) {
				frame.file_choser.showOpenDialog(frame);
				File file = frame.file_choser.getSelectedFile();
				frame.inputtextField.setText(file.toString());
				frame.setFilename(file);
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
			} else if (e.getSource() == frame.analyze) {
				File file = frame.getFilename();
				InizializeInfoFile(file);
			}

		}
}

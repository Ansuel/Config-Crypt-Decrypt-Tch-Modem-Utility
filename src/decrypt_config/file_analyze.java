package decrypt_config;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class file_analyze {
	
	private String file_type;
	private String iv;
	//private String signature;
	
	file_analyze(File config_file_path) throws IOException {

		FileInputStream file_stream = new FileInputStream(config_file_path);
		
		read_header header = new read_header(config_file_path);
		file_stream.skip(header.getByte_read());
		
		StringBuilder tmpstring = new StringBuilder();

		int count = 0;
		while(count < 3) {
			tmpstring.append((char) file_stream.read());
			count++;
    	}
		file_stream.close();
		
		if ( tmpstring.toString().equals("[*]") ) {
			this.file_type = "decrypted";
		} else {
			this.iv = new String(file_util.read_enc_iv(config_file_path));
			this.file_type = "encrypted";
		}
	}
	
	public String getFileType() {
		return file_type;
	}
	
	public String getIv() {
		return iv;
	}

}

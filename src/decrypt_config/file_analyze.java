package decrypt_config;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class file_analyze {
	
	private String file_type;
	private String iv;
	private String signature;
	
	file_analyze(File config_file_path) throws IOException {

		FileInputStream file_stream = new FileInputStream(config_file_path);
		
		read_header header = new read_header(config_file_path);
		file_stream.skip(header.getByte_read());
		
		StringBuilder tmpstring = new StringBuilder();
		//ByteArrayOutputStream file_byte = new ByteArrayOutputStream();
		int count = 0;
		while(count < 3) {
			tmpstring.append((char) file_stream.read());
			count++;
    	}
		file_stream.close();
		System.out.println(tmpstring);
		if ( tmpstring.toString().equals("[*]") )
			this.file_type = "dec";
		else
			this.file_type = "enc";
	}
	
	public String getFileType() {
		return file_type;
	}

}

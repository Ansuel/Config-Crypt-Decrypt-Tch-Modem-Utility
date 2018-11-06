package decrypt_config;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class read_header {
	private byte[] file_byte;
	private int byte_read;
	
	read_header(File config_file_path) throws IOException {
	
	int byte_read = 0;
	FileInputStream file_stream = new FileInputStream(config_file_path);
	StringBuilder tmpstring = new StringBuilder();
	ByteArrayOutputStream file_byte = new ByteArrayOutputStream(); 
	while(file_stream.available() > 0) { //remove header, create a function to read this?
		byte_read++;
    	char car = (char) file_stream.read();
    	file_byte.write(car);
    	if ( car == '\n' ) {
    		if (tmpstring.toString().isEmpty()) {
    		break;
    		}
    		tmpstring.setLength(0);
    	} else {
    		tmpstring.append(car);
    	}
    }
	file_stream.close();
	this.file_byte = file_byte.toByteArray();
	this.byte_read = byte_read;
	}
	
	public int getByte_read() {
		return byte_read;
	}
	
	public byte[] getFile_byte() {
		return file_byte;
	}
}

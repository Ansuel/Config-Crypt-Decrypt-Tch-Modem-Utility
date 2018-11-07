package decrypt_config;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class read_header {
	private byte[] file_byte;
	private int byte_read;
	
	private String BoardType;
	private String ProductName;
	private String SerialNumber;
	private String MAC;
	private String BuildVersion;
	private Boolean Signed = false;
	private Boolean Crypted = false;
	
	read_header(File config_file_path) throws IOException {
		
		Boolean FileChecked = false;
		int byte_read = 0;
		FileInputStream file_stream = new FileInputStream(config_file_path);
		StringBuilder tmpstring = new StringBuilder();
		ByteArrayOutputStream file_byte = new ByteArrayOutputStream(); 
		while(file_stream.available() > 0) { //consume header, and apply sensible info to variable
			byte_read++;
	    	char car = (char) file_stream.read();
	    	file_byte.write(car);
	    	if ( car == '\n' ) {
	    		
	    		if (!tmpstring.toString().contains("PREAMBLE") && !FileChecked) {//This check if the first line is valid and set boolean
	    			file_stream.close();
	    			throw new IOException(); // to skip on next line
	    		} else
	    			FileChecked = true;
	    		if (tmpstring.toString().contains("BOARDMNEMONIC"))
	    			this.BoardType = tmpstring.toString().replaceAll(".*=", "");
	    		if (tmpstring.toString().contains("SERIALNUMBER"))
	    			this.SerialNumber = tmpstring.toString().replaceAll(".*=", "");
	    		if (tmpstring.toString().contains("PRODUCTNAME"))
	    			this.ProductName = tmpstring.toString().replaceAll(".*=", "");
	    		if (tmpstring.toString().contains("MAC"))
	    			this.MAC = tmpstring.toString().replaceAll(".*=", "");
	    		if (tmpstring.toString().contains("BUILDVERSION"))
	    			this.BuildVersion = tmpstring.toString().replaceAll(".*=", "");
	    		if (tmpstring.toString().contains("CIPHERKEY"))
	    			this.Signed = true;
	    		if (tmpstring.toString().contains("SIGNATUREKEY"))
	    			this.Crypted = true;
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
	
	public String getBoardType() {
		return BoardType;
	}
	
	public String getBuildVersion() {
		return BuildVersion;
	}
	
	public String getSerialNumber() {
		return SerialNumber;
	}
	public String getMAC() {
		return MAC;
	}
	public String getProductName() {
		return ProductName;
	}
	public Boolean getSigned() {
		return Signed;
	}
	public Boolean getCrypted() {
		return Crypted;
	}
	
}

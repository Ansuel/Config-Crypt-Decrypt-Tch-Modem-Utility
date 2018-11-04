package decrypt_config;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;

public class file_util {
	
		public static byte[] read_key(String filepath,String type) throws IOException {
			
			int key_type_stop;
			if ( type == "signature") {
				key_type_stop = 1;
			} else {
				key_type_stop = 33;
			}
			FileInputStream file_stream = new FileInputStream(filepath);
			ByteArrayOutputStream file_byte = new ByteArrayOutputStream();
			while ( file_stream.available() > key_type_stop )
				file_byte.write(file_stream.read());
			file_stream.close();
			return file_byte.toByteArray();
		}
		
		public static byte[] read_enc_config_file(String filepath) throws IOException {
			
			return null;
			
		}
		
		public static byte[] read_enc_iv(String filepath) throws IOException {
			FileInputStream file_stream = new FileInputStream(filepath);
			ByteArrayOutputStream file_byte = new ByteArrayOutputStream();
			int n = 0;
			read_header header = new read_header(filepath);
			
			file_stream.skip(header.getByte_read());
			while ( n < 16 ) {
				n++;
				file_byte.write(file_stream.read());
				}
			file_stream.close();
			return file_byte.toByteArray();
		}
		
		public static void write_file(String dest_file, CharSequence content) throws IOException {
			BufferedWriter writer = new BufferedWriter(new FileWriter(dest_file));
			
			writer.append(content);
            
            writer.close();
			
		}
		
		public static void write_file_raw(String dest_file, byte[] content,byte[] sign_key) throws IOException, InvalidKeyException, SignatureException, NoSuchAlgorithmException {
			FileOutputStream writer = new FileOutputStream(dest_file);
			
			writer.write(content);
			writer.write(aes_utility.calculateRFC2104HMAC(content, sign_key));
            
            writer.close();
			
		}

	}

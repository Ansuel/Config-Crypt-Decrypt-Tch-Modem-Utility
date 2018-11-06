package decrypt_config;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class aes_utility {
    
	public static StringBuilder decrypt(File config_file_path,byte[] aes_key) throws Exception {

            final StringBuilder output = new StringBuilder();
            byte[] iv = file_util.read_enc_iv(config_file_path);
            final Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            IvParameterSpec ivParameterSpec = new IvParameterSpec(iv, 0, cipher.getBlockSize());
            SecretKeySpec secretSpec = new SecretKeySpec(aes_key, "AES");
            FileInputStream file_stream = new FileInputStream(config_file_path);
            read_header header = new read_header(config_file_path);
            output.append(new String(header.getFile_byte()));
            
            cipher.init(Cipher.DECRYPT_MODE, secretSpec, ivParameterSpec);
            file_stream.skip(header.getByte_read());
            file_stream.skip(16); //remove iv 
            
            //Create stream to remove signature
            ByteArrayOutputStream enc_array = new ByteArrayOutputStream(); 
            while(file_stream.available() > 20) { //remove signature was 20
            	enc_array.write(file_stream.read());
            }

            //Create input stream from the removed data
            InputStream enc_stream = new ByteArrayInputStream(enc_array.toByteArray()); 
            
            InputStream cipherInputStream = new CipherInputStream(enc_stream, cipher);

            final String charsetName = "UTF-8";

            final byte[] buffer = new byte[1024];
            int read = cipherInputStream.read(buffer);
            
            //int remaining_bit = enc_stream.available();
            
            while (read > -1) {
            	//int progressValue = (int) (((double) (remaining_bit-enc_stream.available())/remaining_bit)*100);
            	//System.out.println(progressValue);
            	//frame.progressbar.setValue(progressValue);
                output.append(new String(buffer, 0, read, charsetName));
                read = cipherInputStream.read(buffer);
            }
            
            cipherInputStream.close();
            file_stream.close();
            
            return output;

    }
	
	public static byte[] encrypt(File config_file_path,byte[] aes_key) throws Exception {

			ByteArrayOutputStream output = new ByteArrayOutputStream();
            final Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            byte[] iv = string_util.getRandomHexString(16).getBytes();
            SecretKeySpec secretSpec = new SecretKeySpec(aes_key, "AES");
            IvParameterSpec ivParameterSpec = new IvParameterSpec(iv, 0, cipher.getBlockSize());
            
            FileInputStream file_stream = new FileInputStream(config_file_path);
            read_header header = new read_header(config_file_path);
            
            output.write(header.getFile_byte());
            output.write(iv);

            file_stream.skip(header.getByte_read());
            
            cipher.init(Cipher.ENCRYPT_MODE, secretSpec, ivParameterSpec);

            InputStream cipherInputStream = new CipherInputStream(file_stream, cipher);

            final byte[] buffer = new byte[1024];
            int r;
            while ((r = cipherInputStream.read(buffer)) > 0) {
                output.write(buffer, 0, r);
            }

            file_stream.close();
            cipherInputStream.close();
            
            return output.toByteArray();

    }
	
	public static byte[] calculateRFC2104HMAC(byte[] data, byte[] key)
			throws SignatureException, NoSuchAlgorithmException, InvalidKeyException
		{
		final String HMAC_SHA1_ALGORITHM = "HmacSHA1";
		
			SecretKeySpec signingKey = new SecretKeySpec(key, HMAC_SHA1_ALGORITHM);
			Mac mac = Mac.getInstance(HMAC_SHA1_ALGORITHM);
			mac.init(signingKey);
			return mac.doFinal(data);
		}
}

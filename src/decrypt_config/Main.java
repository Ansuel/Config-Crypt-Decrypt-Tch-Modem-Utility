package decrypt_config;

public class Main {

	public static void main(String[] args) throws Exception {
		
		gui_construct gui = new gui_construct();
		
		//byte[] key_B_chip = file_util.read_key("D:\\\\test\\random_key","chiper");
		//byte[] key_B_sign = file_util.read_key("D:\\\\test\\random_key","signature");
		String config_file_path = "D:\\\\test\\config.bin";
		String dest_file_path = "D:\\\\test\\config.bin.plain";
		String test_file_path = "D:\\\\test\\config.bin.reenc";
		String test_dec_file_path = "D:\\\\test\\config.bin.reenc_dec";

		//key_pair key = new key_pair("2e2a6a7dda199bea58164fa146315f738abfef943e863e28b525a1583cf780775cfbd65f91f169f169ea26451dccc254ff0a106065a786c1cf7e6da53f9cc930");
		
		//JOptionPane.showInputDialog("Inserisci key");
		
		//System.out.println("Aes key: "+string_util.toHexString(key.getSignature()));
		//System.out.println("Sign key: "+string_util.toHexString(key.getChiper()));
		
		//StringBuilder content = aes_utility.decrypt(config_file_path, key_B_chip);
		
		//file_util.write_file(dest_file_path, content);
		
		//System.out.println(string_util.toHexString(key_B_sign));
		
		//byte[] content2 = aes_utility.encrypt(dest_file_path, key.getChiper());
		
		//file_util.write_file_raw(test_file_path, content2,key.getSignature());
		
		//StringBuilder content = aes_utility.decrypt(test_file_path, key.getChiper());
		
		//file_util.write_file(test_dec_file_path,content);
		
		//System.out.println("Done!");
		
	}

}

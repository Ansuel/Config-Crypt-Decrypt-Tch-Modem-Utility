package decrypt_config;

public class key_pair {
	private byte[] chiper_key;
	private byte[] signature_key;
	
	key_pair(String key){
		this.signature_key = string_util.toByteArray(key);
		this.chiper_key = string_util.toByteArray(key.substring(0, 64));
	}
	
	public byte[] getChiper() {
		return chiper_key;
	}
	
	public byte[] getSignature() {
		return signature_key;
	}
}

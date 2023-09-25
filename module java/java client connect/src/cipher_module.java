import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class cipher_module {
    private
    static SecretKey key = string_to_secretkey(System.getenv("symmetric_key"));
    public cipher_module(){
        key = string_to_secretkey(System.getenv("symmetric_key"));
    }




    public static String decrypt(String cipherText) throws NoSuchPaddingException, NoSuchAlgorithmException,
            InvalidAlgorithmParameterException, InvalidKeyException,
            BadPaddingException, IllegalBlockSizeException {

        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] plainText = cipher.doFinal(Base64.getDecoder()
                .decode(cipherText));
        return new String(plainText);
    }
    public static String encrypt(String plaintext) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE,key);
        byte[] ciphertext_bytes = cipher.doFinal(plaintext.getBytes());
        String ciphertext_String = Base64.getEncoder().encodeToString(ciphertext_bytes);
        return ciphertext_String;
    }

    private static SecretKey string_to_secretkey(String encoded_key){
        byte[] key_bytes = Base64.getDecoder().decode(encoded_key);
        return new SecretKeySpec(key_bytes,0,key_bytes.length,"AES");
    }

}

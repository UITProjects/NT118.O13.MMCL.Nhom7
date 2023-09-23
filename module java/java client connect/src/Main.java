import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException, InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        var client = new client_connect_module("localhost",2509);
        while(true) {
            Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine();
            Encryt_and_decrypt_module aes_module =new Encryt_and_decrypt_module();
           String encrypt_message_String = aes_module.encrypt(input);
           System.out.println(encrypt_message_String);
            client.send_message(encrypt_message_String);
        }

    }
}
import com.google.gson.Gson;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException, InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
            List<String> list = new ArrayList<>();
            Map<String,String> map_test = new HashMap<>();
            map_test.put("type","authentication");
            map_test.put("username_primary","test_username");
            map_test.put("password","123456789");
            System.out.println(map_test);
            String json = new Gson().toJson(map_test);
            System.out.println(json);
        var client = new client_connect_module("localhost",2509);
        while(true) {
          Encryt_and_decrypt_module aes_module =new Encryt_and_decrypt_module();
         String encrypt_message_String = aes_module.encrypt(json);
           System.out.println(encrypt_message_String);
            client.send_message(encrypt_message_String);
            client.listening_message();
            Scanner scanner = new Scanner(System.in);
           String input = scanner.nextLine();
        }

    }
}
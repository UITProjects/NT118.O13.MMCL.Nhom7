import com.google.gson.Gson;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
public class handle_request_types_module {
    static Map<String,String> request_message_Map;
    public static void authentication(String username_primary,String password) throws NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException, IOException {
        request_message_Map = new HashMap<>();
        request_message_Map.put("type","authentication");
        request_message_Map.put("username_primary",username_primary);
        request_message_Map.put("password",password);
        String request_message_json_format_String = new Gson().toJson(request_message_Map);
        String encrypt_request_message_String = cipher_module.encrypt(request_message_json_format_String);
        client_connection_module.send_message(encrypt_request_message_String);
        handle_thread thread_listening = new handle_thread("authentication");
        thread_listening.start();

    }


}

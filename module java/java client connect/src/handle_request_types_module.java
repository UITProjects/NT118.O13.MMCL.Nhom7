import com.google.gson.Gson;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class handle_request_types_module {
    static Map<String,String> request_message_Map;
    static void send_message_to_client(Map<String,String> request_message_Map) throws NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException, IOException {
        String request_message_json_format_String = new Gson().toJson(request_message_Map);
        String encrypt_request_message_String = cipher_module.encrypt(request_message_json_format_String);
        client_connection_module.send_message(encrypt_request_message_String);
    }
    public static void authentication(String username_primary,String password) throws NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException, IOException {
        request_message_Map = new HashMap<>();
        request_message_Map.put("type","authentication");
        request_message_Map.put("username_primary",username_primary);
        request_message_Map.put("password",password);
        send_message_to_client(request_message_Map);
        handle_thread thread_listening = new handle_thread("authentication");
        thread_listening.start();
    }
    public static void forgot_password(String username_primary,String email) throws NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, IOException, InvalidKeyException {
        request_message_Map = new HashMap<>();
        request_message_Map.put("type","forgot_password");
        request_message_Map.put("username_primary",username_primary);
        request_message_Map.put("email",email);
        request_message_Map.put("otp_valid","none");
        send_message_to_client(request_message_Map);
        Map response_from_server = client_connection_module.listening_message();
        System.out.println(response_from_server);
        Scanner otp_input = new Scanner(System.in);
        System.out.println(response_from_server.get("status"));
        if (response_from_server.get("status").equals("otp_sent")){
            System.out.println("Nhap OTP da gui ve tu email");
            request_message_Map.put("otp",otp_input.nextLine());
            send_message_to_client(request_message_Map);
            response_from_server = client_connection_module.listening_message();
            System.out.println(response_from_server);
            if (response_from_server.get("otp_valid").equals("valid")) {
                System.out.println("Nhap mat khau moi");
                request_message_Map.put("new_password", new Scanner(System.in).nextLine());
                send_message_to_client(request_message_Map);
                response_from_server = client_connection_module.listening_message();
                System.out.println(response_from_server);
                otp_input.nextLine();
            }
        }



    }

}

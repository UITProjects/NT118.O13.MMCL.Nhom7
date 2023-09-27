package me.ngodat0103.myapplication;

import com.google.gson.Gson;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class client_connection_module {
    static Socket client_socket =null;
    static OutputStream output = null;
    static InputStream input = null;
    public static void client_connection_module_init(String host, int port) throws IOException {
        client_socket = new Socket(host,port);
        output = client_socket.getOutputStream();
        input = client_socket.getInputStream();
    }
    public static void send(byte[] message_bytes) throws IOException {
        int message_byte_length_int = message_bytes.length;
        ByteBuffer byte_buffer_bytes = ByteBuffer.allocate(4);
        byte_buffer_bytes.putInt(message_byte_length_int);
        byte[] header_length_bytes = byte_buffer_bytes.array();
        output.write(header_length_bytes);
        output.write(message_bytes);
    }
    public static Map listen_response_from_server() throws IOException, InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
            byte[] buffer_header_length_bytes = new byte[4];
            buffer_header_length_bytes = input.readNBytes(4);
            int header_length_int = new BigInteger(buffer_header_length_bytes).intValue();
            byte[] buffer_server_response_encrypted_bytes = new byte[header_length_int];
             buffer_server_response_encrypted_bytes = input.readNBytes(header_length_int);
            String buffer_server_response_decrypted_String = cipher_module.decrypt(buffer_server_response_encrypted_bytes);
        return new Gson().fromJson(buffer_server_response_decrypted_String, Map.class);
    }
    static public Map<String, String> convertStringToMap(String data) {
        Map<String, String> map = new HashMap<>();
        StringTokenizer tokenizer = new StringTokenizer(data, " ");

        while (tokenizer.hasMoreTokens()) {
            String token = tokenizer.nextToken();
            String[] keyValue = token.split("=");
            map.put(keyValue[0], keyValue[1]);
        }
        return map;
    }
}

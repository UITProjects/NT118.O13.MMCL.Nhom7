import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
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
        System.out.println("Connected");
    }
    public static void send_message(String message_String) throws IOException {
        byte[] message_Byte = message_String.getBytes();
        int message_byte_length_int = message_Byte.length;
        ByteBuffer byte_buffer_bytes = ByteBuffer.allocate(4);
        byte_buffer_bytes.putInt(message_byte_length_int);
        byte[] header_length_bytes = byte_buffer_bytes.array();
        output.write(header_length_bytes);
        output.write(message_Byte);
    }
    public static Map listening_message() throws IOException {
            byte[] buffer_header_length_bytes = new byte[4];
            buffer_header_length_bytes = input.readNBytes(4);
            int header_length_int = new BigInteger(buffer_header_length_bytes).intValue();
            byte[] buffer_server_response_bytes = new byte[header_length_int];
            int bytes_read = 0;
             buffer_server_response_bytes = input.readNBytes(header_length_int);
             String server_response_String = new String(buffer_server_response_bytes, Charset.defaultCharset());
        return new Gson().fromJson(server_response_String, Map.class);
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

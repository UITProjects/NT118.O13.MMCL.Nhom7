import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.ByteBuffer;

public class client_connect_module {
    Socket client_socket =null;
    OutputStream output = null;
    public client_connect_module(String host, int port) throws IOException {
        client_socket = new Socket(host,port);
        output = client_socket.getOutputStream();
        System.out.println("Connected");
    }
    public void send_message(String message_String) throws IOException {
        byte[] message_Byte = message_String.getBytes();
        int message_byte_length_int = message_Byte.length;
        System.out.println(message_byte_length_int);
        ByteBuffer byte_buffer_bytes = ByteBuffer.allocate(4);
        byte_buffer_bytes.putInt(message_byte_length_int);
        byte[] header_length_bytes = byte_buffer_bytes.array();
        output.write(header_length_bytes);
        output.write(message_Byte);
    }
}

import java.net.Socket;
import java.net.UnknownHostException;
import java.net.*;
import java.io.*;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.SSLSocket;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Scanner;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) throws IOException {
       Socket client_socket = new Socket("localhost",2509);
       System.out.println("connected");
       OutputStream output = client_socket.getOutputStream();
       String message ="Hello, server";
       byte[] message_to_bytes = message.getBytes();
       output.write(message_to_bytes);
       output.flush();
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
    }
}
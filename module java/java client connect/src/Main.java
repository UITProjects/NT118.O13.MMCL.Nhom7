import java.net.Socket;
import java.net.UnknownHostException;
import java.net.*;
import java.io.*;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.SSLSocket;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;
// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) throws IOException, InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        SecretKey secretKey = Encryt_and_decrypt_module.string_to_secretkey("xKtGF158L74OcMMKEbUmiFTTACaf3KR4Iys2FRzFXZs=");
String message = Encryt_and_decrypt_module.decrypt("3WEEbbzgZsPH8Tik8POBLA==",secretKey);
System.out.println(message);

    }
}
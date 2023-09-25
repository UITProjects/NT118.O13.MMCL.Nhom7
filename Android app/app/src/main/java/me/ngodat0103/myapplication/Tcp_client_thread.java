package me.ngodat0103.myapplication;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class Tcp_client_thread extends Thread {
    private String server_name;
    client_connect_module client_socket = null;
    private int serverPort;
    public Tcp_client_thread(String server_name, int port){
        this.server_name=server_name;
        this.serverPort=port;
    }
    @Override
    public void run(){
        try {
            client_socket = new client_connect_module(server_name,serverPort);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {

            client_socket.send_message(cipher_module.encrypt("Hello"));
        } catch (IOException | NoSuchPaddingException | NoSuchAlgorithmException |
                 InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
            throw new RuntimeException(e);
        }


    }

}

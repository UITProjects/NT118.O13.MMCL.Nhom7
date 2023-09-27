package me.ngodat0103.myapplication;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class handle_thread extends Thread {
    String type;
    public handle_thread() {
    }
    public void run(){
        try {
            client_connection_module.client_connection_module_init("10.0.2.2",2509);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}

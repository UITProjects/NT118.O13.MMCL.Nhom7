import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class handle_thread extends Thread {
    String type;
    public handle_thread(String type) {
        this.type = type;
    }
    public void run(){
        if (type.equals("authentication")) {
            try {
                client_connection_module.listen_response_from_server();
            } catch (IOException | InvalidAlgorithmParameterException | NoSuchPaddingException |
                     IllegalBlockSizeException | NoSuchAlgorithmException | BadPaddingException | InvalidKeyException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

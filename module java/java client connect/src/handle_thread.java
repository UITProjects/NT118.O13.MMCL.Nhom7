import java.io.IOException;

public class handle_thread extends Thread {
    String type;
    public handle_thread(String type) {
        this.type = type;
    }
    public void run(){
        if (type.equals("authentication")) {
            try {
                client_connection_module.listening_message();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class Main {
    public static void main(String[] args) throws IOException, InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
            client_connection_module.client_connection_module_init("localhost",2509);
            //handle_request_types_module.authentication("java_test_create_accoun2t","123456789");
            handle_request_types_module.forgot_password("java_test_create_accoun2t","ngovuminhdat@gmail.com");
    }
}
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class Main {
    public static void main(String[] args) throws IOException, InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
           client_connection_module.client_connection_module_init("localhost",2509);
//            handle_request_types_module.authentication("test_account1","123456789");
//            handle_request_types_module.forgot_password("test_account1","phuckien289@gmail.com");
//            handle_request_types_module.upload_image_profile("test_account1");
//            handle_request_types_module.upload_image_profile("test_account2");
              handle_request_types_module.load_profile_image("test_account2");
              handle_request_types_module.load_profile_image("test_account1");
              handle_request_types_module.upload_image_profile("test_account3");
              handle_request_types_module.load_profile_image("test_account3");

    }
}
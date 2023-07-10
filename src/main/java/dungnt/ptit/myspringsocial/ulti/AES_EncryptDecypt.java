package dungnt.ptit.myspringsocial.ulti;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.*;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;

@Slf4j
@Component
public class AES_EncryptDecypt {
    @Value("${app.secret.user}")
    String SECRET_KEY_USER = "dungntptit150699";

    SecretKeySpec skeySpec = new SecretKeySpec(SECRET_KEY_USER.getBytes(), "AES");;
    Cipher cipher;

    {
        try {
            cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (NoSuchPaddingException e) {
            throw new RuntimeException(e);
        }
    }

    public String encryptUser(String plainText) {
        String encrypted = null;
        try {
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
            byte[] byteEncrypted = cipher.doFinal(plainText.getBytes());
            encrypted =  Base64.getEncoder().encodeToString(byteEncrypted);
        }catch (Exception ex){
            log.error(ex.getMessage(),ex);
        }
        return encrypted;
    }

    public String decryptUser(String encryptText){
        String decrypted = null;
        try{
            cipher.init(Cipher.DECRYPT_MODE, skeySpec);
            byte[] byteDecrypted = cipher.doFinal(Base64.getDecoder().decode(encryptText));
            decrypted =  new String(byteDecrypted);
        }catch (Exception ex){
            log.error(ex.getMessage(),ex);
        }
        return decrypted;
    }

}

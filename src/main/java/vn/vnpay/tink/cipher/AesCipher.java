package vn.vnpay.tink.cipher;

import com.google.crypto.tink.Aead;
import com.google.crypto.tink.aead.AeadConfig;
import lombok.extern.slf4j.Slf4j;

import java.io.FileOutputStream;
import java.util.Arrays;

@Slf4j
public class AesCipher extends Cipher {

    @Override
    public void encrypt(byte[] plain, byte[] aad) {
        try {
            AeadConfig.register();
            Aead aead = keysetHandle.getPrimitive(Aead.class);
            try (FileOutputStream stream = new FileOutputStream("out")) {
                stream.write(aead.encrypt(plain, aad));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void decrypt(byte[] cypher, byte[] aad) {
        try {
            AeadConfig.register();
            Aead aead = keysetHandle.getPrimitive(Aead.class);
            try (FileOutputStream stream = new FileOutputStream("out")) {
                stream.write(aead.decrypt(cypher, aad));
            }
        } catch (Exception e) {
            log.error(Arrays.toString(e.getStackTrace()));
        }
    }
}

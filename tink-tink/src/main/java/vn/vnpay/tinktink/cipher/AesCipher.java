package vn.vnpay.tinktink.cipher;

import com.google.crypto.tink.Aead;
import com.google.crypto.tink.aead.AeadConfig;
import lombok.extern.slf4j.Slf4j;
import vn.vnpay.tink.result.Result;

import java.io.FileOutputStream;
import java.security.GeneralSecurityException;

@Slf4j
public class AesCipher extends Cipher {

    @Override
    public Result encrypt(byte[] plain, byte[] aad) {
        try {
            log.info("Start encrypt with aes");
            AeadConfig.register();
            Aead aead = keysetHandle.getPrimitive(Aead.class);
            try (FileOutputStream stream = new FileOutputStream(
                    String.valueOf(System.currentTimeMillis()))) {
                stream.write(aead.encrypt(plain, aad));
            }
            return Result.SUCCESS;
        } catch (GeneralSecurityException e) {
            log.error("Stack trace.", e);
            return Result.ENCRYPTION_FAILURE;
        } catch (Exception e) {
            log.error("Stack trace.", e);
            return Result.FAILURE;
        }
    }

    @Override
    public Result decrypt(byte[] cypher, byte[] aad) {
        try {
            log.info("Start decrypt with decrypt");
            AeadConfig.register();
            Aead aead = keysetHandle.getPrimitive(Aead.class);
            try (FileOutputStream stream = new FileOutputStream(
                    String.valueOf(System.currentTimeMillis()))) {
                stream.write(aead.decrypt(cypher, aad));
            }
            return Result.SUCCESS;
        } catch (GeneralSecurityException e) {
            log.error("Stack trace.", e);
            return Result.DECRYPTION_FAILURE;
        } catch (Exception e) {
            log.error("Stack trace.", e);
            return Result.FAILURE;
        }
    }
}
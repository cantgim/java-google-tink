package vn.vnpay.tinktink.cipher;

import com.google.crypto.tink.Aead;
import com.google.crypto.tink.aead.AeadConfig;
import lombok.extern.slf4j.Slf4j;
import vn.vnpay.tinktink.result.Result;

import java.io.FileOutputStream;
import java.security.GeneralSecurityException;

@Slf4j
public class AesCipher extends Cipher {

    public AesCipher() throws GeneralSecurityException {
        AeadConfig.register();
    }

    /**
     * Encrypt data to file.
     *
     * @param plain Plain text as bytes
     * @param aad   Associated data as bytes
     * @return <code>Result<code/> status
     * @see vn.vnpay.tinktink.result.Result
     */
    @Override
    public Result encrypt(byte[] plain, byte[] aad) {
        try {
            log.info("Start encrypt with aes");
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
            log.info("Start decrypt with aes");
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

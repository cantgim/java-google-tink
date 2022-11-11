package vn.vnpay.tinktink.cipher;

import com.google.crypto.tink.Mac;
import com.google.crypto.tink.mac.MacConfig;
import lombok.extern.slf4j.Slf4j;
import vn.vnpay.tink.result.Result;

import java.io.FileOutputStream;
import java.security.GeneralSecurityException;

@Slf4j
public class HmacCipher extends Cipher {

    private final byte[] tag;

    public HmacCipher(byte[] tag) {
        this.tag = tag;
    }

    @Override
    public Result encrypt(byte[] plain, byte[] aad) {
        try {
            log.info("Start encrypt with hmac");
            MacConfig.register();
            Mac mac = keysetHandle.getPrimitive(Mac.class);
            try (FileOutputStream stream =
                         new FileOutputStream(String.valueOf(System.currentTimeMillis()))) {
                stream.write(mac.computeMac(plain));
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
    public Result decrypt(byte[] data, byte[] aad) {
        try {
            log.info("Start decrypt with hmac");
            MacConfig.register();
            Mac mac = keysetHandle.getPrimitive(Mac.class);
            mac.verifyMac(tag, data);
            return Result.SUCCESS;
        } catch (GeneralSecurityException e) {
            log.error("Stack trace.", e);
            return Result.VERIFICATION_FAILURE;
        } catch (Exception e) {
            log.error("Stack trace.", e);
            return Result.FAILURE;
        }
    }
}
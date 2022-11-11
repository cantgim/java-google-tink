package vn.vnpay.tinktink.cipher;

import com.google.crypto.tink.KeysetHandle;
import com.google.crypto.tink.PublicKeySign;
import com.google.crypto.tink.PublicKeyVerify;
import com.google.crypto.tink.signature.SignatureConfig;
import lombok.extern.slf4j.Slf4j;
import vn.vnpay.tinktink.result.Result;

import java.io.FileOutputStream;
import java.security.GeneralSecurityException;

@Slf4j
public class DigitalSignatureCipher extends Cipher {

    private final byte[] signature;

    public DigitalSignatureCipher(byte[] signature) {
        this.signature = signature;
    }

    @Override
    public Result encrypt(byte[] data, byte[] aad) {
        try {
            log.info("Start encrypt with digital signature");
            SignatureConfig.register();
            PublicKeySign signer = keysetHandle.getPrimitive(PublicKeySign.class);
            try (FileOutputStream stream = new FileOutputStream(
                    String.valueOf(System.currentTimeMillis()))) {
                stream.write(signer.sign(data));
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
            log.info("Start verify with digital signature");
            SignatureConfig.register();
            KeysetHandle publicKeysetHandle = keysetHandle.getPublicKeysetHandle();
            PublicKeyVerify verifier = publicKeysetHandle.getPrimitive(PublicKeyVerify.class);
            verifier.verify(signature, data);
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

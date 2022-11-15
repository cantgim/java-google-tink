package vn.vnpay.tinktink.cipher;

import com.google.crypto.tink.Aead;
import com.google.crypto.tink.CleartextKeysetHandle;
import com.google.crypto.tink.JsonKeysetReader;
import com.google.crypto.tink.KeysetHandle;
import vn.vnpay.tinktink.result.Result;

import java.io.IOException;
import java.security.GeneralSecurityException;

public abstract class Cipher {
    protected KeysetHandle keysetHandle;

    public void withCleartextKeysetHandle(String keyFile) throws IOException,
            GeneralSecurityException {
        this.keysetHandle =
                CleartextKeysetHandle.read(JsonKeysetReader.withPath(keyFile));
    }

    public void withEncryptKeysetHandle(String keyFile, String decryptKeyFile)
            throws IOException, GeneralSecurityException {
        Aead masterKey = CleartextKeysetHandle.read(JsonKeysetReader.withPath(decryptKeyFile))
                .getPrimitive(Aead.class);
        this.keysetHandle = KeysetHandle.read(JsonKeysetReader.withPath(keyFile), masterKey);
    }

    public abstract Result encrypt(byte[] plain, byte[] aad);

    public abstract Result decrypt(byte[] cypher, byte[] aad);
}

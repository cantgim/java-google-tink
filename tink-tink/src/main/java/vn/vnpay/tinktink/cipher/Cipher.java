package vn.vnpay.tinktink.cipher;

import com.google.crypto.tink.CleartextKeysetHandle;
import com.google.crypto.tink.JsonKeysetReader;
import com.google.crypto.tink.KeysetHandle;
import vn.vnpay.tink.result.Result;

import java.io.IOException;
import java.security.GeneralSecurityException;

public abstract class Cipher {
    protected KeysetHandle keysetHandle;

    public void setKeysetHandle(String keyFile) throws IOException, GeneralSecurityException {
        this.keysetHandle =
                CleartextKeysetHandle.read(JsonKeysetReader.withPath(keyFile));
    }

    public abstract Result encrypt(byte[] plain, byte[] aad);

    public abstract Result decrypt(byte[] cypher, byte[] aad);
}

package vn.vnpay.tink.crypter;

import com.google.crypto.tink.KeysetHandle;
import lombok.Getter;

@Getter
public abstract class Crypter {
    public abstract void encrypt(byte[] plain);
    public abstract void decrypt(byte[] cypher);
}

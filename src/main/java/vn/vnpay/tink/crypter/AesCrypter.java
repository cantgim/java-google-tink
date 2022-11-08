package vn.vnpay.tink.crypter;

import com.google.crypto.tink.*;
import com.google.crypto.tink.aead.AeadConfig;
import com.google.crypto.tink.config.TinkConfig;
import lombok.extern.slf4j.Slf4j;
import vn.vnpay.tink.crypter.Crypter;

import java.io.File;
import java.util.Arrays;

@Slf4j
public class AesCrypter extends Crypter {
    @Override
    public void encrypt(byte[] plain) {
        try {
            AeadConfig.register();
            KeysetHandle keysetHandle =
                    CleartextKeysetHandle.read(JsonKeysetReader.withFile(new File("test.json")));
//            KeysetHandle keysetHandle = KeysetHandle.generateNew(KeyTemplates.get("AES_GCM"));

//            CleartextKeysetHandle.write(keysetHandle, JsonKeysetWriter.withFile(new File()));
            Aead aead = keysetHandle.getPrimitive(Aead.class);
            byte[] cyphertext = aead.encrypt("This is a box".getBytes(), "".getBytes());
            byte[] decrypt = aead.decrypt(cyphertext, "".getBytes());
            System.out.println(new String(cyphertext));
            System.out.println(new String(decrypt));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void decrypt(byte[] cypher) {
        try {
            KeysetHandle keysetHandle =
                    CleartextKeysetHandle.read(JsonKeysetReader.withFile(new File("test.json")));
            Aead aead = keysetHandle.getPrimitive(Aead.class);
            aead.decrypt(cypher, )
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

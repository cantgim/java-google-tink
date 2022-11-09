package vn.vnpay.tink.facade;

import lombok.extern.slf4j.Slf4j;
import vn.vnpay.tink.cipher.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.Optional;
import java.util.Scanner;

@Slf4j
public class AppFacade {
    private static final Scanner sc = new Scanner(System.in);
    private static final String EXIT = "0";
    private static final String ENCRYPT = "1";
    private static final String DECRYPT = "2";
    private static final String AES = "1";
    private static final String HMAC = "2";
    private static final String SHA = "3";
    private static final String DIGITAL_SIGNATURE = "4";

    public void start() {
        try {
            String cmd;
            do {
                System.out.print(
                        "1. encrypt\n" +
                                "2. decrypt\n" +
                                "0. Exit\n" +
                                "Option: ");
                cmd = sc.nextLine();
                action(cmd);
            } while (EXIT.equals(cmd));
        } catch (Exception e) {
            log.error(Arrays.toString(e.getStackTrace()));
        }
    }

    private void action(String action) throws IOException, GeneralSecurityException {
        if (action == null) return;
        switch (action) {
            case ENCRYPT:
                log.info("Encrypt");
                Optional<Cipher> cipher = selectCipherType();
                if (!cipher.isPresent()) return;
                Cipher encryptCipher = cipher.get();
                System.out.print("Data to be encrypt: ");
                encryptCipher.encrypt(Files.readAllBytes(Paths.get(sc.nextLine())), "".getBytes());
                break;
            case DECRYPT:
                log.info("Decrypt");
                cipher = selectCipherType();
                if (!cipher.isPresent()) return;
                Cipher decryptCipher = cipher.get();
                System.out.print("Data to be decrypt: ");
                decryptCipher.decrypt(Files.readAllBytes(Paths.get(sc.nextLine())), "".getBytes());
                break;
            case EXIT:
                log.info("Exit");
                System.exit(0);
                break;
            default:
                log.info("No action specified");
                System.exit(1);
        }
    }

    private Optional<Cipher> selectCipherType() throws IOException, GeneralSecurityException {
        System.out.print("What type of encryption u wanna use?\n" +
                "1. AES\n2. HMAC\n3. SHA\n4. Digital Signature\nOption: ");
        String type = sc.nextLine();
        System.out.print("Keyfile: ");
        String keyFile = sc.nextLine();
        switch (type) {
            case AES:
                AesCipher aesCipher = new AesCipher();
                aesCipher.setKeysetHandle(keyFile);
                return Optional.of(aesCipher);
            case HMAC:
                HmacCipher hmacCipher = new HmacCipher();
                hmacCipher.setKeysetHandle(keyFile);
                return Optional.of(hmacCipher);
            case SHA:
                ShaCipher shaCipher = new ShaCipher();
                shaCipher.setKeysetHandle(keyFile);
                return Optional.of(shaCipher);
            case DIGITAL_SIGNATURE:
                DigitalSignatureCipher dsc = new DigitalSignatureCipher();
                dsc.setKeysetHandle(keyFile);
                return Optional.of(dsc);
            default:
                log.info("No action specified");
                System.exit(1);
        }
        return Optional.empty();
    }
}

package vn.vnpay.tink.facade;

import lombok.extern.slf4j.Slf4j;
import vn.vnpay.tinktink.cipher.AeadCipher;
import vn.vnpay.tinktink.cipher.Cipher;
import vn.vnpay.tinktink.cipher.DigitalSignatureCipher;
import vn.vnpay.tinktink.cipher.MacCipher;
import vn.vnpay.tinktink.result.Result;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.GeneralSecurityException;
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

    private static final String DEFAULT_AAD = "";


    public void start() {
        try {
            String cmd;
            do {
                System.out.print(
                        "1. encrypt/sign\n" + "2. decrypt/verify\n" + "0. Exit\n" + "Option: ");
                cmd = sc.nextLine();
                action(cmd);
            } while (EXIT.equals(cmd));
        } catch (Exception e) {
            log.error("Stack trace.", e);
        } finally {
            sc.close();
        }
    }

    private void action(String action) throws IOException, GeneralSecurityException {
        if (action == null) return;
        switch (action) {
            case ENCRYPT:
                log.info("Encrypt");
                Optional<Cipher> cipher = selectCipherType(ENCRYPT);
                if (!cipher.isPresent()) return;
                Cipher encryptCipher = cipher.get();
                System.out.print("Data to be encrypt/sign: ");
                Result encrypt = encryptCipher.encrypt(Files.readAllBytes(Paths.get(sc.nextLine())),
                        DEFAULT_AAD.getBytes());
                System.out.println(encrypt);
                break;
            case DECRYPT:
                log.info("Decrypt");
                cipher = selectCipherType(DECRYPT);
                if (!cipher.isPresent()) return;
                Cipher decryptCipher = cipher.get();
                System.out.print("Data to be decrypt/verify: ");
                Result decrypt = decryptCipher.decrypt(Files.readAllBytes(Paths.get(sc.nextLine())),
                        DEFAULT_AAD.getBytes());
                System.out.println(decrypt);
                break;
            case EXIT:
                log.info("Exit");
                sc.close();
                System.exit(0);
                break;
            default:
                log.info("No action specified");
                sc.close();
                System.exit(1);
        }
    }

    private Optional<Cipher> selectCipherType(String option)
            throws IOException, GeneralSecurityException {
        System.out.print(
                "What type of encryption u wanna use?\n" + "1. AES\n2. HMAC\n3. SHA\n4. Digital " + "Signature\nOption: ");
        String type = sc.nextLine();
        System.out.print("Keyfile: ");
        String keyFile = sc.nextLine();
        switch (type) {
            case AES:
                AeadCipher aeadCipher = new AeadCipher();
                aeadCipher.withCleartextKeysetHandle(keyFile);
                return Optional.of(aeadCipher);
            case HMAC:
                byte[] tag = new byte[0];
                if (DECRYPT.equals(option)) {
                    System.out.print("Your tag file: ");
                    tag = Files.readAllBytes(Paths.get(sc.nextLine()));
                }
                MacCipher macCipher = new MacCipher(tag);
                macCipher.withCleartextKeysetHandle(keyFile);
                return Optional.of(macCipher);
            case SHA:
                return Optional.empty();
            case DIGITAL_SIGNATURE:
                byte[] signature = new byte[0];
                if (DECRYPT.equals(option)) {
                    System.out.print("Your signature file: ");
                    signature = Files.readAllBytes(Paths.get(sc.nextLine()));
                }
                DigitalSignatureCipher dsc = new DigitalSignatureCipher(signature);
                dsc.withCleartextKeysetHandle(keyFile);
                return Optional.of(dsc);
            default:
                log.info("No action specified");
                sc.close();
                System.exit(1);
        }
        return Optional.empty();
    }
}

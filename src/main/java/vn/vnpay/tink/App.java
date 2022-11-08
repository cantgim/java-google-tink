package vn.vnpay.tink;

import vn.vnpay.tink.crypter.AesCrypter;

import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner sc = null;
        try {
            sc = new Scanner(System.in);
            String cmd = "";
            do {
                System.out.print(
                        "1. encrypt\n" +
                        "2. decrypt\n" +
                        "Options: ");
                    cmd = sc.nextLine();
                AesCrypter aes = new AesCrypter();
                aes.encrypt();
            } while (!"0".equals(cmd));
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if (sc != null) {
                sc.close();
            }
        }
    }
}
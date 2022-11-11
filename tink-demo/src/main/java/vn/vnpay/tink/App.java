package vn.vnpay.tink;

import lombok.extern.slf4j.Slf4j;
import vn.vnpay.tink.facade.AppFacade;

@Slf4j
public class App {
    public static void main(String[] args) {
        AppFacade facade = new AppFacade();
        facade.start();
    }
}
package vn.vnpay.tinktink.result;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Result {
    SUCCESS(0, "Success"),
    DECRYPTION_FAILURE(1, "Cannot decrypt data"),
    ENCRYPTION_FAILURE(2, "Cannot encrypt data"),
    VERIFICATION_FAILURE(3, "Cannot verify data"),
    FAILURE(4, "An error occurred");
    private final int code;
    private final String message;
}

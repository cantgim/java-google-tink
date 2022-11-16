package vn.vnpay.tinktink.cipher;

import org.junit.jupiter.api.Test;
import vn.vnpay.tinktink.result.Result;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AeadCipherTest {

    @Test
    public void shouldEncrypt_success() {
        AeadCipher aeadCipher = mock(AeadCipher.class);
        when(aeadCipher.encrypt("This is plain text".getBytes(), "".getBytes())).thenReturn(
                Result.SUCCESS);
        assertEquals(Result.SUCCESS,
                aeadCipher.encrypt("This is plain text".getBytes(), "".getBytes()));
    }
}

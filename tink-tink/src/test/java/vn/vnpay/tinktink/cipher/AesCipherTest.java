package vn.vnpay.tinktink.cipher;

import org.junit.jupiter.api.Test;
import vn.vnpay.tinktink.result.Result;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AesCipherTest {

    @Test
    public void shouldReturnEncryptSuccess() {
        AesCipher aesCipher = mock(AesCipher.class);
        when(aesCipher.encrypt("This is plain text".getBytes(), "".getBytes())).thenReturn(
                Result.SUCCESS);
        assertEquals(Result.SUCCESS,
                aesCipher.encrypt("This is plain text".getBytes(), "".getBytes()));
    }
}

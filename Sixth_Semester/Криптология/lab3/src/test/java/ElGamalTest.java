
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class ElGamalTest {
    @Test
    void run() {
        String text = "MyText";
        for(byte b : text.getBytes()) {
            BigInteger input = BigInteger.valueOf(b);
            Assert.assertEquals(input, ElGamal.run(input));
        }

    }
}
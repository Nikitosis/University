import java.math.BigDecimal;
import java.security.SecureRandom;
import java.util.*;
import java.math.BigInteger;

/**
 *
 * El Gamal algorithm
 *
 * Generating of key
 *  1. generate random simple number p
 *  2. choose integer number g, g is antiderivative root of p
 *  3. choose number x , 1 < x < p-1
 *  4. h = g^x mod p - public key
 *  5. x - private key
 *
 * Encryption
 *  1. choose session key k, 1 < k < p-1
 *  2. Pair (a,b) is a encryption text
 *     a = g^k mod p
 *     b = (y^k * M) mod p
 *
 * Decryption
 *  M = (b*a^(p-1-x)) mod p
 *
 */

public class ElGamal {

    static Random sc = new SecureRandom();

    public static BigInteger run (BigInteger message){
        //generate public key
        BigInteger p = BigInteger.probablePrime(64, sc);
        BigInteger g = new BigInteger("3");
        BigInteger secretKey = BigInteger.probablePrime(63, sc);
        BigInteger h = g.modPow(secretKey, p);

        //encryption
        BigInteger k = new BigInteger(63, sc);
        BigInteger EC = message.multiply(h.modPow(k, p)).mod(p);

        //cryptoText
        BigInteger a = g.modPow(k, p);
        BigInteger b = a.modPow(secretKey, p);

        //decryption
        BigInteger d = b.modInverse(p);
        BigInteger M = d.multiply(EC).mod(p);
        return M;
    }
}



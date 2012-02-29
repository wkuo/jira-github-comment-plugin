package com.atex.jira.plugins.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class SignatureUtil {
    private static final String HMAC_SHA1_ALGORITHM = "HmacSHA1";
    
    private static final Logger LOGGER = LoggerFactory.getLogger(SignatureUtil.class);

    /**
    * Computes RFC 2104-compliant HMAC signature.
    * * @param data
    * The data to be signed.
    * @param key
    * The signing key.
    * @return
    * The Base64-encoded RFC 2104-compliant HMAC signature.
    * @throws
    * java.security.SignatureException when signature generation fails
    */
    public static String calculateRFC2104HMAC(String data, String key)
    throws java.security.SignatureException
    {
        String result;
        try {
    
            // get an hmac_sha1 key from the raw key bytes
            SecretKeySpec signingKey = new SecretKeySpec(key.getBytes(), HMAC_SHA1_ALGORITHM);
        
            // get an hmac_sha1 Mac instance and initialize with the signing key
            Mac mac = Mac.getInstance(HMAC_SHA1_ALGORITHM);
            mac.init(signingKey);
        
            // compute the hmac on input data bytes
            byte[] rawHmac = mac.doFinal(data.getBytes());
        
            // base64-encode the hmac
            // result = Encoding.EncodeBase64(rawHmac);
            StringBuilder resultSB = new StringBuilder();
            for (byte b : rawHmac) {
                resultSB.append(Character.forDigit((b & 240) >> 4, 16));
                resultSB.append(Character.forDigit((b & 15), 16));
            }       
            result = resultSB.toString();
        }  catch (InvalidKeyException e) {
            throw new SignatureException("Failed to generate HMAC : " + e.getMessage(), e);
        } catch (NoSuchAlgorithmException e) {
            throw new SignatureException("Failed to generate HMAC : " + e.getMessage(), e);
        }
        return result;
    }
    
    public static final String encode(String raw) {
        try {
            return URLEncoder.encode(raw, "UTF-8").replace("+", "%20");            
        } catch (UnsupportedEncodingException e) {
            LOGGER.error(e.getMessage(), e);
            return "";
        } catch (NullPointerException e) {
            LOGGER.warn(e.getMessage(), e);
            return "";
        }
    }
   
}

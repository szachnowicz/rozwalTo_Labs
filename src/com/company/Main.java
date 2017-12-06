package com.company;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collections;

public class Main {

    public static void main(String[] args) throws UnsupportedEncodingException {
	// write your code here
        String text = "GCg7Ozs7Oy01e3oNMz4gP3ogP3ovPjs2NXoZM3opMz96KDUgKSAjPCg1LTs5ei4/MSkueiA7KSAjPCg1LTs0I3oqNTA/PiM0OSAjN3o4OzAuPzd0ehQ1ej41" +
                "OCg7dno8Njs9O3ouNWB6CBUADRsWBSEJMzQ9Nj8CNSgYIy4/GTMqMj8oJw==";


        byte[] decode = Base64.getDecoder().decode(new String(text).getBytes("UTF-8"));
        String byteText = "";
        for (byte b : decode) {
            byteText+=b;
        }

        System.out.println(byteText);

        String rozwal = "ROZWAL";
       String xored;
        int key=0;
            for (int k = 0; k < 256; k++) {

            xored = "";
            for (int j = 0; j < rozwal.length(); j++) {
                char c = rozwal.charAt(j);
                xored += (byte) (c ^ k);

            }
            if (byteText.contains(xored))
                key = k;
            }

        String unCoded="";
        for (byte b : decode) {
            int value = b ^ key;
    unCoded += (char)value ;

        }

        System.out.println(unCoded);






    }
}

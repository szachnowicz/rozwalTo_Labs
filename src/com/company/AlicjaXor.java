package com.company;

import sun.misc.BASE64Decoder;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class AlicjaXor {

    public String unDecodedText = "PAADREshCgcRDgZPBAoPSxgGBBEOAR0ABksVEUUeDw4YCksoBlQWAg5PBgoRGBUNAxkEGBUGS" +
            "x8KGkUfDgQHEUVLIgEWAgoDVAoFSw0NBksFBhEGBEsLGBARGBUNSUsKDQ1FBgQVGAwcDk8WHA" +
            "cETwQXEQ4fBgocCgsOAAUCClQADQ4EABwcBQoTCksKGxUOHkVPOQoRDk8eABgRDA4ASx8dGwY" +
            "DDk8EBA8PBhoCHksVEQcSSwEdAEsJFhgKSxEOVBEZHgsaCkVLIB9JSw0DFQIKSxsbRTkkNSMk" +
            "JzQUNQkCCAo9FiIGHwYAGBgKEBg=";
    private int[] changesArray;
    private int[] number;

    public static void main(String[] args) throws UnsupportedEncodingException {
AlicjaXor alicjaXor = new AlicjaXor();
        try {
            System.out.println(alicjaXor.decodeText(null));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private String decodeText(String text) throws IOException {
        if(text == null)
            text = unDecodedText;
        byte[] arrayToDecode = getArrayToDecode();
        byte[] arrayKey = searchKey(arrayToDecode);
        int i = 0;
        while (i < arrayToDecode.length) {
            for (int k = 0; k < arrayKey.length && i < arrayToDecode.length; i++, k++) {
                arrayToDecode[i] = (byte) (arrayToDecode[i] ^ arrayKey[k]);
            }
        }
        return new String(arrayToDecode);

    }

    private byte[] searchKey(byte[] arrayToDecode) throws IOException {
        byte[] result = null;
        intializeArraysShiftAndNumber(arrayToDecode);
        countNumberOfShifts(arrayToDecode);
        sortArraysShiftAndNumber();

        int keyLength = NWD(changesArray[0], NWD(changesArray[1], changesArray[2]));
        result = new byte[keyLength];

        for (int i = 0; i < keyLength; i++) {
            result[i] = searchKeyLetter(i, keyLength, arrayToDecode);
        }
        return result;
    }

    private void sortArraysShiftAndNumber() {
        int temp;
        boolean change = true;
        while (change) {
            change = false;
            for (int i = 0; i < changesArray.length - 1; i++) {
                if (number[i] < number[i + 1]) {
                    temp = changesArray[i + 1];
                    changesArray[i + 1] = changesArray[i];
                    changesArray[i] = temp;

                    temp = number[i + 1];
                    number[i + 1] = number[i];
                    number[i] = temp;

                    change = true;
                }
            }
        }
    }

    private byte[] getArrayToDecode() throws IOException {
        BASE64Decoder base64Decoder = new BASE64Decoder();
        return base64Decoder.decodeBuffer(unDecodedText);
    }


    private byte searchKeyLetter(int number, int keyLength, byte[] arrayToDecode) {
        int[] arrayInstances = new int[256];
        fillArrayWithZeros(arrayInstances);
        for (int j = number; j < arrayToDecode.length; j = j + keyLength) {
            for (int k = 0; k < arrayInstances.length; k++) {
                int wynik = arrayToDecode[j] ^ k;
                if (wynik == 32 || 65 <= wynik && wynik <= 90 || 97 <= wynik && wynik <= 122)
                    arrayInstances[k]++;
            }
        }
        return (byte) getMaxValueIndex(arrayInstances);
    }

    private int getMaxValueIndex(int[] arrayInstances) {
        int maxIndex = 0;
        int maxValue = 0;
        for (int i = 0; i < arrayInstances.length; i++) {
            if (maxValue < arrayInstances[i]) {
                maxValue = arrayInstances[i];
                maxIndex = i;
            }
        }

        return maxIndex;
    }


    private void intializeArraysShiftAndNumber(byte[] arrayToDecode) {
        changesArray = new int[arrayToDecode.length];
        number = new int[arrayToDecode.length];
        for (int k = 0; k < arrayToDecode.length; k++) {
            changesArray[k] = k + 1;
            number[k] = 0;
        }
    }

    private void countNumberOfShifts(byte[] arrayToDecode) {
        for (int i = 0; i < changesArray.length; i++) {
            for (int k = 0; k < arrayToDecode.length - i - 1; k++) {
                if (arrayToDecode[k] == arrayToDecode[k + changesArray[i]])
                    number[i]++;
            }
        }
    }

    private int NWD(int a, int b) {
        while (a != b) {
            if (a > b)
                a -= b;
            else
                b -= a;
        }

        return a;
    }


    public void fillArrayWithZeros(int[] array) {
        for (int i = 0; i < array.length; i++) {
            array[i] = 0;
        }


    }

}

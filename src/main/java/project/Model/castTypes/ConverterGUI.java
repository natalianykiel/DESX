package project.Model.castTypes;

import java.util.Arrays;

public class ConverterGUI {

    //KONWERTUJE TABLICE STRING NA TABLICE BYTE
    public static byte[] stringToByteTab(String bytes) {
        byte[] temp = new byte[bytes.length()];
        for (int i = 0; i < bytes.length(); i++) {
            temp[i] = (byte) bytes.charAt(i);
        }
        return temp;
    }
    //KONWERSJA TABLICY BYTE'ÓW NA STRING
    public static String byteTabToString(byte[] bytes) {
        StringBuilder temp = new StringBuilder();
        int len = bytes.length;
        for (int i = 0; i < len; i++) {
            temp.append((char) bytes[i]);
        }
        return temp.toString();
    }

    //DOPEŁNIANIE BITAMI DO PEŁNEGO BYTE
    public static byte[] completeTheBits(byte[] bytes) {
        int numberOfBytes = bytes.length;
        byte howMany = 0;
        while (numberOfBytes % 8 != 0) {
            numberOfBytes++;
            howMany++;
        }
        byte[] filled = new byte[numberOfBytes];
        System.arraycopy(bytes, 0, filled, 0, bytes.length);
        if(howMany != 0) {
            Arrays.fill(filled,bytes.length, numberOfBytes, (byte) 0);
            filled[filled.length-1] = howMany;
        }
        return filled;
    }

    //PRZYCINANIE OSTATNICH BYTE'ÓW NA KOŃCU TABLICY BYTE'ÓW
    public static byte[] cutLastBytes(byte[] bytes) {
        int howMany = bytes[bytes.length-1];
        int size = bytes.length-howMany;
        if(howMany > 31 || howMany < 0) {
            return bytes;
        }

        byte[] filled = new byte[size];

        System.arraycopy(bytes, 0, filled,0,size);

        return filled;
    }
}

package project.Model.castTypes;

import java.util.Arrays;

public class Converter {

    //POTRZEBNE DO PERMUTACJI Z WZORCEM S-BOX
    public static byte[] byteNumberTo4bTab(byte number){
        byte[] blockByte = new byte[4];
        byte temporaryByte;
        //dla 10 dostaniemy pokolei 0, 1, 0, 1 czyli jest odwrócone
        for (int i=0; i<4; i++){
            blockByte[i] = (byte) (number%2);
            number = (byte) (number/2);
        }

        //teraz ta petla odwraca do zeby bylo 1010
        for(int i=0; i < 2; i++){
            temporaryByte = blockByte[i];
            blockByte[i] = blockByte[3 - i];
            blockByte[3-1] = temporaryByte;
        }
        return blockByte;
    }

    // POTRZEBNE DO byteTabTo8bTab
    public static byte[] byteNumberTo8bTab(byte value) {
        byte[] finalForm = new byte[8];
        int number = value;
        if (number > 0) {
            for (int i = 7; i >= 0; i--) {
                finalForm[i] = (byte) (number % 2 == 1 ? 1 : 0);
                number = number / 2;
            }
        } else {
            number = number * (-1);
            for (int i = 7; i >= 0; i--) {
                finalForm[i] = (byte) (number % 2 == 1 ? 1 : 0);
                number = number / 2;
            }
            for (int i = 0; i < 8; i++) {
                finalForm[i] ^= 1;
            }
            for (int i = 7; i >= 0; i--) {
                if (finalForm[i] == 0) {
                    finalForm[i] = 1;
                    break;
                }
                finalForm[i] = 0;
            }
        }
        return finalForm;
    }

    //POTRZEBNE DO KONWERSJI KLUCZA NA 64 BITY
    public  static byte[] byteTabTo8bTab(byte[] bytes){
        int iteracje=0;
        byte[] wynik = new byte[64];
        for(int i=0; i<8; i++){
            byte[] bits8 = byteNumberTo8bTab(bytes[i]);
            for(int j=0; j<8; j++){
                wynik[iteracje++] = bits8[j];
            }
        }
        return wynik;
    }

    //POTRZEBNE DO KONWERSJI WYNIKU DZIAŁANIA DES NA BYTE
    public static byte[] binaryChainToByteForm(byte[] bits) {
        int iterator = 0;
        byte[] finalForm = new byte[8];
        for (int i = 0; i < 8; i++) {
            int upperLimit = 128;
            for (int j = 0; j < 8; j++) {
                if (bits[iterator] == 1) {
                    finalForm[i] += (byte) upperLimit;
                }
                iterator++;
                upperLimit /= 2;
            }
        }
        return finalForm;
    }

    //to do GUI ale zmienie to jeszcze chyba

    public static byte[] getCountOfBytes(byte[] bytes, int index, int count) {
        byte[] temp = new byte[count];
        for (int i = 0; i < count; i++) {
            temp[i] = bytes[index];
            index++;
        }
        return temp;
    }

    public static byte[] stringToByteTab(String bytes) {
        byte[] temp = new byte[bytes.length()];
        for (int i = 0; i < bytes.length(); i++) {
            temp[i] = (byte) bytes.charAt(i);
        }
        return temp;
    }

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

    /**
     * Method that is Truncates the number of bytes specified at the end
     * @param bytes Byte Array
     * @return byte Table
     */

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

    public static String byteTabToString(byte[] bytes) {
        StringBuilder temp = new StringBuilder();
        int len = bytes.length;
        for (int i = 0; i < len; i++) {
            temp.append((char) bytes[i]);
        }
        return temp.toString();
    }






}

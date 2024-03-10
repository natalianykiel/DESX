package org.example.castTypes;

public class Converter {

    /**
     * Potrzebne przy sbox w Permutation
     * @param number
     * @return
     */
    public static byte[] ConvertByteNumberTo4b(byte number){
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

    public  static byte[] byteTabToBinary(byte[] bytes){
        int iteracje=0;
        byte[] wynik = new byte[64];
        for(int i=0; i<8; i++){
            byte[] bits8 = byteTo8BitTable(bytes[i]);
            for(int j=0; j<8; j++){
                wynik[iteracje++] = bits8[j];
            }
        }
        return wynik;
    }
    ///potrzebne do des
    public static byte[] byteTo8BitTable(byte value) {
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
}

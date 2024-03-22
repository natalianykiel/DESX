package project.Model.crypto.algorithm;

import project.Model.castTypes.Converter;
import project.Model.functions.XOR;

public class DesX {

    public byte[] encrypt(byte[] plainText, byte[] keyInternal, byte[] keyDes, byte[] keyExternal) {
        Des des = new Des();
        byte[] finalText = new byte[plainText.length];
        for(int i = 0; i < plainText.length/8; i++){
            //blok poddajemy xor'owaniu
            byte[] tmp = XOR.XOR(Converter.getCountOfBytes(plainText, i * 8, 8), keyInternal);
            //blok przechodzi proces enkrypcji DES
            tmp  = des.encrypt(tmp, keyDes);
            //ponowne xor'owanie bloku
            tmp = XOR.XOR(keyExternal,tmp);
            //przepisywanie 8 byte'ów wynikowych
            System.arraycopy(tmp, 0, finalText, i * 8, 8);
        }
        return finalText;
    }

    public byte[] decrypt(byte[] plainText, byte[] keyInternal, byte[] keyDes, byte[] keyExternal) {
        Des des = new Des();
        byte[] finalText = new byte[plainText.length];
        for(int i = 0; i < plainText.length/8; i++){
            //blok poddajemy xor'owaniu
            byte[] tmp = XOR.XOR(Converter.getCountOfBytes(plainText, i * 8, 8), keyExternal);
            //blok przechodzi proces enkrypcji DES
            tmp = des.decrypt(tmp, keyDes);
            //ponowne xor'owanie bloku
            tmp = XOR.XOR(keyInternal,tmp);
            //przepisywanie 8 byte'ów wynikowych
            System.arraycopy(tmp, 0, finalText, i * 8, 8);
        }
        return finalText;
    }

}





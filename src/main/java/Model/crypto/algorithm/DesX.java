package Model.crypto.algorithm;

import Model.castTypes.Converter;
import Model.functions.XOR;

public class DesX {

    public byte[] encrypt(byte[] plainText, byte[] keyInternal, byte[] keyDes, byte[] keyExternal) {
        Des des = new Des();
        byte[] finalText = new byte[plainText.length];
        for(int i = 0; i < plainText.length/8; i++){
            //The block is Xor-processed
            byte[] tmp = XOR.XOR(Converter.getCountOfBytes(plainText, i * 8, 8), keyInternal);
            //The block is DES-processed
            tmp  = des.encrypt(tmp, keyDes);
            //The block is again Xor-processed
            tmp = XOR.XOR(keyExternal,tmp);
            //rewriting the block (8 bytes) to the resulting Array
            System.arraycopy(tmp, 0, finalText, i * 8, 8);
        }
        return finalText;
    }

    public byte[] decrypt(byte[] plainText, byte[] keyInternal, byte[] keyDes, byte[] keyExternal) {
        Des des = new Des();
        byte[] finalText = new byte[plainText.length];
        for(int i = 0; i < plainText.length/8; i++){
            //The block is Xor-processed
            byte[] tmp = XOR.XOR(Converter.getCountOfBytes(plainText, i * 8, 8), keyExternal);
            //The block is DES-processed
            tmp = des.decrypt(tmp, keyDes);
            //The block is again Xor-processed
            tmp = XOR.XOR(keyInternal,tmp);
            //rewriting the block (8 bytes) to the resulting Array
            System.arraycopy(tmp, 0, finalText, i * 8, 8);
        }
        return finalText;
    }

}





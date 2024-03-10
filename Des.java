package org.example.crypto.algorithm;

import org.example.castTypes.Converter;
import org.example.patterns.Tables;

import org.example.functions.XOR;
import org.example.functions.Permutation;

import org.example.crypto.component.DataBlock;
import org.example.crypto.component.KeyBlock;
public class Des {
    private DataBlock dataBlock;
    private KeyBlock keyBlock;

    public byte[] encrypt(byte[] byteText, byte[] byteKey){
        //konwertujemy block na 64bity
        byte[] binaryText = Converter.byteTabToBinary(byteText);
        //konwersja key na 64bit
        byte[] binaryKey = Converter.byteTabToBinary(byteKey);

        keyBlock = new KeyBlock(binaryKey);
        byte[][] keys = keyBlock.getLastPodklucz();
        dataBlock = new DataBlock(binaryText);

        //get left and right side
        byte[] left = dataBlock.getLeft();
        byte[] right = dataBlock.getRight();

        byte[] expandedRightSite = new byte[48];

        byte[] finalForm = new byte[64];


        for (int i = 0; i < 16; i++) {
            //Expanding permutation
            expandedRightSite = Permutation.Permutation(Tables.EP,right,48);
            //Get the i-subkey
            byte[] key = keys[i];
            expandedRightSite = XOR.XOR(expandedRightSite,key);
            expandedRightSite = Permutation.sBoxTransformation(expandedRightSite);
            //Straight Permutation
            expandedRightSite = Permutation.Permutation(Tables.P, expandedRightSite,32);
            expandedRightSite = XOR.XOR(left,expandedRightSite);
            left = right;
            right = expandedRightSite;
        }

        System.arraycopy(right, 0, finalForm, 0, 32);
        System.arraycopy(left, 0, finalForm, 32, 32);

        //Inverse Initial Permutation
        finalForm = Permutation.Permutation(Tables.IP1,finalForm,64);

        //Binary Array to Byte Array
        byte[] toByte = Converter.binaryChainToByteForm(finalForm);

        return toByte;
    }


    //tutaj decrypt


}

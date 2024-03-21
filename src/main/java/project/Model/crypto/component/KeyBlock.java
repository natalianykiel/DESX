package project.Model.crypto.component;

import project.Model.patterns.Tables;
import project.Model.functions.Permutation;

public class KeyBlock {
    //nazywam te klucze tak bo kazdy jest to innej dlugosci
    //polecam wyklad str 8 tam masz wytlumaczone czm 48, 56 i 64

    private byte[] key56;
    private byte[] key48 = new byte[48];
    private byte[] key64;
    private byte[][] last16Podklucz = new byte[16][48];

    //MODTYFIKACJA Z KEY64 NA KEY56
    public KeyBlock(byte[] block) {
        this.key64 = block;
        this.key56 = Permutation.Permutation(Tables.PC1, key64, 56);
    }

    //GENEROWANIE KLUCZY DLA RUND
    public byte[][] getLastPodklucz() {
        for (int i = 0; i < Tables.shiftBits.length; i++) {
            //przesun bity z poczatku na koniec w zaleznosci od info w tabeli przesuniec
            przesuniecieLewo(Tables.shiftBits[i], i);
        }
        return last16Podklucz;
    }

    //POTRZEBNE DO UZYSKANIA KEY48
    public void przesuniecieLewo(byte count, int podkluczNumber) {
        byte temporaryL;
        byte temporaryR;
        for (int i = 0; i < count; i++) {
            //przesuniecie bitowe
            temporaryL = this.key56[0];
            temporaryR = this.key56[28];
            int iteracje = 28;
            for (int j = 0; j < 27; j++) {
                this.key56[j] = this.key56[j + 1];
                this.key56[iteracje] = this.key56[iteracje + 1];
                iteracje++;
            }
            this.key56[27] = temporaryL;
            this.key56[55] = temporaryR;
        }

        key48 = Permutation.Permutation(Tables.PC2, this.key56, 48);
        for (int j = 0; j < 48; j++) {
            last16Podklucz[podkluczNumber][j] = key48[j];
        }

    }
}




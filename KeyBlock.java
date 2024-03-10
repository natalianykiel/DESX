package org.example.crypto.component;

import org.example.functions.Permutation;
import org.example.patterns.Tables;

//ta klasa to wszelkiego rodzaju zabawa z kluczami
//generowanie podklucza, permutacja klucza i przechowowywanie go
//i ta jest dla desx a nie des jka cos

//chwila dla debila
//w desx masz 2 permutacje ale to jebac to
//liczy sie to ze masz 16 rund jebania sie z podkluczem
//rb 16 podkluczy tam chyba 48 bitowych
public class KeyBlock {
    //nazywam te klucze tak bo kazdy jest to innej dlugosci
    //polecam wyklad str 8 tam masz wytlumaczone czm 48, 56 i 64

    private byte[] key56;
    private byte[] key48 = new byte[48];
    private byte[] key64;
    private byte[][] last16Podklucz = new byte[16][48];

    public KeyBlock(byte[] block) {
        this.key64 = block; //tu sa 64 bity
        //i teraz potrzeba pierwsza permutacje do 56 bitsów
        this.key56 = Permutation.Permutation(Tables.PC1, key64, 56);
    }

    //generowanka 16 podkluczy
    public byte[][] getLastPodklucz() {
        for (int i = 0; i < Tables.shiftBits.length; i++) {
            //przesun bity z poczatku na koniec w zaleznosci od info w tabeli przesuniec
            przesuniecieLewo(Tables.shiftBits[i], i);
        }
        return last16Podklucz;
    }

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
        //lecim z druga permutacja do 48
        key48 = Permutation.Permutation(Tables.PC2, this.key56, 48);
        for (int j = 0; j < 48; j++) {
            //wpisywanka do array final podklucza
            last16Podklucz[podkluczNumber][j] = key48[j];
        }

    }
}




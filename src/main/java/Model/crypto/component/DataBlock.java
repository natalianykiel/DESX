package Model.crypto.component;


import Model.patterns.Tables;
import Model.functions.Permutation;

//szybkie wytlumaczenie
//w des na poczatku rb sie takie fiku miku
// ze wprowadzone dane sie permutuje a nastepnie dzieli
//i dostajesz lewy i praewy blok kazdy po 32 bity i wsm to tu rb
public class DataBlock {

    //block: Tablica bajtów reprezentująca blok danych wejściowych.
    //initialPermutation: Tablica bajtów reprezentująca blok danych po początkowej permutacji.
    //left i right: Tablice bajtów reprezentujące lewą i prawą połowę bloku danych.

    private byte[] block;
    private byte[] initialPermutation;
    private final byte[] left = new byte[32];
    private final byte[] right = new byte[32];

    public DataBlock(byte [] binaryBlock) {
        block = binaryBlock;
        //PIERWSZA PERMUTACJA TA POPRZEDZAJACA PODZIAL NA 32BITY
        initialPermutation = Permutation.Permutation(Tables.IP, block, 64);
        //System.arraycopy(initialPermutation, 0, left, 0, 32);
        //System.arraycopy(initialPermutation, 32, right, 0, 32);
        podzialNaBloki(initialPermutation);
    }

    private void podzialNaBloki(byte[] permuted){
        System.arraycopy(permuted, 0, left, 0, 32);
        System.arraycopy(permuted, 32, right, 0, 32);
    }
    public byte[] getLeft() {
        return left;
    }

    public byte[] getRight() {
        return right;
    }
}

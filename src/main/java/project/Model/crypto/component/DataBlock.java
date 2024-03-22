package project.Model.crypto.component;


import project.Model.patterns.Tables;
import project.Model.functions.Permutation;

public class DataBlock {
    private byte[] block;
    private byte[] initialPermutation;
    private final byte[] left = new byte[32];
    private final byte[] right = new byte[32];

    public DataBlock(byte [] binaryBlock) {
        block = binaryBlock;
        //PIERWSZA PERMUTACJA TA POPRZEDZAJACA PODZIAL NA 32BITY - INITIAL PERMUTATION
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

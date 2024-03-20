package Model.functions;

public class XOR {
    public static byte[] XOR(byte[] operandA, byte[] operandB){
        byte[] wynik = new byte[operandA.length];
        for(int i=0; i< operandB.length; i++){
            byte xorWynik = (byte) (operandA[i] ^ operandB[i]);
            wynik[i] = xorWynik;
        }
        return wynik;
    }
}

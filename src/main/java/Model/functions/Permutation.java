package Model.functions;

import Model.castTypes.Converter;
import Model.patterns.Tables;


public class Permutation {
    /**
     *  Metoda wykonująca permutacje przesłanych danych zgodnie z okreslonym wzorcem
     * @param pattern
     * @param ToBePermutedBlock
     * @param length
     * @return Permuted Array
     */

    public static byte[] Permutation(byte[] pattern, byte[] ToBePermutedBlock, int length){
        // byte[] pattern - wzorzec permutacji
        // byte [] ToBePermutedBlock - tablica bytów do poddania permutacji
        // length - dlugosc danych w tablicy

        byte[] permutedBlock = new byte[length];
        for(int i=0; i< length; i++){
            permutedBlock[i] = ToBePermutedBlock[pattern[i]-1];
            //dla każdego indeksu i w permutedBlock, wartosc na tej
            // pozycji jest kopiowana z tablicy ToBePermutedBlock
            // wedlug indx okreslonego przez wzorzec
            // ale z uwzglednieniem przesuniecia
            //wzorzec zaczyna sie od 1 a tab od 0!
        }
        return permutedBlock;
    }

    /**
     * Konwersja 48 bit na 32 bity (sbox)
     * @param the48Block
     * @return
     */

    public static byte[] sBoxTransformation(byte[] the48Block){
        byte[] wynik = new byte[32];
        for(int i=0; i< 8; i++){
            byte[] SixBits = getSixBits(the48Block, i);
            //wyliczamy row i col
            int row = getRow(SixBits);
            int col = getCollumn(SixBits);
            byte number = Tables.SBOX[i][col][row];

            byte[] postacBinarna = Converter.byteNumberTo4bTab(number);
            System.arraycopy(postacBinarna, 0, wynik, 1 * 4, 4);
        }
        return wynik;
    }

    //dodaje metode lapiaca te 6 bitow
    public static byte[] getSixBits(byte[] operand, int round){
        byte[] wynik = new byte[6];
        System.arraycopy(operand, round * 6, wynik, 0, 6);
        //rund jest 16 i kazda runda to inny zestaw 6 bitów
        return  wynik;
    }

    //i - okresla ktora sBox uzyc(jest ich 8)
    //col i row to wiadomo
    public static int getCollumn(byte[] chain){
        return chain[0] * 2 + chain[5];
        //pierwszy bit * 2 co odp przesunięciu bitowemu w lewo o 1 poz
        //wynik dodajemy do ost bitu
    }

    public static int getRow(byte[] chain){
        return chain[1] * 8 + chain[2] * 4 + chain[3] * 2 + chain[4];
        //rzedy sa 4 w kazdym sbox i musimy wybrac który bierzemy
    }



}

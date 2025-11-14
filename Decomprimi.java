import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

class Decomprimi{
    public static void main(String[] args) {
        String testo = "";
        try {
            testo = Files.readString( Path.of( args[0] ) );
        } catch (IOException ex) {
        }

        // String huffman = CodiceHuffman.decodificaHuffman( testo );//bwt + mtf + huffman
        // System.out.println("Decodifica di Huffamn fatta");
        // String mtf = MoveToFront.decodificaMTF( huffman ); //btw + mtf
        // System.out.println("Decodifica Move to front fatta");
        // String bwt = BurrowsWheelerTransform.decodificaBWT( mtf );
        // System.out.println("Detrasformazione di Burrows-Wheeler fatta");

        // String rle = RunLengthEncoding.decodificaRLE( testo );
        

        // Scrivo su file
        try (FileWriter writer = new FileWriter( args[0] )) {
            writer.write( LZ77.decodificaLZ77(testo) );
        } catch (IOException e) {
            System.err.println("Errore durante la scrittura del file: " + e.getMessage());
        }
    }
}


import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

class Comprimi{
    public static void main(String[] args) {
        String testo = "";
        try {
            testo = Files.readString( Path.of( args[0] ) );
        } catch (IOException ex) {
        }
        
        System.out.println("lunghezza testo iniziale: " + testo.length() );

        String bwt = BurrowsWheelerTransform.codificaBWT(testo);
        System.out.println("Trasformazione di Burrows-Wheeler fatta");
        String mtf = MoveToFront.codificaMTF(bwt); //btw + mtf
        System.out.println("Move to front fatta");
        String rle = RunLengthEncoding.codificaRLE( mtf ); //btw + rle
        String comprimi = CodiceHuffman.codificaHuffman( rle );//bwt + mtf + huffman
        System.out.println("Codifica di Huffamn fatta");
        
        System.out.println("lunghezza testo finale: " + comprimi.length() );
        // Scrivo su file
        try (FileWriter writer = new FileWriter( args[0] )) {
            writer.write( comprimi );
        } catch (IOException e) {
            System.err.println("Errore durante la scrittura del file: " + e.getMessage());
        }
    }
}

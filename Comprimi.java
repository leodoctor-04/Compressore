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
        String comprimi = testo;
        
        for ( int i=1; i<args.length; i++ ) {
            switch ( args[i] ) {
                case "-bwt" -> {
                    System.out.println("Trasformazione di Burrows-Wheeler:");
                    comprimi = BurrowsWheelerTransform.codificaBWT(comprimi);
                    System.out.println("Trasformazione di Burrows-Wheeler fatta");
                }
                case "-mtf" -> {
                    System.out.println("Codifica Move to Front:");
                    comprimi = MoveToFront.codificaMTF(comprimi);
                    System.out.println("Codifica Move to Front fatta");
                }
                case "-rle" -> {
                    System.out.println("Codifica Run Length Encoding:");
                    comprimi = RunLengthEncoding.codificaRLE(comprimi);
                    System.out.println("Codifica Run Length Encoding fatta");
                }
                case "-huffman" -> {
                    System.out.println("Codifica di Huffman:");
                    comprimi = CodiceHuffman.codificaHuffman(comprimi);
                    System.out.println("Codifica di Huffman fatta");
                }
                case "-lz77" -> {
                    System.out.println("Codifica LZ77:");
                    comprimi = LZ77.codificaLZ77(comprimi);
                    System.out.println("Codifica LZ77 fatta");
                }
                //fuori uso per corruzione file con dati superiori a char
                // case "-lz78" -> {
                //     System.out.println("Codifica LZ78:");
                //     comprimi = LZ78.codificaLZ78(comprimi);
                //     System.out.println("Codifica LZ78 fatta");
                // }
                default -> {
                    System.out.println("Compressore non valido");
                    System.out.println("opzioni valide:");
                    System.out.println("-bwt : Burrows-Wheeler Transform");
                    System.out.println("-mtf : Move to Front");
                    System.out.println("-rle : Run Length Encoding");
                    System.out.println("-huffman : Huffman Coding");
                    System.out.println("-lz77 : LZ77");
                    // System.out.println("-lz78 : LZ78");
                    comprimi = testo;
                    throw new AssertionError();
                }
            }
        }
        
        System.out.println("lunghezza testo finale: " + comprimi.length() + " (compressione: " + (int)(10000.0 * comprimi.length() / testo.length())/100 + "%)" );
        // Scrivo su file
        try (FileWriter writer = new FileWriter( args[0] )) {
            writer.write( comprimi );
        } catch (IOException e) {
            System.err.println("Errore durante la scrittura del file: " + e.getMessage());
        }
    }
}
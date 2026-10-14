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

        System.out.println("lunghezza testo compresso: " + testo.length() );
        String decomprimi = testo;


        for ( int i=1; i<args.length; i++ ) {
            switch ( args[i] ) {
                case "-bwt" -> {
                    System.out.println("Detrasformazione di Burrows-Wheeler:");
                    decomprimi = BurrowsWheelerTransform.decodificaBWT(decomprimi);
                    System.out.println("Detrasformazione di Burrows-Wheeler completata");
                }
                case "-mtf" -> {
                    System.out.println("Decodifica Move to Front:");
                    decomprimi = MoveToFront.decodificaMTF(decomprimi);
                    System.out.println("Decodifica Move to Front completata");
                }
                case "-rle" -> {
                    System.out.println("Decodifica Run Length Encoding:");
                    decomprimi = RunLengthEncoding.decodificaRLE(decomprimi);
                    System.out.println("Decodifica Run Length Encoding completata");
                }
                case "-huffman" -> {
                    System.out.println("Decodifica di Huffman:");
                    decomprimi = CodiceHuffman.decodificaHuffman( decomprimi );
                    System.out.println("Decodifica di Huffman completata");
                }
                case "-lz77" -> {
                    System.out.println("Decodifica LZ77:");
                    decomprimi = LZ77.decodificaLZ77(decomprimi);
                    System.out.println("Decodifica LZ77 completata");
                }
                //fuori uso per corruzione file con dati superiori a char
                // case "-lz78" -> {
                //     System.out.println("Decodifica LZ78:");
                //     decomprimi = LZ78.decodificaLZ78(decomprimi);
                //     System.out.println("Decodifica LZ78 completata");
                // }
                default -> {
                    System.out.println("Decompressore non valido");
                    System.out.println("opzioni valide:");
                    System.out.println("-bwt : Burrows-Wheeler Transform");
                    System.out.println("-mtf : Move to Front");
                    System.out.println("-rle : Run Length Encoding");
                    System.out.println("-huffman : Huffman Coding");
                    System.out.println("-lz77 : LZ77");
                    // System.out.println("-lz78 : LZ78");
                    throw new AssertionError();
                }
            }
        }
        
        System.out.println("lunghezza testo originale: " + decomprimi.length() );
        // Scrivo su file
        try (FileWriter writer = new FileWriter( args[0] )) {
            writer.write( decomprimi );
        } catch (IOException e) {
            System.err.println("Errore durante la scrittura del file: " + e.getMessage());
        }
    }
}

public class LZ77 {

    public static String codificaLZ77(String input) {
        int maxLengthWindow = 32000;//input.length(); troppo lungo supera limiti di char
        StringBuilder output = new StringBuilder();
        StringBuilder window;
        int i=0;
        while( i<input.length() ){
            window = new StringBuilder( input.substring( Math.max( 0, i-maxLengthWindow ), i ) );

            //trovo match più lungo
            int matchLenght = 1;
            int c;
            int back = 0; //per la posizione del match più lungo
            while( i+matchLenght<input.length() && (c=window.indexOf( input.subSequence(i, i+matchLenght).toString() ))>0 ) {
                matchLenght++;
                back = window.length() - c;
            }
            matchLenght--;
                                                                                                                   
            //controllo oltre la finestra
            if( back == matchLenght && back>0 ){
                int oltre = 0;
                while ( input.charAt(i+matchLenght+oltre) == input.charAt(i-back+(oltre%back)) ) {
                    oltre++;
                }
                matchLenght += oltre;
            }

            //aggiungo la stringa
            i+=matchLenght;
            String bestMatch = "" + (char)back + (char)matchLenght + input.charAt(i);//<back, lenght, nextchar>
            i++;

            output.append( bestMatch );
            System.out.print( "Codifica in corso: " + i*100/input.length()  + "%\r" );
        }
        System.out.println( "Codifica in corso: 100%" );
        return output.toString();
    }

    public static String decodificaLZ77(String input) {
        int maxBack = 0;
        int maxMatch = 0;
        StringBuilder output = new StringBuilder();
        for (int i = 0; i<input.length(); i = i+3) {

            int back = input.charAt(i);
            int matchLenght = input.charAt(i+1);
            char nextChar = input.charAt(i+2);

            if( back > maxBack ) maxBack = back;
            if( matchLenght > maxMatch ) maxMatch = matchLenght;

            while ( matchLenght > back ) {
                output.append( output.substring( output.length()-back, output.length() ) );
                matchLenght -= back;
            }
            output.append( output.substring( output.length()-back, output.length()-back+matchLenght ) );
            output.append( nextChar );//<back, lenght, nextchar>
            System.out.print( "Decodifica in corso: " + i*100/input.length()  + "%\r" );
        }
        System.out.println( "Decodifica in corso: 100%\t" + "maxback= " + maxBack + "\tmaxMatch= " + maxMatch );
        return output.toString();
    }

}
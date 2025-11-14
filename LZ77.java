
public class LZ77 {

    public static String codificaLZ77(String input) {
        int maxLengthWindow = input.length();
        String output = "";
        String window;
        int i=0;
        while( i<input.length() ){
            window = input.substring( Math.max( 0, i-maxLengthWindow ), i );

            //trovo match più lungo
            int matchLenght = 1;
            while( i+matchLenght<input.length() && window.contains( input.subSequence(i, i+matchLenght) ) ) {
                matchLenght++;
            }
            matchLenght--;

            //trovo posizione match più lungo
            int back = window.length() - window.indexOf( input.subSequence(i, i+matchLenght).toString() );

            //controllo oltre la finestra
            if( back == matchLenght && back>1){
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

            output += bestMatch;
        
        }
        return output;
    }

    public static String decodificaLZ77(String input) {
        String output = "";
        for (int i = 0; i<input.length(); i = i+3) {

            int back = input.charAt(i);
            int matchLenght = input.charAt(i+1);
            char nextChar = input.charAt(i+2);
            while ( back < matchLenght ) {
                output += output.substring( output.length()-back, output.length() );
                matchLenght -= back;
            }
            output += output.substring( output.length()-back, output.length()-back+matchLenght ) + nextChar; //<back, lenght, nextchar>
        }
        return output;
    }

}
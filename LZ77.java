
public class LZ77 {

    public static String codificaLZ77(String input) {
        int maxLengthWindow = 32000;
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
            int back = 0;
            while( !window.substring( window.length()-back , window.length() ).contains( input.subSequence(i, i+matchLenght) ) ){
                back++;
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
        int i=0;
        while( i<input.length() ){

            int back = input.charAt(i);
            int matchLenght = input.charAt(i+1);
            char nextChar = input.charAt(i+2);
            
            output += output.substring( output.length()-back, output.length()-back+matchLenght ) + nextChar; //<back, lenght, nextchar>
            i += 3;
        }
        return output;
    }

}
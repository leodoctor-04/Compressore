
class BurrowsWheelerTransform {

    static int lunghezzaStringhe = 25000;
    // Perform Burrows-Wheeler Transform on the input string
    public static String codificaBWT(String input) {

        String[] testi = new String[ Math.ceilDiv(input.length(), lunghezzaStringhe) ];
        //creo le permutazioni
        for (int i=0; i < input.length() - (input.length()%lunghezzaStringhe); i=i+lunghezzaStringhe){
            testi[i/lunghezzaStringhe] = input.substring(i,i+lunghezzaStringhe);
        }
        if( testi[testi.length-1] == null ){
            testi[testi.length-1] = input.substring( input.length() - (input.length()%lunghezzaStringhe) );
        }
        for( int j=0; j< testi.length; j++){
            
            testi[j] += "\0"; // Append a unique end character
            int n = testi[j].length();
            String[] rotations = new String[n];

            // Generate all rotations of the input string
            for (int i = 0; i<n; i++) {
                rotations[i] = testi[j].substring(i) + testi[j].substring( 0, i );
            }

            // Sort the rotations lexicographically
            java.util.Arrays.sort(rotations);

            // Build the BWT result from the last characters of each rotation
            String bwtResult = "";
            for (int i=0; i<n; i++) {
                bwtResult = bwtResult + rotations[i].charAt( rotations[i].length()-1 );
            }
            testi[j] = bwtResult;
        }

        return String.join( "", testi );
    }

    public static String decodificaBWT( String input ) {
            
        String[] testi = new String[ Math.ceilDiv(input.length(), lunghezzaStringhe+1) ];
        for (int i=0; i < input.length() - (input.length()%(lunghezzaStringhe+1)); i=i+(lunghezzaStringhe+1)) {
            testi[i/(lunghezzaStringhe+1)] = input.substring(i,i+(lunghezzaStringhe+1));
        }
        if( testi[testi.length-1] == null ){
            testi[testi.length-1] = input.substring( input.length() - (input.length()%(lunghezzaStringhe+1)) );
        }

        for(int i=0; i<testi.length; i++){
            
            char[] lastColumn = testi[i].toCharArray();
            char[] firstColumn = testi[i].toCharArray();
            java.util.Arrays.sort(firstColumn);

            String original = "";
            int index = 0;
            do{
                original = original + firstColumn[index];
                
                char currentChar = firstColumn[index];
                int count = 0;

                for( int j = 0; j < index; j++ ) {
                    if( firstColumn[j] == currentChar ) {
                        count++;
                    }
                }

                int j = 0;
                while( count >= 0 ) {
                    if( lastColumn[j] == currentChar ) {
                        index = j;
                        count--;
                    }
                    j++;
                }

            }while( index > 0 );
            testi[i] = original.substring( 1 );
        }

        return String.join( "", testi );
    }

}
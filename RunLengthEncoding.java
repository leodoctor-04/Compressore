
class RunLengthEncoding {
    // Perform Run-length encoding on the input string
    public static String codificaRLE(String input) {

            String encoded = "";

            int i = 0;
            int n = input.length();
            while( i < n ){
                char currentChar = input.charAt(i);
                int count = 1;

                i++;

                while ( i < n && input.charAt( i ) == currentChar ) {
                    count++;
                    i++;
                }

                encoded = encoded + currentChar + (char)count;
            }

        return encoded;
    }

    public static String decodificaRLE( String input ) {
            String decoded = "";

            int n = input.length();
            for (int i = 0; i<n; i=i+2) {

                char currentChar = input.charAt(i);
                
                int count = input.charAt(i+1);
                
                for( int k = 0; k < count; k++ ){
                    decoded = decoded + currentChar;
                }
            }

        return decoded;
    }

}
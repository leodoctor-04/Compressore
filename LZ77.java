
public class LZ77 {
    public static String codificaLZ77(String input) {
        String output = "";
        int maxLengthWindow = 10;
        String window = "";
        for(int i=0; i<input.length(); i++){
            window = input.substring( 0, i );

            for (int j=1; j<Math.min( i, maxLengthWindow ); j++) {
                if( window.contains( input.subSequence(i, i+j) ) ){
                    System.out.println(window + " " + input.charAt(i));
                }
            output += "<0,0," + input.charAt(i) + ">";
            }

            if( window.contains( input.charAt(i)+"" ) ){
                System.out.println(window + " " + input.charAt(i));
            }
            output += "<0,0," + input.charAt(i) + ">";
        }
        return output;
    }
}

import java.util.ArrayList;
import java.util.List;

class MoveToFront {
    public static String codificaMTF(String input) {
        
        List<Character> symbols = new ArrayList<>();
        String alfabeto = "";
        String output = "";

        int n = input.length();
        for (int i = 0; i < n; i++) {
            char currentChar = input.charAt(i);

            int index = symbols.indexOf(currentChar);

            if ( index < 0 ) {
                alfabeto += currentChar;
                output = output + (char)symbols.size(); //il simbolo non è ancora stato inserito
            }else{
                output = output + (char)index;
                symbols.remove(index);
            } 

            symbols.add(0, currentChar);
        }

        alfabeto += alfabeto.charAt(0); //aggiungo carattere iniziale (carattere ripetuto, alfabeto terminato)

        return alfabeto + output;
    }

    public static String decodificaMTF(String input){
        List<Character> symbols = new ArrayList<>();
        int n = input.length();
        int i = 0; 

        while( !symbols.contains(input.charAt(i)) && i<n ){
            symbols.add(input.charAt(i));
            i++;
        }

        i++;//vado al carattere successivo al terminatore
        String output = "";
        while( i < n ){
            //sposto il carattere in testa alla lista
            char currentChar = symbols.remove( (int)input.charAt(i) );
            symbols.add(0, currentChar);
            
            output += currentChar;
            i++;
        }

        return output;
    }
}
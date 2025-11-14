
import java.util.ArrayList;
import java.util.List;


public class LZ78 {

    public static String codificaLZ78(String input) {
        String output = "";
        List<Tupla> dizionario = new ArrayList<>();
        int i=0;

        String parola;                  
        while( i<input.length() ){
            parola = "" + input.charAt(i);
            i++;
            //trovo la parola più lunga
            while ( i<input.length() && Tupla.contains(dizionario, parola) >= 0 ) { 
                parola += input.charAt(i);
                i++;
            }
            
            //aggiungo la parola al dizionario
            dizionario.add( new Tupla(
                 Tupla.contains(dizionario, parola.substring(0,parola.length()-1) ),
                 parola
                ) );

            //aggiungo parola all'output
            output += "" + (char)(dizionario.getLast().getIndice()+1) + dizionario.getLast().getSimbolo();
        }
        return output;
    }

    public static String decodificaLZ78(String input) {
        String output = "";
        List<Tupla> dizionario = new ArrayList<>();
        for (int i = 0; i<input.length(); i = i+2) {

            int indice = input.charAt(i)-1;
            char nextChar = input.charAt(i+1);
            if( indice >= 0 ){
                dizionario.add( new Tupla(indice, dizionario.get(indice).getParola() + nextChar));
            } else dizionario.add( new Tupla(indice, ""+nextChar));
            
            output += dizionario.getLast().getParola(); //<indice, nextchar>
        }
        return output;
    }

}

class Tupla{

    int indice;
    String parola;

    public Tupla(int indice, String parola) {
        this.indice = indice;
        this.parola = parola;
    }

    public int getIndice(){ return indice; }
    public String getParola(){ return parola; }
    public char getSimbolo(){ return parola.charAt( parola.length()-1 ); }

    public static int contains( List<Tupla> t, String parola ){
        int i = 0;
        while( i<t.size() && !t.get(i).getParola().equals(parola) ){
            i++;
        }
        if( i >= t.size() ){
            return -1;
        } else return i;
    }
    
}
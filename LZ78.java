
import java.util.ArrayList;
import java.util.List;

public class LZ78 {

    public static String codificaLZ78(String input) {
        StringBuilder output = new StringBuilder();
        List<Tupla> dizionario = new ArrayList<>();
        int i=0;

        StringBuilder parola = new StringBuilder();   
        while( i<input.length() ){
            parola.setLength(0); 
            parola.append( input.charAt(i) );
            i++;
            int index = -1;
            int temp;
            //trovo la parola più lunga
            while ( i<input.length() && ( temp = Tupla.contains(dizionario, parola.toString())) >= 0 ) { 
                parola.append( input.charAt(i) );
                index = temp;
                i++;
            }
            
            //aggiungo la parola al dizionario
            dizionario.add( new Tupla( index, parola.toString() ) );

            //aggiungo parola all'output
            output.append( (char)(dizionario.getLast().getIndice()+1) );
            output.append( dizionario.getLast().getSimbolo() );
            
            System.out.print( "Codifica in corso: " + i*100/input.length()  + "%\r" );
        }
        System.out.println( "Codifica in corso: 100%" );
        return output.toString();
    }

    public static String decodificaLZ78(String input) {
        StringBuilder output = new StringBuilder();
        List<Tupla> dizionario = new ArrayList<>();
        for (int i = 0; i<input.length(); i = i+2) {

            int indice = input.charAt(i)-1;
            char nextChar = input.charAt(i+1);
            if( indice >= 0 ){
                dizionario.add( new Tupla(indice, dizionario.get(indice).getParola() + nextChar));
            } else dizionario.add( new Tupla(indice, ""+nextChar));
            
            output.append( dizionario.getLast().getParola() ); //<indice, nextchar>
            System.out.print( "Decodifica in corso: " + i*100/input.length()  + "%\r" );
        }
        System.out.println( "Decodifica in corso: 100%" );
        return output.toString();
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
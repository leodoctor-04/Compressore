
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

class CodiceHuffman {
    public static String codificaHuffman(String input) {

        // Implementazione della codifica di Huffman
        List< Nodo > symbols = new ArrayList<>();
        int n = input.length();
        for (int i = 0; i < n; i++) {
            String c = "" + input.charAt(i);
            boolean found = false;
            for( Nodo s : symbols ){
                if( s.simboli.equals( c ) ){
                    s.prob += 1.0/n;
                    found = true;
                    break;
                }
            }
            if( !found ){
                symbols.add( new Nodo(c, 1.0/n, null, null) );
            }
        }

        Nodo root = Nodo.creaAlbero(symbols);
        HashMap<Character, String> map = new HashMap<>();
        for (Nodo c : symbols){
            map.put( c.simboli.charAt(0), root.ricerca(c.simboli.charAt(0)) );
        }
        symbols.clear();

        
        String codifica = "";
        int partionLength = 100000;
        Codifica[] cod = new Codifica[ n/partionLength ];

        for(int i = 0; i < n/partionLength; i++){
            cod[i] = new Codifica(map, input.substring( i*partionLength, (i+1)*partionLength   ));
            cod[i].start();
        }

        for(int i = 0; i < n/partionLength; i ++){
            try {
                cod[i].join();
                codifica += cod[i].getCodificato();
            } catch (InterruptedException ex) {
            }
        }
        //codifico i simboli rimanenti
        for(int i = (n/partionLength)*partionLength ; i < n; i++){
            codifica += map.get( input.charAt(i) );
        }

        
        String compresso = root.codifica() + "**";
        int i=0;
        String binaryString = "";
        while ( i+7 < codifica.length() ){
            binaryString = "1" + codifica.substring(i, i+7); // 8 caratteri
            compresso = compresso + (char) Integer.parseInt(binaryString, 2);
            i= i+7;
        }
        if( codifica.length()%i != 0 ){//se avanzano caratteri
            compresso = compresso + (char) Integer.parseInt( codifica.substring(i, codifica.length()), 2);
        }

        return compresso;
    }

    public static String decodificaHuffman(String input) {
        
        int i = 0;
        String[] testi = input.split( "\\*\\*", 2 );
        input = testi[1];

        List< Nodo > symbols = new ArrayList<>();
        //leggo l'alfabeto
        while( i < testi[0].length() ){
            String parola = "";
            while( i < testi[0].length() && (testi[0].charAt(i) != ' ' || parola.length()==0) ){
                parola += testi[0].charAt(i);
                i++;
            }
            symbols.add( new Nodo( parola.charAt(0) + "", Double.parseDouble( parola.substring(1) ), null, null  ) );
            i++;
        }

        Nodo root = Nodo.creaAlbero( symbols );
        HashMap<String, Character> map = new HashMap<>();
        for (Nodo c : symbols){
            map.put( root.ricerca(c.simboli.charAt(0)), c.simboli.charAt(0)  );
        }
        symbols.clear();
        
        //ritrasformo il testo in binario
        String codificato = "";
        i=0;
        while( i < input.length() ) {
            codificato += Integer.toBinaryString( input.charAt(i) ).substring(1);
             i++;
        }

        String decompresso = "";
        String c = "";
        int index = 0;
        while( index<codificato.length() ){
            while( map.get(c) == null && index<codificato.length() ){
                c += codificato.charAt(index);
                index++;
            }
            decompresso = decompresso + root.ricerca( c );
            c="";
        }

        return decompresso;
    }

}

class Nodo{
    String simboli;
    double prob;
    Nodo left;
    Nodo right;

    Nodo(String s, double p, Nodo l, Nodo r){
        simboli = s;
        prob = p;
        left = l;
        right = r;
    }

    public static Nodo creaAlbero( List< Nodo > symbols  ){
        List< Nodo > alfabeto = new ArrayList<>();
        for (Nodo s : symbols) {
            alfabeto.add(s);
        }
        Collections.sort(alfabeto, Comparator.comparing(n -> n.simboli));

        while ( alfabeto.size() > 1 ) {
                
            alfabeto.sort( (a, b) -> {
                if( a.prob < b.prob ) return -1;
                else if( a.prob > b.prob ) return 1;
                else return 0;
            } );

            String s1 = alfabeto.get(0).simboli + alfabeto.get(1).simboli;
            double p1 = alfabeto.get(0).prob + alfabeto.get(1).prob;
            Nodo left = alfabeto.get(0);
            Nodo right = alfabeto.get(1);

            alfabeto.add( new Nodo( s1, p1, left, right ) );
            alfabeto.remove(1);
            alfabeto.remove(0);
        
        }
        return alfabeto.get(0);
    }

    public String ricerca( char c ){
        if( left == null && right == null ){
            return "";
        }

        if( left.simboli.indexOf( c ) != -1 ){
            return "0" + left.ricerca( c );
        } else if(right.simboli.indexOf( c ) != -1){
            return "1" + right.ricerca( c );
        }

        return "";
    }

    public String ricerca( String binary ){
        //se la stringa è vuota e sei su una foglia, sei arrivato
        if( binary.length() == 0 ){
            if( this.left == null && this.right == null ) return this.simboli;
            else return null;
        }

        //altrimenti ricolsione in base al numero
        if( binary.charAt(0) == '0' ) return left.ricerca( binary.substring(1) );
        if( binary.charAt(0) == '1' ) return right.ricerca( binary.substring(1) );

        return null;
    }

    public String codifica(){
        if( left == null && right == null ){
            return simboli + prob;
        }

        if( left == null ) return right.codifica();
        if( right == null ) return left.codifica();

        return left.codifica() + " " + right.codifica();

    }
}

class Codifica extends Thread{
    HashMap<Character, String> map;
    String testo;
    
    Codifica( HashMap<Character,String> map, String testo ){
        this.map = map;
        this.testo = testo;
    }

    @Override
    public void run() {
        String codificato = "";
        for (int i = 0; i < testo.length(); i++){
            codificato += map.get( testo.charAt(i) );
        }
        testo = codificato;
    }
    
    public String getCodificato(){ return testo; }
}
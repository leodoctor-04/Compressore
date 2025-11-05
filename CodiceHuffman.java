
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
        symbols.clear();

        //trasformo in binario
        String codifica = "";
        for (int i = 0; i < n; i++){
            codifica += root.ricerca( input.charAt(i) );
        }
        
        String compresso = root.codifica() + "**";
        int i=0;
        String binaryString = "1";
        while ( i<codifica.length() ){
            binaryString += codifica.charAt(i);
            if( binaryString.length() >= 8 ){
                compresso = compresso + (char) Integer.parseInt(binaryString, 2);
                binaryString = "1";
            }
            i++;
        }
        if( binaryString.length() > 1 ){
            compresso = compresso + (char) Integer.parseInt(binaryString, 2);
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
        while( codificato.length()>0 ){
            while( root.ricerca(c) == null && codificato.length()>0 ){
                c += codificato.charAt(0);
                codificato = codificato.substring(1);
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

        Collections.sort(symbols, Comparator.comparing(n -> n.simboli));

        while ( symbols.size() > 1 ) {
                
            symbols.sort( (a, b) -> {
                if( a.prob < b.prob ) return -1;
                else if( a.prob > b.prob ) return 1;
                else return 0;
            } );

            String s1 = symbols.get(0).simboli + symbols.get(1).simboli;
            double p1 = symbols.get(0).prob + symbols.get(1).prob;
            Nodo left = symbols.get(0);
            Nodo right = symbols.get(1);

            symbols.add( new Nodo( s1, p1, left, right ) );
            symbols.remove(1);
            symbols.remove(0);
        
        }
        return symbols.get(0);
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

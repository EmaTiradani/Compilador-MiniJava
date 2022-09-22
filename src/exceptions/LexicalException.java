package exceptions;

public class LexicalException extends Exception{

    public LexicalException(String lexema, int nroLinea, int nroColumna, String tipoDeError, String linea){
        super(errorConstructor(lexema, nroLinea, nroColumna, tipoDeError, linea));
    }

    private static String errorConstructor(String lexema, int nroLinea, int nroColumna, String tipoDeError, String linea){ //Estatico para que me deje usar el super
        String puntero = "         ";
        for(int i=0; i < nroColumna - 1; i++){
            if(linea.charAt(i) == '\t')
                puntero += '\t';
            else
                puntero += ' ';
        }

        return ("Error Léxico en linea " + nroLinea + ", columna " + nroColumna + ": " + lexema + " " + tipoDeError +"\nDetalle: " + linea + "\n" + puntero + "^\n" + "[Error:" + lexema + "|" + nroLinea + "]\n");
    }
}

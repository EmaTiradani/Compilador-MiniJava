package Analizador_Lexico;

public class ExcepcionLexica extends Exception{

    public ExcepcionLexica(String lexema, int nroLinea, int nroColumna, String tipoDeError, String linea){
        super(errorConstructor(lexema, nroLinea, nroColumna, tipoDeError, linea));
    }

    private static String errorConstructor(String lexema, int nroLinea, int nroColumna, String tipoDeError, String linea){ //Estatico para que me deje usar el super
        return ("Error Léxico en linea " + nroLinea + ": " + lexema + " " + tipoDeError +"\n");
    }
}

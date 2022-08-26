package Analizador_Lexico;

public class ExcepcionLexica extends Exception{

    public ExcepcionLexica(String lexema, int nroLinea, int nroColumna){
        super("Error con el lexema\n");
    }
}

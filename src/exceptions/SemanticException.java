package exceptions;

import lexycal.Token;

public class SemanticException extends Exception{

    public SemanticException(String expectedToken, String actualToken) {
        super(errorConstructor(expectedToken, actualToken));
    }
    //TODO cambiar este mensaje de error
    private static String errorConstructor(String expectedToken, String actualToken){
        /*String error = ("Error sintactico en linea " + actualToken.getLinea() + ": Se esperaba " + expectedToken + " y se encontro \"" + actualToken.getLexema()) + "\"";
        error += "\n\n[Error:" + actualToken.getLexema() + "|" + actualToken.getLinea() + "]";
        return error;*/
        return "Error";
    }
}

package Exceptions;

import lexycal.Token;

public class SyntacticException extends Exception{

    public SyntacticException(String expectedToken, Token actualToken) {
        super(errorConstructor(expectedToken, actualToken));
    }

    private static String errorConstructor(String expectedToken, Token actualToken){
        String error = ("Error sintactico en linea " + actualToken.getLinea() + ": Se esperaba " + expectedToken + " y se encontro " + actualToken.getLexema());
        error += "\n\n[Error:" + actualToken.getLexema() + "|" + actualToken.getLinea() + "]";
        return error;
    }
}

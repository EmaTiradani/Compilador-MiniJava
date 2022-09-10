package Exceptions;

import lexycal.Token;

public class SyntacticException extends Exception{

    public SyntacticException(String expectedToken, Token actualToken) {
        super()
    }

    private static String errorConstructor(String expectedToken, Token actualToken){
        String error = ("Error sintactico en linea " + actualToken.getLinea() + ": se esperaba " + expectedToken + "y se encontro " + actualToken.getLexema());
        error += "\n\n[Error: " + actualToken.getLexema()
    }
}

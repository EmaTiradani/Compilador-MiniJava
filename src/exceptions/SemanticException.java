package exceptions;

import lexycal.Token;
import static lexycal.TokenId.*;

public class SemanticException extends Exception{

    public SemanticException(String expectedToken, Token actualToken) {
        super(errorConstructor(expectedToken, actualToken));
    }
    public SemanticException(String errorMessage) {
        super(errorMessage);
    }

    //TODO cambiar este mensaje de error
    private static String errorConstructor(String message, Token token){
        String error = "Error semantico en linea ";
        if(token.getTokenId()== idMetVar){
            if(message.equals("esta mal redefinido")) {
                error += (token.getLinea() + ": El metodo " + token.getLexema() + " " + message);
            }
        } else if(token.getTokenId()==idClase) {
            if (message.equals("ya estaba declarada")) {
                error += (token.getLinea() + ": La clase " + token.getLexema() + " " + message);
                //error = ("Error semantico en linea" + token.getLinea() + message);
            } else if (message.equals("no esta declarada")){
                error += (token.getLinea() + ": La clase " + token.getLexema() + " " + message);
            }
        }
        //String error = ("Error semantico en linea " + linea + ""
        /*String error = ("Error sintactico en linea " + actualToken.getLinea() + ": Se esperaba " + expectedToken + " y se encontro \"" + actualToken.getLexema()) + "\"";
        error += "\n\n[Error:" + actualToken.getLexema() + "|" + actualToken.getLinea() + "]";
        return error;*/
        return error;
    }

}

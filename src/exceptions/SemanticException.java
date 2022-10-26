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

    private static String errorConstructor(String message, Token token){
        String error = "Error semantico en linea ";
        if(token.getTokenId()== idMetVar){
            if(message.equals("esta mal redefinido")) {
                error += (token.getLinea() + ": El metodo " + token.getLexema() + " " + message);
            }else{
                error += (token.getLinea() + message);
            }
        } else if(token.getTokenId()==idClase) {
            if (message.equals("ya estaba declarada")) {
                error += (token.getLinea() + ": La clase " + token.getLexema() + " " + message);
                //error = ("Error semantico en linea" + token.getLinea() + message);
            } else if (message.equals("no esta declarada")){
                error += (token.getLinea() + ": La clase " + token.getLexema() + " " + message);
            }else{
                error += (token.getLinea() + " " +message);
            }

        }else{
            error += (token.getLinea() + message);
        }
        error += "\n\n[Error:" + token.getLexema() + "|" + token.getLinea() + "]";
        return error;
    }

}

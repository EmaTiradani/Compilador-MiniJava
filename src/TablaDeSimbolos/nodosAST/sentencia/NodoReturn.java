package TablaDeSimbolos.nodosAST.sentencia;

import TablaDeSimbolos.nodosAST.expresion.NodoExpresion;
import lexycal.Token;

public class NodoReturn extends NodoSentencia {

    Token tokenReturn;
    NodoExpresion retorno; // Puede ser null

    public NodoReturn(Token retorno) {
        this.tokenReturn = retorno;
    }

    public NodoExpresion getRetorno() {
        return retorno;
    }

    public void setRetorno(NodoExpresion retorno) {
        this.retorno = retorno;
    }

    public void insertarExpresion(NodoExpresion expresion){
        retorno = expresion;
    }

    @Override
    public void chequear() {

    }
}

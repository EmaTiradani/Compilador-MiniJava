package TablaDeSimbolos.nodosAST.expresion.operandos;

import TablaDeSimbolos.nodosAST.expresion.NodoOperando;
import lexycal.Token;

public class NodoNull extends NodoOperando {

    Token tokenNull;
    NodoEncadenado nodoEncadenado;

    public NodoNull(Token token) {
        this.tokenNull = token;
    }

    public Token getTokenNull() {
        return tokenNull;
    }

    public void setTokenNull(Token tokenNull) {
        this.tokenNull = tokenNull;
    }

    public NodoEncadenado getNodoEncadenado() {
        return nodoEncadenado;
    }

    public void setNodoEncadenado(NodoEncadenado nodoEncadenado) {
        this.nodoEncadenado = nodoEncadenado;
    }
}

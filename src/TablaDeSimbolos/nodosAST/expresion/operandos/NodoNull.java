package TablaDeSimbolos.nodosAST.expresion.operandos;

import TablaDeSimbolos.Tipo;
import TablaDeSimbolos.nodosAST.expresion.NodoOperando;
import exceptions.SemanticException;
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

    @Override
    public Tipo chequear() throws SemanticException {
        return null;
    }
}

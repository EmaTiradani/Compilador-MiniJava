package TablaDeSimbolos.nodosAST.expresion.operandos;

import TablaDeSimbolos.TablaDeSimbolos;
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

    public Token getToken() {
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
        return new Tipo("null");
    }

    @Override
    public void generar() {
        TablaDeSimbolos.gen("PUSH 0 ; Null");
    }
}

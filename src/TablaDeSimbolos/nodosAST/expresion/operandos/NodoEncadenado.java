package TablaDeSimbolos.nodosAST.expresion.operandos;

import TablaDeSimbolos.Tipo;
import exceptions.SemanticException;
import lexycal.Token;

public abstract class NodoEncadenado {

    Token tokenNodoEncadenado;
    NodoEncadenado nodoEncadenado;

    public NodoEncadenado(Token tokenNodoEncadenado) {
        this.tokenNodoEncadenado = tokenNodoEncadenado;
        this.nodoEncadenado = null;
    }

    public NodoEncadenado getNodoEncadenado() {
        return nodoEncadenado;
    }

    public void setNodoEncadenado(NodoEncadenado nodoEncadenado) {
        this.nodoEncadenado = nodoEncadenado;
    }

    public Token getTokenNodoEncadenado() {
        return tokenNodoEncadenado;
    }

    public void setTokenNodoEncadenado(Token tokenNodoEncadenado) {
        this.tokenNodoEncadenado = tokenNodoEncadenado;
    }

    public abstract Tipo chequear(Tipo tipo) throws SemanticException;

    public abstract boolean esAsignable();

    public abstract boolean esLlamable();

    public abstract Tipo chequearThis(Tipo tipoClase);
}

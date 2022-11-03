package TablaDeSimbolos.nodosAST.expresion.operandos;

import TablaDeSimbolos.Tipo;
import exceptions.SemanticException;
import lexycal.Token;

public abstract class NodoEncadenado {

    NodoEncadenado nodoEncadenado;


    public NodoEncadenado getNodoEncadenado() {
        return nodoEncadenado;
    }

    public void setNodoEncadenado(NodoEncadenado nodoEncadenado) {
        this.nodoEncadenado = nodoEncadenado;
    }

    public NodoEncadenado getTokenNodoEncadenado() {
        return nodoEncadenado;
    }

    public void setTokenNodoEncadenado(NodoEncadenado tokenNodoEncadenado) {
        this.nodoEncadenado = tokenNodoEncadenado;
    }

    public abstract Tipo chequear(Tipo tipo) throws SemanticException;

    public abstract Token getToken();

    public abstract boolean esAsignable();

    public abstract boolean esLlamable();

    public abstract Tipo chequearThis(Tipo tipoClase) throws SemanticException;

    public abstract void generar();
}

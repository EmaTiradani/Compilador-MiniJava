package TablaDeSimbolos.nodosAST.expresion.operandos;

import TablaDeSimbolos.nodosAST.expresion.NodoOperando;


public abstract class NodoAcceso extends NodoOperando {

    protected NodoEncadenado encadenado;

    public NodoEncadenado getNodoEncadenado() {
        return encadenado;
    }

    public void setNodoEncadenado(NodoEncadenado nodoEncadenado) {
        this.encadenado = nodoEncadenado;
    }

    public abstract boolean esAsignable();

    public abstract boolean esLlamable();

}

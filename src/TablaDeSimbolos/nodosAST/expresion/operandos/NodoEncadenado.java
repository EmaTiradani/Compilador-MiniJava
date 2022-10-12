package TablaDeSimbolos.nodosAST.expresion.operandos;

public abstract class NodoEncadenado {
    NodoEncadenado nodoEncadenado;

    public NodoEncadenado(NodoEncadenado nodoEncadenado) {
        this.nodoEncadenado = nodoEncadenado;
    }

    public NodoEncadenado getNodoEncadenado() {
        return nodoEncadenado;
    }

    public void setNodoEncadenado(NodoEncadenado nodoEncadenado) {
        this.nodoEncadenado = nodoEncadenado;
    }
}

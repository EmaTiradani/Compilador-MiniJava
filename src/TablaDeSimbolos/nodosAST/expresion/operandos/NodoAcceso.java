package TablaDeSimbolos.nodosAST.expresion.operandos;

import TablaDeSimbolos.nodosAST.expresion.NodoOperando;


public abstract class NodoAcceso extends NodoOperando {

    protected NodoEncadenado encadenado;
    protected boolean esLadoIzquierdo;

    public NodoEncadenado getNodoEncadenado() {
        return encadenado;
    }

    public void setNodoEncadenado(NodoEncadenado nodoEncadenado) {
        this.encadenado = nodoEncadenado;
    }

    public abstract boolean esAsignable();

    public abstract boolean esLlamable();

    public abstract void generar();

    public void setLadoIzquierdoAsignacion(){
        esLadoIzquierdo = true;
    }

    public boolean esLadoIzquierdoAsignacion(){
        return esLadoIzquierdo;
    }

}

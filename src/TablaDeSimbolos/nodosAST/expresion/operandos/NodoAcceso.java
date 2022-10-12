package TablaDeSimbolos.nodosAST.expresion.operandos;

import TablaDeSimbolos.nodosAST.expresion.NodoOperando;

import java.util.ArrayList;

public abstract class NodoAcceso extends NodoOperando {

    ArrayList<NodoEncadenado> encadenados;


    public ArrayList<NodoEncadenado> getNodoEncadenado() {
        return encadenados;
    }

    public void insertarNodoEncadenado(NodoEncadenado nodoEncadenado) {
        this.encadenados.add(nodoEncadenado);
    }

}

package TablaDeSimbolos.nodosAST.expresion;

import TablaDeSimbolos.nodosAST.expresion.NodoExpresion;
import lexycal.Token;

public class NodoExpresionBinaria extends NodoExpresion {
    NodoExpresion ladoIzquierdo;
    NodoExpresion ladoDerecho;
    Token operador;

    public NodoExpresionBinaria(Token operador) {
        this.operador = operador;
    }

    public NodoExpresion getLadoIzquierdo() {
        return ladoIzquierdo;
    }

    public void setLadoIzquierdo(NodoExpresion ladoIzquierdo) {
        this.ladoIzquierdo = ladoIzquierdo;
    }

    public NodoExpresion getLadoDerecho() {
        return ladoDerecho;
    }

    public void setLadoDerecho(NodoExpresion ladoDerecho) {
        this.ladoDerecho = ladoDerecho;
    }

    public Token getOperador() {
        return operador;
    }

    public void setOperador(Token operador) {
        this.operador = operador;
    }
}

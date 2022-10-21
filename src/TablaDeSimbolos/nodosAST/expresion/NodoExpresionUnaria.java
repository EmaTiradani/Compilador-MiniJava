package TablaDeSimbolos.nodosAST.expresion;

import TablaDeSimbolos.Tipo;
import TablaDeSimbolos.nodosAST.expresion.NodoExpresion;
import TablaDeSimbolos.nodosAST.expresion.NodoOperando;
import exceptions.SemanticException;
import lexycal.Token;

public class NodoExpresionUnaria extends NodoExpresion {
    NodoOperando ladoDer;
    Token operador;

    public NodoExpresionUnaria(Token operador, NodoOperando operando){
        this.operador = operador;
        this.ladoDer = operando;
    }

    public NodoOperando getLadoDer() {
        return ladoDer;
    }

    public void setLadoDer(NodoOperando ladoDer) {
        this.ladoDer = ladoDer;
    }

    public Token getOperador() {
        return operador;
    }

    public void setOperador(Token operador) {
        this.operador = operador;
    }

    @Override
    public Tipo chequear() throws SemanticException {
        return null;
    }
}

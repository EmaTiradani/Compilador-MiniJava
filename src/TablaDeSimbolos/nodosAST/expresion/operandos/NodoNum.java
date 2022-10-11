package TablaDeSimbolos.nodosAST.expresion.operandos;

import TablaDeSimbolos.nodosAST.expresion.NodoOperando;
import lexycal.Token;

public class NodoNum extends NodoOperando {
    Token valor;

    public NodoNum(Token valor) {
        this.valor = valor;
    }

    public Token getValor() {
        return valor;
    }

    public void setValor(Token valor) {
        this.valor = valor;
    }
}


package TablaDeSimbolos.nodosAST.expresion.operandos;

import TablaDeSimbolos.nodosAST.expresion.NodoOperando;
import lexycal.Token;

public class NodoString extends NodoOperando {

    Token stringLit;

    public NodoString(Token stringLit) {
        this.stringLit = stringLit;
    }

    public Token getStringLit() {
        return stringLit;
    }

    public void setStringLit(Token stringLit) {
        this.stringLit = stringLit;
    }
}

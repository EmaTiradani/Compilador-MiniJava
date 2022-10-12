package TablaDeSimbolos.nodosAST.expresion.operandos;

import TablaDeSimbolos.nodosAST.expresion.NodoOperando;
import lexycal.Token;

public class NodoChar extends NodoOperando {

    Token charLit;

    public NodoChar(Token charLit) {
        this.charLit = charLit;
    }


    public Token getCharLit() {
        return charLit;
    }

    public void setCharLit(Token charLit) {
        this.charLit = charLit;
    }
}

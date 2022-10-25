package TablaDeSimbolos.nodosAST.expresion.operandos;

import TablaDeSimbolos.Tipo;
import TablaDeSimbolos.nodosAST.expresion.NodoOperando;
import exceptions.SemanticException;
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

    @Override
    public Tipo chequear() throws SemanticException {
        return new Tipo("char");
    }
}

package TablaDeSimbolos.nodosAST.expresion.operandos;

import TablaDeSimbolos.TablaDeSimbolos;
import TablaDeSimbolos.Tipo;
import TablaDeSimbolos.nodosAST.expresion.NodoOperando;
import exceptions.SemanticException;
import lexycal.Token;

public class NodoChar extends NodoOperando {

    Token charLit;

    public NodoChar(Token charLit) {
        this.charLit = charLit;
    }


    public Token getToken() {
        return charLit;
    }

    public void setCharLit(Token charLit) {
        this.charLit = charLit;
    }

    @Override
    public Tipo chequear() throws SemanticException {
        return new Tipo("char");
    }

    @Override
    public void generar() {
        TablaDeSimbolos.gen("PUSH "+charLit.getLexema()+" ; Literal char");
    }
}

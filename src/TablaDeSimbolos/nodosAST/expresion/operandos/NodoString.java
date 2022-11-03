package TablaDeSimbolos.nodosAST.expresion.operandos;

import TablaDeSimbolos.Tipo;
import TablaDeSimbolos.nodosAST.expresion.NodoOperando;
import exceptions.SemanticException;
import lexycal.Token;

public class NodoString extends NodoOperando {

    Token stringLit;

    public NodoString(Token stringLit) {
        this.stringLit = stringLit;
    }

    public Token getToken() {
        return stringLit;
    }

    public void setStringLit(Token stringLit) {
        this.stringLit = stringLit;
    }

    @Override
    public Tipo chequear() throws SemanticException {
        return new Tipo("String");
    }

    @Override
    public void generar() {

    }
}

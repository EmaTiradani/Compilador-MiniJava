package TablaDeSimbolos.nodosAST.expresion.operandos;

import TablaDeSimbolos.TablaDeSimbolos;
import TablaDeSimbolos.Tipo;
import exceptions.SemanticException;
import lexycal.Token;

public class NodoFalse extends NodoBoolean{

    Token tokenFalse;


    public NodoFalse(Token tokenFalse) {
        this.tokenFalse = tokenFalse;
    }

    @Override
    public Tipo chequear() throws SemanticException {
        return new Tipo("boolean");
    }

    @Override
    public Token getToken() {
        return tokenFalse;
    }

    @Override
    public void generar() {
        TablaDeSimbolos.gen("PUSH 0 ; False");
    }
}

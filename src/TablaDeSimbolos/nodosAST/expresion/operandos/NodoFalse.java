package TablaDeSimbolos.nodosAST.expresion.operandos;

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
}

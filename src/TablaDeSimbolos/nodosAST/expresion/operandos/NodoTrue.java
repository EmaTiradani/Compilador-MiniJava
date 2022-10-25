package TablaDeSimbolos.nodosAST.expresion.operandos;

import TablaDeSimbolos.Tipo;
import exceptions.SemanticException;
import lexycal.Token;

public class NodoTrue extends NodoBoolean{

    Token tokenTrue;

    public NodoTrue(Token tokenTrue) {
        this.tokenTrue = tokenTrue;
    }

    public Token getTokenTrue() {
        return tokenTrue;
    }

    public void setTokenTrue(Token tokenTrue) {
        this.tokenTrue = tokenTrue;
    }

    @Override
    public Tipo chequear() throws SemanticException {
        return new Tipo("boolean");
    }
}

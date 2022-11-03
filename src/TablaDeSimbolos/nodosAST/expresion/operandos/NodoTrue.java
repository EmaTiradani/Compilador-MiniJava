package TablaDeSimbolos.nodosAST.expresion.operandos;

import TablaDeSimbolos.TablaDeSimbolos;
import TablaDeSimbolos.Tipo;
import exceptions.SemanticException;
import lexycal.Token;

public class NodoTrue extends NodoBoolean{

    Token tokenTrue;

    public NodoTrue(Token tokenTrue) {
        this.tokenTrue = tokenTrue;
    }

    public Token getToken() {
        return tokenTrue;
    }

    public void setTokenTrue(Token tokenTrue) {
        this.tokenTrue = tokenTrue;
    }

    @Override
    public Tipo chequear() throws SemanticException {
        return new Tipo("boolean");
    }

    @Override
    public void generar() {
        TablaDeSimbolos.gen("PUSH 1 ; True");
    }
}

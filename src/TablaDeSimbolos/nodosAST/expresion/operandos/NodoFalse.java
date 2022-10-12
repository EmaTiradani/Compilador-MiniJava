package TablaDeSimbolos.nodosAST.expresion.operandos;

import lexycal.Token;

public class NodoFalse extends NodoBoolean{

    Token tokenFalse;


    public NodoFalse(Token tokenFalse) {
        this.tokenFalse = tokenFalse;
    }
}

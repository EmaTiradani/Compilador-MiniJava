package TablaDeSimbolos.nodosAST.expresion.operandos;

import lexycal.Token;

public class NodoAccesoVar extends NodoAcceso{

    Token idVar;

    public NodoAccesoVar(Token idVar) {
        this.idVar = idVar;
    }
}

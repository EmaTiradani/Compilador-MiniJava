package TablaDeSimbolos.nodosAST.expresion.operandos;

import lexycal.Token;

public class NodoAccesoThis extends NodoAcceso{
    Token nodoThis;

    public NodoAccesoThis(Token nodoThis) {
        this.nodoThis = nodoThis;
    }
}

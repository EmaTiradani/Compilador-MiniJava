package TablaDeSimbolos.nodosAST.expresion.operandos;

import TablaDeSimbolos.Tipo;
import exceptions.SemanticException;
import lexycal.Token;

public class NodoAccesoThis extends NodoAcceso{
    Token nodoThis;

    public NodoAccesoThis(Token nodoThis) {
        this.nodoThis = nodoThis;
    }

    @Override
    public Tipo chequear() throws SemanticException {
        return null;
    }
}

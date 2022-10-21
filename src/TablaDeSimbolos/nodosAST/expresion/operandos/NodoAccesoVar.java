package TablaDeSimbolos.nodosAST.expresion.operandos;

import TablaDeSimbolos.Tipo;
import exceptions.SemanticException;
import lexycal.Token;

public class NodoAccesoVar extends NodoAcceso{

    Token idVar;

    public NodoAccesoVar(Token idVar) {
        this.idVar = idVar;
    }

    @Override
    public Tipo chequear() throws SemanticException {
        return null;
    }
}

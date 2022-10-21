package TablaDeSimbolos.nodosAST.expresion.operandos;

import TablaDeSimbolos.Tipo;
import TablaDeSimbolos.nodosAST.expresion.NodoExpresion;
import exceptions.SemanticException;

public class NodoAccesoExpresionParentizada extends NodoAcceso{

    NodoExpresion expresion;

    public NodoAccesoExpresionParentizada(NodoExpresion expresion) {
        this.expresion = expresion;
    }

    @Override
    public Tipo chequear() throws SemanticException {
        return null;
    }
}

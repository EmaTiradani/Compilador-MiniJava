package TablaDeSimbolos.nodosAST.expresion.operandos;

import TablaDeSimbolos.Tipo;
import exceptions.SemanticException;
import lexycal.Token;

public class NodoAccesoConstructor extends NodoAcceso{

    Token idClase;


    public NodoAccesoConstructor(Token idClase) {
        this.idClase = idClase;
    }

    @Override
    public Tipo chequear() throws SemanticException {
        return null;
    }
}

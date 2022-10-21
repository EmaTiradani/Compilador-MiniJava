package TablaDeSimbolos.nodosAST.expresion;

import TablaDeSimbolos.Tipo;
import exceptions.SemanticException;

public abstract class NodoExpresion {

    public abstract Tipo chequear() throws SemanticException;
}

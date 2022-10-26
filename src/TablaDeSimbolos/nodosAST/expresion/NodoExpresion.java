package TablaDeSimbolos.nodosAST.expresion;

import TablaDeSimbolos.Tipo;
import exceptions.SemanticException;
import lexycal.Token;

public abstract class NodoExpresion {

    public abstract Tipo chequear() throws SemanticException;

    public abstract Token getToken();
}

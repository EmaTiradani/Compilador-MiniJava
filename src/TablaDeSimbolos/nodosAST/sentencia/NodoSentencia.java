package TablaDeSimbolos.nodosAST.sentencia;

import exceptions.SemanticException;

public abstract class NodoSentencia {

    public abstract void chequear() throws SemanticException;
}

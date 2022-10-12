package TablaDeSimbolos.nodosAST.sentencia;

import TablaDeSimbolos.nodosAST.expresion.NodoExpresion;

public abstract class NodoAsignacion extends NodoSentencia{
    //protected NodoAcceso ladoIzq; Implementar NodoAcceso
    protected NodoExpresion ladoDer;
    
    public abstract void chequear();
}

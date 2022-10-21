package TablaDeSimbolos.nodosAST.sentencia;

import TablaDeSimbolos.nodosAST.expresion.NodoExpresion;
import TablaDeSimbolos.nodosAST.expresion.operandos.NodoAcceso;
import exceptions.SemanticException;

public abstract class NodoAsignacion extends NodoSentencia{
    protected NodoExpresion ladoDer;
    protected NodoAcceso ladoIzq;

    public void setNodoExpresion(NodoExpresion nodoExpresion){
        this.ladoDer = nodoExpresion;
    }

    public void setNodoAsignacion(NodoAcceso nodoAcceso){
        this.ladoIzq = nodoAcceso;
    }
}

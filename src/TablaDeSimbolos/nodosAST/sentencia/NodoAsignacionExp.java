package TablaDeSimbolos.nodosAST.sentencia;

import TablaDeSimbolos.Tipo;
import TablaDeSimbolos.nodosAST.expresion.NodoExpresion;
import TablaDeSimbolos.nodosAST.expresion.operandos.NodoAcceso;
import exceptions.SemanticException;
import lexycal.Token;

public class NodoAsignacionExp extends NodoAsignacion{

    Token tipoAsignacion;
    NodoAcceso nodoAcceso;
    NodoExpresion nodoExpresion;

    public NodoAsignacionExp(Token tipoAsignacion, NodoAcceso nodoAcceso) {
        this.tipoAsignacion = tipoAsignacion;
        this.nodoAcceso = nodoAcceso;
        this.nodoExpresion = nodoExpresion;
    }

    @Override
    public void chequear() throws SemanticException {
        Tipo tipoAcceso = nodoAcceso.chequear();

        if(!tipoAcceso.tipoCompatible(new Tipo("int"))){
            throw new SemanticException(" se esperaba un entero", tipoAsignacion);
        }
        if (!nodoAcceso.esAsignable()) {
            throw new SemanticException("Lado izquierdo incompatible, se esperaba una variable ",tipoAsignacion);
        }
    }

    public Token getTipoAsignacion() {
        return tipoAsignacion;
    }

    public void setTipoAsignacion(Token tipoAsignacion) {
        this.tipoAsignacion = tipoAsignacion;
    }

    public NodoAcceso getNodoAcceso() {
        return nodoAcceso;
    }

    public void setNodoAcceso(NodoAcceso nodoAcceso) {
        this.nodoAcceso = nodoAcceso;
    }

    public NodoExpresion getNodoExpresion() {
        return nodoExpresion;
    }

}

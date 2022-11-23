package TablaDeSimbolos.nodosAST.sentencia;

import TablaDeSimbolos.Tipo;
import TablaDeSimbolos.nodosAST.expresion.NodoExpresion;
import TablaDeSimbolos.nodosAST.expresion.operandos.NodoAcceso;
import exceptions.SemanticException;
import lexycal.Token;

public class NodoAsignacionExp extends NodoAsignacion{

    Token tipoAsignacion;
    //NodoAcceso nodoAcceso;
    //NodoExpresion nodoExpresion;

    public NodoAsignacionExp(Token tipoAsignacion, NodoAcceso nodoAcceso) {
        this.tipoAsignacion = tipoAsignacion;
        this.ladoIzq = nodoAcceso;
        //this.nodoExpresion = nodoExpresion;
    }

    @Override
    public void chequear() throws SemanticException {

        if (!ladoIzq.esAsignable()) {
            throw new SemanticException("Lado izquierdo incompatible, se esperaba una variable ",tipoAsignacion);
        }

        Tipo tipoAcceso = ladoIzq.chequear();

        if(!tipoAcceso.tipoCompatible(tipoAcceso)){
            throw new SemanticException(" la expresion no es de un tipo compatible", tipoAsignacion);
        }

        if(!ladoIzq.chequear().checkSubtipo(ladoDer.chequear())){
            throw new SemanticException(" La expresion no es de un tipo compatible", tipoAsignacion);
        }

    }

    @Override
    public void generar() {
        ladoDer.generar();
        ladoIzq.setLadoIzquierdoAsignacion();
        ladoIzq.generar();
    }

    public void setExpresion(NodoExpresion expresion){
        this.ladoDer = expresion;
    }

    public Token getTipoAsignacion() {
        return tipoAsignacion;
    }

    public void setTipoAsignacion(Token tipoAsignacion) {
        this.tipoAsignacion = tipoAsignacion;
    }

    public NodoAcceso getNodoAcceso() {
        return ladoIzq;
    }

    public void setNodoAcceso(NodoAcceso nodoAcceso) {
        this.ladoIzq = nodoAcceso;
    }


}

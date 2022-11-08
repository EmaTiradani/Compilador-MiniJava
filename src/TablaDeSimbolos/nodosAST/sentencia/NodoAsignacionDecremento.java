package TablaDeSimbolos.nodosAST.sentencia;

import TablaDeSimbolos.TablaDeSimbolos;
import TablaDeSimbolos.Tipo;
import TablaDeSimbolos.nodosAST.expresion.NodoExpresion;
import TablaDeSimbolos.nodosAST.expresion.operandos.NodoAcceso;
import exceptions.SemanticException;
import lexycal.Token;

public class NodoAsignacionDecremento extends NodoAsignacion{

    Token tipoAsignacion;
    NodoAcceso nodoAcceso;

    public NodoAsignacionDecremento(Token tipoAsignacion, NodoAcceso nodoAcceso) {
        this.tipoAsignacion = tipoAsignacion;
        this.nodoAcceso = nodoAcceso;
    }

    @Override
    public void chequear() throws SemanticException {
        Tipo tipoAcceso = nodoAcceso.chequear();

        if(!tipoAcceso.mismoTipo(new Tipo("int"))){
            throw new SemanticException(" se esperaba un entero", tipoAsignacion);
        }
        if (!nodoAcceso.esAsignable()) {
            throw new SemanticException("Lado izquierdo incompatible, se esperaba una variable ",tipoAsignacion);
        }

        Tipo tipoExpresion = ladoDer.chequear();
        if(!tipoExpresion.mismoTipo(new Tipo("int"))){
            throw new SemanticException("Lado derecho incompatible, se esperaba un entero ",tipoAsignacion);
        }
    }

    @Override
    public void generar() {
        ladoIzq.generar();
        TablaDeSimbolos.gen("PUSH 1");
        TablaDeSimbolos.gen("SUB ; realizamos la resta");
        ladoIzq.setLadoIzquierdoAsignacion();
        ladoIzq.generar();
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
}

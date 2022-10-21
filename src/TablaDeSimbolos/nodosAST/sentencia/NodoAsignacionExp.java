package TablaDeSimbolos.nodosAST.sentencia;

import TablaDeSimbolos.nodosAST.expresion.NodoExpresion;
import TablaDeSimbolos.nodosAST.expresion.operandos.NodoAcceso;
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
    public void chequear() {

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

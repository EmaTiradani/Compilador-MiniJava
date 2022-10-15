package TablaDeSimbolos.nodosAST.sentencia;

import TablaDeSimbolos.nodosAST.expresion.operandos.NodoAcceso;
import lexycal.Token;

public class NodoAsignacionDecremento extends NodoAsignacion{

    Token tipoAsignacion;
    NodoAcceso nodoAcceso;

    public NodoAsignacionDecremento(Token tipoAsignacion, NodoAcceso nodoAcceso) {
        this.tipoAsignacion = tipoAsignacion;
        this.nodoAcceso = nodoAcceso;
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
}

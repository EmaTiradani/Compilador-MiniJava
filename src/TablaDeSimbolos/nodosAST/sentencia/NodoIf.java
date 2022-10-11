package TablaDeSimbolos.nodosAST.sentencia;

import TablaDeSimbolos.nodosAST.expresion.NodoExpresion;

public class NodoIf extends NodoSentencia {
    NodoExpresion condicion;
    NodoSentencia sentencia;

    public NodoIf(NodoExpresion condicion, NodoSentencia sentencia) {
        this.condicion = condicion;
        this.sentencia = sentencia;
    }

    public NodoExpresion getCondicion() {
        return condicion;
    }

    public void setCondicion(NodoExpresion condicion) {
        this.condicion = condicion;
    }

    public NodoSentencia getSentencia() {
        return sentencia;
    }

    public void setSentencia(NodoSentencia sentencia) {
        this.sentencia = sentencia;
    }

    @Override
    public void chequear() {

    }
}

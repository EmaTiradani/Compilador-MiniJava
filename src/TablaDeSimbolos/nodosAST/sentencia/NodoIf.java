package TablaDeSimbolos.nodosAST.sentencia;

import TablaDeSimbolos.nodosAST.expresion.NodoExpresion;
import lexycal.Token;

public class NodoIf extends NodoSentencia {

    Token tokenIf;
    NodoExpresion condicion;
    NodoSentencia sentenciaIf;
    NodoSentencia sentenciaElse;

    public NodoIf(Token tokenIf, NodoExpresion condicion, NodoSentencia sentencia, NodoSentencia sentenciaElse) {

        this.tokenIf = tokenIf;
        this.condicion = condicion;
        this.sentenciaIf = sentencia;
        this.sentenciaElse = sentenciaElse;
    }

    public NodoExpresion getCondicion() {
        return condicion;
    }

    public void setCondicion(NodoExpresion condicion) {
        this.condicion = condicion;
    }

    public NodoSentencia getSentenciaIf() {
        return sentenciaIf;
    }

    public void setSentenciaIf(NodoSentencia sentencia) {
        this.sentenciaIf = sentencia;
    }

    public NodoSentencia getSentenciaElse() {
        return sentenciaElse;
    }

    public void setSentenciaElse(NodoSentencia sentencia) {
        this.sentenciaElse = sentencia;
    }

    @Override
    public void chequear() {

    }
}

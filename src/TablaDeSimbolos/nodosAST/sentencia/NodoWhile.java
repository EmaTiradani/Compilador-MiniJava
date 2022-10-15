package TablaDeSimbolos.nodosAST.sentencia;

import TablaDeSimbolos.nodosAST.expresion.NodoExpresion;
import lexycal.Token;

public class NodoWhile extends NodoSentencia{

    Token tokenWhile;
    NodoExpresion condicion;
    NodoSentencia sentencia;

    public NodoWhile(Token tokenWhile, NodoExpresion condicion, NodoSentencia sentencia) {
        this.tokenWhile = tokenWhile;
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

    public Token getTokenWhile() {
        return tokenWhile;
    }

    public void setTokenWhile(Token tokenWhile) {
        this.tokenWhile = tokenWhile;
    }

    @Override
    public void chequear() {

    }
}

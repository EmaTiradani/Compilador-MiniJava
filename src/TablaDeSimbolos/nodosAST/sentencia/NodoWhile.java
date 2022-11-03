package TablaDeSimbolos.nodosAST.sentencia;

import TablaDeSimbolos.TablaDeSimbolos;
import TablaDeSimbolos.Tipo;
import TablaDeSimbolos.nodosAST.expresion.NodoExpresion;
import exceptions.SemanticException;
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
    public void chequear() throws SemanticException {
        if(condicion.chequear().mismoTipo(new Tipo("boolean"))){

            // Lo de los bloques es lo mismo que lo del if
            NodoBloque bloqueWhile = new NodoBloque();
            TablaDeSimbolos.apilarBloque(bloqueWhile);

            sentencia.chequear();

            TablaDeSimbolos.desapilarBloqueActual();
        }else{
            throw new SemanticException(" La condicion del while debe ser una expresion booleana", tokenWhile);
        }
    }

    @Override
    public void generar() {

    }
}

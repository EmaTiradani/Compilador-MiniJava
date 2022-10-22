package TablaDeSimbolos.nodosAST.sentencia;

import TablaDeSimbolos.Tipo;
import TablaDeSimbolos.nodosAST.expresion.NodoExpresion;
import exceptions.SemanticException;
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
    public void chequear() throws SemanticException {
        if(condicion.chequear().mismoTipo(new Tipo("boolean"))){

            // TODO Meto el bloque del if por si el if usa un bloque en vez de una sentencia pelada?

            sentenciaIf.chequear();

            // Todo aca lo saco a la wea

            if(sentenciaElse != null){
                // Todo lo mismo que los comments del bloque del if
                sentenciaElse.chequear();
            }

        }else{
            throw new SemanticException(" La condicion del if debe ser una expresion booleana", tokenIf);
        }
    }
}

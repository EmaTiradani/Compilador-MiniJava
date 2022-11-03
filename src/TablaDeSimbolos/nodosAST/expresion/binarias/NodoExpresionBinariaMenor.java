package TablaDeSimbolos.nodosAST.expresion.binarias;

import TablaDeSimbolos.Tipo;
import TablaDeSimbolos.nodosAST.expresion.NodoExpresionBinaria;
import exceptions.SemanticException;
import lexycal.Token;

public class NodoExpresionBinariaMenor extends NodoExpresionBinaria {
    public NodoExpresionBinariaMenor(Token operador) {
        super(operador);
    }

    @Override
    public Tipo chequear() throws SemanticException {
        if(ladoIzquierdo.chequear().mismoTipo(new Tipo("int")) && ladoDerecho.chequear().mismoTipo(new Tipo("int"))){
            return new Tipo("boolean");
        }else{
            throw new SemanticException("Ambos lados del operador < deben ser numeros enteros", operador);
        }
    }

    @Override
    public void generar() {

    }
}

package TablaDeSimbolos.nodosAST.expresion.binarias;

import TablaDeSimbolos.Tipo;
import TablaDeSimbolos.nodosAST.expresion.NodoExpresionBinaria;
import exceptions.SemanticException;
import lexycal.Token;

public class NodoExpresionBinariaIgual extends NodoExpresionBinaria {
    public NodoExpresionBinariaIgual(Token operador) {
        super(operador);
    }

    @Override
    public Tipo chequear() throws SemanticException {
        Tipo tipoLadoIzq = ladoIzquierdo.chequear();
        Tipo tipoLadoDer = ladoDerecho.chequear();

        if(tipoLadoDer.checkSubtipo(tipoLadoIzq) || tipoLadoIzq.checkSubtipo(tipoLadoDer)){
            return new Tipo("boolean");
        }else{
            throw new SemanticException("Ambos lados del operador == deben ser subtipos entre si", operador);
        }
    }
}

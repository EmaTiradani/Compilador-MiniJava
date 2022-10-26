package TablaDeSimbolos.nodosAST.expresion.binarias;

import TablaDeSimbolos.Tipo;
import TablaDeSimbolos.nodosAST.expresion.NodoExpresionBinaria;
import exceptions.SemanticException;
import lexycal.Token;

public class NodoExpresionBinariaDistinto extends NodoExpresionBinaria {
    public NodoExpresionBinariaDistinto(Token operador) {
        super(operador);
    }

    @Override
    public Tipo chequear() throws SemanticException {
        Tipo tipoLadoIzq = ladoIzquierdo.chequear();
        Tipo tipoLadoDer = ladoDerecho.chequear();

        if(tipoLadoDer.checkSubtipo(tipoLadoIzq) || tipoLadoIzq.checkSubtipo(tipoLadoDer) || tipoLadoIzq.checkSubtipo(new Tipo("null")) || tipoLadoDer.checkSubtipo(new Tipo("null"))){
            return new Tipo("boolean");
        }else{
            throw new SemanticException("Ambos lados de la expresion != deben ser subtipos entre si", operador);
        }

    }
}

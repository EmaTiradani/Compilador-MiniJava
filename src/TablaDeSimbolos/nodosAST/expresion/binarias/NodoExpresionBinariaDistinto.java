package TablaDeSimbolos.nodosAST.expresion.binarias;

import TablaDeSimbolos.TablaDeSimbolos;
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

    @Override
    public void generar() {
        ladoIzquierdo.generar();
        ladoDerecho.generar();
        //TODO importa el orden de estos dos de arriba? Creo que no, total van a quedar los ultimos 2 en el tope de la pila y la instruccion viene despues
        TablaDeSimbolos.gen("NE ; Comparacion por desigual");
    }
}

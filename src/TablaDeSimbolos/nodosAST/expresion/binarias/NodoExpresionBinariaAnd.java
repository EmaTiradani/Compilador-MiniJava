package TablaDeSimbolos.nodosAST.expresion.binarias;

import TablaDeSimbolos.TablaDeSimbolos;
import TablaDeSimbolos.Tipo;
import TablaDeSimbolos.nodosAST.expresion.NodoExpresionBinaria;
import exceptions.SemanticException;
import lexycal.Token;

public class NodoExpresionBinariaAnd extends NodoExpresionBinaria {
    public NodoExpresionBinariaAnd(Token operador) {
        super(operador);
    }

    @Override
    public Tipo chequear() throws SemanticException {
        if(ladoIzquierdo.chequear().mismoTipo(new Tipo("boolean")) && ladoDerecho.chequear().mismoTipo(new Tipo("boolean"))){
            return new Tipo("boolean");
        }else{
            throw new SemanticException("Ambos lados del operador AND deben ser de tipo boolean", operador);
        }
    }

    @Override
    public void generar() {
        ladoIzquierdo.generar();
        ladoDerecho.generar();
        TablaDeSimbolos.gen("PUSH "+operador.getLexema());
    }


}

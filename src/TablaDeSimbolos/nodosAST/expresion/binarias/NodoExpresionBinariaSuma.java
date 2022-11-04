package TablaDeSimbolos.nodosAST.expresion.binarias;

import TablaDeSimbolos.TablaDeSimbolos;
import TablaDeSimbolos.Tipo;
import TablaDeSimbolos.nodosAST.expresion.NodoExpresionBinaria;
import exceptions.SemanticException;
import lexycal.Token;

public class NodoExpresionBinariaSuma extends NodoExpresionBinaria {
    public NodoExpresionBinariaSuma(Token operador) {
        super(operador);
    }

    @Override
    public Tipo chequear() throws SemanticException {
        if(ladoIzquierdo.chequear().mismoTipo(new Tipo("int")) && ladoDerecho.chequear().mismoTipo(new Tipo("int"))){
            return new Tipo("int");
        }else{
            throw new SemanticException("Ambos lados del operador + deben ser numeros enteros", operador);
        }
    }

    @Override
    public void generar() {
        ladoIzquierdo.generar();
        ladoDerecho.generar();
        TablaDeSimbolos.gen("ADD ; Suma");
    }
}

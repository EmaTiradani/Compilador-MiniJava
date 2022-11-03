package TablaDeSimbolos.nodosAST.expresion.operandos;

import TablaDeSimbolos.TablaDeSimbolos;
import TablaDeSimbolos.Tipo;
import TablaDeSimbolos.nodosAST.expresion.NodoOperando;
import exceptions.SemanticException;
import lexycal.Token;

public class NodoString extends NodoOperando {

    Token stringLit;

    public NodoString(Token stringLit) {
        this.stringLit = stringLit;
    }

    public Token getToken() {
        return stringLit;
    }

    public void setStringLit(Token stringLit) {
        this.stringLit = stringLit;
    }

    @Override
    public Tipo chequear() throws SemanticException {
        return new Tipo("String");
    }

    @Override
    public void generar() {// TODO lo que tengo anotado aca y en notion
        //TablaDeSimbolos.gen("PUSH "+stringLit.getLexema());
        TablaDeSimbolos.gen(".DATA"); // Es necesario este .data?
        TablaDeSimbolos.gen("litString_"+stringLit.getLexema()+": DW \""+stringLit.getLexema()+"\",0");
    }
}

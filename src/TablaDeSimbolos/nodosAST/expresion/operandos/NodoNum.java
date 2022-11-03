package TablaDeSimbolos.nodosAST.expresion.operandos;

import TablaDeSimbolos.TablaDeSimbolos;
import TablaDeSimbolos.Tipo;
import TablaDeSimbolos.nodosAST.expresion.NodoOperando;
import exceptions.SemanticException;
import lexycal.Token;

public class NodoNum extends NodoOperando {
    Token valor;

    public NodoNum(Token valor) {
        this.valor = valor;
    }

    public Token getToken() {
        return valor;
    }

    public void setValor(Token valor) {
        this.valor = valor;
    }

    @Override
    public Tipo chequear() throws SemanticException {
        return new Tipo("int");
    }


    @Override
    public void generar() {
        TablaDeSimbolos.gen("PUSH "+valor.getLexema());
    }
}


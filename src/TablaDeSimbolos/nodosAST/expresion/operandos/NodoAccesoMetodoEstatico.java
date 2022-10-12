package TablaDeSimbolos.nodosAST.expresion.operandos;

import lexycal.Token;

public class NodoAccesoMetodoEstatico {

    Token idClaseEstatica;
    Token idMetodoEstatico;

    public NodoAccesoMetodoEstatico(Token idClaseEstatica, Token idMetodoEstatico) {
        this.idClaseEstatica = idClaseEstatica;
        this.idMetodoEstatico = idMetodoEstatico;
    }

    public Token getIdClaseEstatica() {
        return idClaseEstatica;
    }

    public void setIdClaseEstatica(Token idClaseEstatica) {
        this.idClaseEstatica = idClaseEstatica;
    }

    public Token getIdMetodoEstatico() {
        return idMetodoEstatico;
    }

    public void setIdMetodoEstatico(Token idMetodoEstatico) {
        this.idMetodoEstatico = idMetodoEstatico;
    }
}

package TablaDeSimbolos.nodosAST.expresion.operandos;

import TablaDeSimbolos.nodosAST.expresion.NodoExpresion;
import lexycal.Token;

import java.util.List;

public class NodoAccesoMetodoEstatico extends NodoAcceso{

    protected Token idClaseEstatica;
    protected Token idMetodoEstatico;
    protected List<NodoExpresion> parametrosActuales;

    public NodoAccesoMetodoEstatico(Token idClaseEstatica, Token idMetodoEstatico, List<NodoExpresion> parametros) {
        this.idClaseEstatica = idClaseEstatica;
        this.idMetodoEstatico = idMetodoEstatico;
        this.parametrosActuales = parametros;
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

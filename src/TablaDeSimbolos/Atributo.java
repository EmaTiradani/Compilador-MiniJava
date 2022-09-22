package TablaDeSimbolos;

import lexycal.Token;
import lexycal.TokenId;

public class Atributo {

    private Token idVar;
    private Tipo tipo;
    private TokenId visibilidad;

    public Atributo(Token idVar, Tipo tipo, TokenId visibilidad){
        this.idVar = idVar;
        this.tipo = tipo;
        this.visibilidad = visibilidad;
    }

    public String getId(){
        return idVar.getLexema();
    }

    public Tipo getTipo(){
        return tipo;
    }

    public TokenId getVisibilidad(){
        return visibilidad;
    }
}
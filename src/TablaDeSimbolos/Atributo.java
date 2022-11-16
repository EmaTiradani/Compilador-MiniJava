package TablaDeSimbolos;

import lexycal.Token;
import lexycal.TokenId;

public class Atributo {

    private Token idVar;
    private Tipo tipo;
    private TokenId visibilidad;
    private int offset;

    public Atributo(Token idVar, Tipo tipo, TokenId visibilidad){
        this.idVar = idVar;
        this.tipo = tipo;
        this.visibilidad = visibilidad;
        offset = -1;
    }

    public String getId(){
        return idVar.getLexema();
    }

    public Token getToken(){ return idVar;}

    public Tipo getTipo(){
        return tipo;
    }

    public TokenId getVisibilidad(){
        return visibilidad;
    }

    public void setOffset(int offset){
        this.offset = offset;
    }

    public int getOffset(){
        return offset;
    }

    public void print(){
        System.out.println("Atributo: " + idVar.getLexema() + " |tipo: " + tipo.getType() + " |visibilidad : " + visibilidad);
    }
}

package TablaDeSimbolos;

import lexycal.Token;

public class Argumento {

    private Token idVar;
    private Tipo tipoParametro;

    public Argumento(Token id, Tipo tipo){
        this.idVar = id;
        this.tipoParametro = tipo;
    }

    public Token getIdVar(){
        return idVar;
    }

    public Tipo getTipoParametro(){
        return tipoParametro;
    }

    public void print(){
        System.out.println("Argumento: " + idVar + "(" + tipoParametro.getType() + ")");
    }
}

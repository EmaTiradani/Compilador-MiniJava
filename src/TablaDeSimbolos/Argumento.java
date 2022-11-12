package TablaDeSimbolos;

import exceptions.SemanticException;
import lexycal.Token;

public class Argumento {

    private Token idVar;
    private Tipo tipoParametro;
    private int offset;

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

    public void checkDec() throws SemanticException {
        tipoParametro.checkExistencia(idVar.getLinea());
    }

    public void setOffset(int offset){
        this.offset = offset;
    }

    public int getOffset(){
        return offset;
    }


    public void print(){
        System.out.println("Argumento: " + idVar + "(" + tipoParametro.getType() + ")");
    }
}

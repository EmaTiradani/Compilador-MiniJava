package TablaDeSimbolos;

import exceptions.SemanticException;
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

    public void checkDec() throws SemanticException {
        tipoParametro.checkExistencia(idVar.getLinea());
    }

    public void print(){
        System.out.println("Argumento: " + idVar + "(" + tipoParametro.getType() + ")");
    }
}

package TablaDeSimbolos;

import exceptions.SemanticException;
import lexycal.Token;


import static lexycal.TokenId.*;

public class Tipo {

    protected boolean isPrimitive;
    protected String id;

    public Tipo(String id){
        this.id = id;
        switch(id){
            case "boolean": isPrimitive = true;
            break;
            case "char": isPrimitive = true;
            break;
            case "int": isPrimitive = true;
            break;
            default : isPrimitive = false;
            break;
        }
    }

    public String getType(){
        return id;
    }

    public void checkExistencia(int numeroLinea) throws SemanticException {
        if(!isPrimitive){
            if(!TablaDeSimbolos.existeClase(id) && !TablaDeSimbolos.existeInterfaz(id))
                throw new SemanticException("no esta declarada", new Token(idClase, id, numeroLinea));
        }
    }

    public boolean isPrimitive(){
        return isPrimitive;
    }

    public void setPrimitive(){
        isPrimitive = true;
    }

}

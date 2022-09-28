package TablaDeSimbolos;

import exceptions.SemanticException;
import lexycal.Token;


import static lexycal.TokenId.*;

public class Tipo {

    private boolean isPrimitive;
    private String id;

    public Tipo(String id){
        this.id = id;
        isPrimitive = false;
    }

    public String getType(){
        return id;
    }

    public void checkExistencia(int numeroLinea) throws SemanticException {
        if(!isPrimitive){
            if(!TablaDeSimbolos.existeClase(id))
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

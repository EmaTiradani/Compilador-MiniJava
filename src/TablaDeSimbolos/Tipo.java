package TablaDeSimbolos;

import exceptions.SemanticException;
import lexycal.Token;


import java.util.ArrayList;

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

    public boolean tipoCompatible(Tipo tipo) {
        if(tipo.isPrimitive){
            if(tipo.getType().equals(this.id) || tipo.getType().equals("null"))
                return true;
            else
                return false;
        }else{
            return checkSubtipo(tipo);
        }
    }

    public boolean checkSubtipo(Tipo tipo){// Obtengo los ancestros de tipo y veo si estoy entre ellos.
        if(isPrimitive){
            if(tipo.isPrimitive){
                if(mismoTipo(tipo))
                    return true;
                else
                    return false;
            }else{
                return false;
            }
        }
        ArrayList<String> ancestros = new ArrayList<>();
        ancestros.add(this.id);
        if(TablaDeSimbolos.existeClase(tipo.getType())){
            ancestros = TablaDeSimbolos.getClase(tipo.getType()).getAncestros();
        }else if(TablaDeSimbolos.existeInterfaz(tipo.getType())){
            ancestros = TablaDeSimbolos.getInterfaz(tipo.getType()).getAncestros();
        }


        if(ancestros.contains(id) || mismoTipo(tipo))
            return true;// tipo es un subtipo mio
        else
            return false;
    }

    public boolean mismoTipo(Tipo tipo) {
        if(tipo.getType().equals(this.id))
            return true;
        else
            return false;
    }


}

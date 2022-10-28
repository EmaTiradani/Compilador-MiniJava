package TablaDeSimbolos.nodosAST.expresion.operandos;

import TablaDeSimbolos.Clase;
import TablaDeSimbolos.TablaDeSimbolos;
import TablaDeSimbolos.*;
import exceptions.SemanticException;
import lexycal.Token;
import lexycal.TokenId;

import java.util.ArrayList;

public class NodoVarEncadenada extends NodoEncadenado {

    public Token idVar;


    public NodoVarEncadenada(Token idVarEncadenada) {
        idVar = idVarEncadenada;
    }

    public Token getToken(){
        return idVar;
    }

    @Override
    public Tipo chequear(Tipo tipoClase) throws SemanticException {
        Tipo tipoAtributo;
        Clase claseContenedora = TablaDeSimbolos.getClase(tipoClase.getType());
        if(claseContenedora != null){
            Atributo atributo = claseContenedora.getAtributo(idVar.getLexema());
            if(atributo != null){
                // DONE poner el chequeo de que el padre no tenga el atributo privado para poder acceder a mis privados que esta en NodoAccesoThis
                if(atributo.getVisibilidad() == TokenId.kw_public || claseContenedora.getNombreClase().equals(TablaDeSimbolos.claseActual.getNombreClase())){
                    tipoAtributo = atributo.getTipo();
                }else{ // Si es privado
                    throw new SemanticException("Se esta intentando acceder al atributo privado "+idVar.getLexema()+" de la clase "+claseContenedora.getNombreClase(), idVar);
                }
            }else{
                throw new SemanticException("El atributo "+idVar.getLexema()+" no existe", idVar);
            }
        }else{
            throw new SemanticException("No existe la clase a la que se quiere acceder", idVar);
        }

        if(nodoEncadenado == null){
            return tipoAtributo;
        }else{
            return nodoEncadenado.chequear(tipoAtributo);
        }
    }

    @Override
    public Tipo chequearThis(Tipo tipoClase) throws SemanticException {
        Tipo tipoAtributo;
        Clase claseContenedora = TablaDeSimbolos.getClase(tipoClase.getType());
        if(claseContenedora != null){
            Atributo atributo = claseContenedora.getAtributo(idVar.getLexema());
            if(atributo != null){
                tipoAtributo = atributo.getTipo();
            }else{
                throw new SemanticException("El atributo "+idVar.getLexema()+" no existe", idVar);
            }
        }else{
            throw new SemanticException("No existe la clase a la que se quiere acceder", idVar);
        }

        if(nodoEncadenado == null){
            return tipoAtributo;
        }else{
            return nodoEncadenado.chequear(tipoAtributo);
        }
    }

    @Override
    public boolean esAsignable() {
        if(nodoEncadenado == null){
            return true;
        }else{
            return nodoEncadenado.esAsignable();
        }
    }

    @Override
    public boolean esLlamable() {
        if(nodoEncadenado == null){
            return false;
        }else{
            return nodoEncadenado.esLlamable();
        }
    }


}

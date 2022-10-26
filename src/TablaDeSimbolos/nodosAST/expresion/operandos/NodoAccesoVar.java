package TablaDeSimbolos.nodosAST.expresion.operandos;

import TablaDeSimbolos.TablaDeSimbolos;
import TablaDeSimbolos.Tipo;
import TablaDeSimbolos.nodosAST.sentencia.NodoVarLocal;
import exceptions.SemanticException;
import lexycal.Token;
import TablaDeSimbolos.*;

public class NodoAccesoVar extends NodoAcceso{

    Token idVar;

    public NodoAccesoVar(Token idVar) {
        this.idVar = idVar;
    }

    @Override
    public Tipo chequear() throws SemanticException {
        Tipo tipoVar;
        NodoVarLocal nodoVarLocal = TablaDeSimbolos.getVarLocalClaseActual(idVar.getLexema());
        if(nodoVarLocal != null){
            tipoVar = nodoVarLocal.getTipo();
        }else{
            Argumento argumento = TablaDeSimbolos.metodoActual.getArgumento(idVar.getLexema());
            if(argumento != null){
                tipoVar = argumento.getTipoParametro();
            }else{
                Atributo atributo = TablaDeSimbolos.claseActual.getAtributo(idVar.getLexema());
                if(atributo != null){
                    if(!TablaDeSimbolos.metodoActual.getEstatico()){
                        tipoVar = atributo.getTipo();
                    }else{
                        throw new SemanticException("Se intento acceder a un atributo de instancia desde un entorno estatico", idVar);
                    }
                }else{
                    throw new SemanticException("Se intento acceder al atributo "+idVar.getLexema()+"pero no existe(o no es accesible)");
                }
            }
        }

        // Chequeo el encadenado si es que tiene uno TODO (revisar si me olvide de otro mas)
        if(encadenado != null){
            return encadenado.chequear(tipoVar);
        }
        return tipoVar;
    }

    @Override
    public Token getToken() {
        return idVar;
    }

    @Override
    public boolean esAsignable() {
        if(encadenado == null){
            return true;
        }else{
            return encadenado.esAsignable();
        }
    }

    @Override
    public boolean esLlamable() {
        if(encadenado == null){
            return false;
        }else{
            return encadenado.esLlamable();
        }
    }
}

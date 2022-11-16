package TablaDeSimbolos.nodosAST.expresion.operandos;

import TablaDeSimbolos.Clase;
import TablaDeSimbolos.TablaDeSimbolos;
import TablaDeSimbolos.Tipo;
import TablaDeSimbolos.nodosAST.expresion.NodoExpresion;
import exceptions.SemanticException;
import lexycal.Token;
import TablaDeSimbolos.*;

import java.util.ArrayList;
import java.util.List;

public class NodoMetodoEncadenado extends NodoEncadenado {

    protected List<NodoExpresion> parametros;
    protected Token idMet;
    protected Metodo metodo;

    public NodoMetodoEncadenado(Token tokenIdMet) {
        idMet = tokenIdMet;
        parametros = new ArrayList<>();
        esLadoIzquierdo = false;
    }

    @Override
    public Tipo chequear(Tipo tipoEncadenadoLadoIzq) throws SemanticException {
        Tipo tipoMetodo;
        Clase claseContenedora = TablaDeSimbolos.getClase(tipoEncadenadoLadoIzq.getType());
        if(claseContenedora == null)
            claseContenedora = TablaDeSimbolos.getInterfaz(tipoEncadenadoLadoIzq.getType());
        if(claseContenedora != null){
            metodo = claseContenedora.getMetodoQueConformaParametros(idMet, parametros);
            if(metodo == null){
                throw new SemanticException("El metodo "+idMet.getLexema()+" no es accesible en la clase "+claseContenedora.getNombreClase(), idMet);
            }else{
                tipoMetodo = metodo.getTipoRetorno();
            }
        }else{
            throw new SemanticException("El tipo no es de una clase declarada", idMet);
        }

        if(nodoEncadenado == null){
            return tipoMetodo;
        }else{
            return nodoEncadenado.chequear(tipoMetodo);
        }
    }

    @Override
    public Token getToken() {
        return idMet;
    }

    @Override
    public boolean esAsignable() {
        if(nodoEncadenado == null){
            return false;
        }else{
            return nodoEncadenado.esAsignable();
        }
    }

    @Override
    public boolean esLlamable() {
        if(nodoEncadenado == null){
            return true;
        }else{
            return nodoEncadenado.esLlamable();
        }    }

    @Override
    public Tipo chequearThis(Tipo tipoEncadenadoLadoIzq) throws SemanticException {
        return chequear(tipoEncadenadoLadoIzq);
    }

    @Override
    public void generar() {
        if(metodo.getEstatico()){
            if(!metodo.getTipoRetorno().mismoTipo(new Tipo("void")))
                TablaDeSimbolos.gen("RMEM 1 ; Lugar para el retorno");
            for(NodoExpresion parametro : parametros){// TODO esto lo tendria que mandar al metodo estatico, no?
                parametro.generar();
            }
            TablaDeSimbolos.gen("PUSH "+metodo.getId().getLexema()+metodo.getClaseContenedora());
            TablaDeSimbolos.gen("CALL");
        }else{// Si el metodo es dinamico
            //TablaDeSimbolos.gen("LOAD 3 ; Carga el This");
            if(!metodo.getTipoRetorno().mismoTipo(new Tipo("void"))){
                TablaDeSimbolos.gen("RMEM 1 ; Lugar para el retorno");
                TablaDeSimbolos.gen("SWAP ; Pone el This en el tope de la pila");
            }
            for(NodoExpresion parametro : parametros){
                parametro.generar();
                TablaDeSimbolos.gen("SWAP");// Con esta instruccion mantengo el this en el tope de la pila
            }
            TablaDeSimbolos.gen("DUP ; Duplica el tope de la pila, porque LOADREF consume");
            TablaDeSimbolos.gen("LOADREF 0 ; Apila el valor de la VT");
            TablaDeSimbolos.gen("LOADREF " +metodo.getOffsetEnClase()+ " ; Carga el metodo accediendo a la VT" );
            TablaDeSimbolos.gen("CALL");
        }

        if(nodoEncadenado != null){
            if(this.esLadoIzquierdoAsignacion())
                nodoEncadenado.setLadoIzquierdoAsignacion();
            nodoEncadenado.generar();
        }
    }


    public List<NodoExpresion> getParametros() {
        return parametros;
    }

    public void setParametros(List<NodoExpresion> parametros) {
        this.parametros = parametros;
    }


}

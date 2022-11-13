package TablaDeSimbolos.nodosAST.expresion.operandos;

import TablaDeSimbolos.Metodo;
import TablaDeSimbolos.TablaDeSimbolos;
import TablaDeSimbolos.Tipo;
import TablaDeSimbolos.nodosAST.expresion.NodoExpresion;
import TablaDeSimbolos.nodosAST.sentencia.NodoBloque;
import exceptions.SemanticException;
import lexycal.Token;

import java.util.List;

public class NodoAccesoMetodo extends NodoAcceso{

    protected Token idMet;
    protected List<NodoExpresion> parametrosActuales;
    protected Metodo metodo;


    public NodoAccesoMetodo(Token id, List<NodoExpresion> parametrosActuales){
        this.idMet = id;
        this.parametrosActuales = parametrosActuales;
    }


    @Override
    public Tipo chequear() throws SemanticException {

        metodo = TablaDeSimbolos.claseActual.getMetodoQueConformaParametros(idMet, parametrosActuales);
        if(metodo == null){
            throw new SemanticException("No existe el metodo "+idMet.getLexema(), idMet);
        }
        // TODO tengo que chequear que no sea un metodo estatico? O eso lo hago directamente desde el acceso met Estatico?
        if(TablaDeSimbolos.metodoActual.getEstatico() && !metodo.getEstatico()){
            throw new SemanticException(" Se intento acceder a un metodo dinamico desde un metodo estatico", idMet);
        }

        if(encadenado == null){
            return metodo.getTipoRetorno();
        }else{
            return encadenado.chequear(metodo.getTipoRetorno());
        }

    }

    @Override
    public Token getToken() {
        return idMet;
    }

    @Override
    public boolean esAsignable() {
        if(encadenado == null){
            return false;
        }else{
            return encadenado.esAsignable();
        }
    }

    @Override
    public boolean esLlamable() {
        if(encadenado == null){
            return true;
        }else{
            return encadenado.esLlamable();
        }
    }

    @Override
    public void generar() {
        if(metodo.getEstatico()){
            if(!metodo.getTipoRetorno().mismoTipo(new Tipo("void")))
                TablaDeSimbolos.gen("RMEM 1 ; Lugar para el retorno");
            for(NodoExpresion parametro : parametrosActuales){// TODO esto lo tendria que mandar al metodo estatico, no?
                parametro.generar();
            }
            TablaDeSimbolos.gen("PUSH "+metodo.getId().getLexema()+metodo.getClaseContenedora());
            TablaDeSimbolos.gen("CALL");
        }else{// Si el metodo es dinamico
            TablaDeSimbolos.gen("LOAD 3 ; Carga el This");
            if(!metodo.getTipoRetorno().mismoTipo(new Tipo("void"))){
                TablaDeSimbolos.gen("RMEM 1 ; Lugar para el retorno");
                TablaDeSimbolos.gen("SWAP ; Pone el This en el tope de la pila");
            }
            for(NodoExpresion parametro : parametrosActuales){
                parametro.generar();
                TablaDeSimbolos.gen("SWAP");// Con esta instruccion mantengo el this en el tope de la pila
            }
            TablaDeSimbolos.gen("DUP ; Duplica el tope de la pila, porque LOADREF consume");
            TablaDeSimbolos.gen("LOADREF 0 ; Apila el valor de la VT");
            TablaDeSimbolos.gen("LOADREF " +metodo.getOffsetEnClase()+ " ; Carga el metodo accediendo a la VT" );
            TablaDeSimbolos.gen("CALL");
        }

        if(encadenado != null){
            if(this.esLadoIzquierdoAsignacion())
                encadenado.setLadoIzquierdoAsignacion();
            encadenado.generar();
        }

    }
}

package TablaDeSimbolos.nodosAST.expresion.operandos;

import TablaDeSimbolos.TablaDeSimbolos;
import TablaDeSimbolos.*;
import exceptions.SemanticException;
import lexycal.Token;
import lexycal.TokenId;

import java.util.ArrayList;

public class NodoAccesoThis extends NodoAcceso{
    Token nodoThis;

    public NodoAccesoThis(Token nodoThis) {
        this.nodoThis = nodoThis;
    }

    @Override
    public Tipo chequear() throws SemanticException {

        if(TablaDeSimbolos.metodoActual.getEstatico()){ // Si el metodo es estatico, error
            throw new SemanticException("Referencia a this desde un metodo estatico", nodoThis);
        }

        ArrayList<String> ancestros = TablaDeSimbolos.claseActual.getAncestros();
        ancestros.remove(TablaDeSimbolos.claseActual.getNombreClase());
        for(String ancestro : ancestros){
            Clase claseConcreta = TablaDeSimbolos.getClase(ancestro);
            if(encadenado != null){
                Atributo atributo = claseConcreta.getAtributo(encadenado.getToken().getLexema());
                if(atributo != null && atributo.getVisibilidad() == TokenId.kw_private){
                    throw new SemanticException("Se intento acceder a un atributo privado", encadenado.getToken());
                }
            }
        }

        Tipo tipoClaseActual = new Tipo(TablaDeSimbolos.claseActual.getNombreClase());
        if(encadenado != null){
            return encadenado.chequearThis(tipoClaseActual);
        }

        return tipoClaseActual;
    }

    @Override
    public Token getToken() {
        return nodoThis;
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
            return false;
        }else{
            return encadenado.esLlamable();
        }
    }

    @Override
    public void generar() {
        TablaDeSimbolos.gen("LOAD 3 ; Apilo la referencia al objeto actual (this)");

        if(encadenado != null){
            if(this.esLadoIzquierdo)
                encadenado.setLadoIzquierdoAsignacion();
            encadenado.generar();
        }
    }
}

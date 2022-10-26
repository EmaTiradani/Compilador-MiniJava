package TablaDeSimbolos.nodosAST.expresion.operandos;

import TablaDeSimbolos.TablaDeSimbolos;
import TablaDeSimbolos.Tipo;
import exceptions.SemanticException;
import lexycal.Token;

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
}

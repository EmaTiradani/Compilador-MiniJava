package TablaDeSimbolos.nodosAST.expresion.operandos;

import TablaDeSimbolos.Metodo;
import TablaDeSimbolos.TablaDeSimbolos;
import TablaDeSimbolos.*;
import TablaDeSimbolos.nodosAST.expresion.NodoExpresion;
import exceptions.SemanticException;
import lexycal.Token;

import java.util.List;

public class NodoAccesoMetodoEstatico extends NodoAcceso{

    protected Token idClaseEstatica;
    protected Token idMetodoEstatico;
    protected List<NodoExpresion> parametrosActuales;

    public NodoAccesoMetodoEstatico(Token idClaseEstatica, Token idMetodoEstatico, List<NodoExpresion> parametros) {
        this.idClaseEstatica = idClaseEstatica;
        this.idMetodoEstatico = idMetodoEstatico;
        this.parametrosActuales = parametros;
    }

    public Token getIdClaseEstatica() {
        return idClaseEstatica;
    }

    public void setIdClaseEstatica(Token idClaseEstatica) {
        this.idClaseEstatica = idClaseEstatica;
    }

    public Token getToken() {
        return idMetodoEstatico;
    }

    public void setIdMetodoEstatico(Token idMetodoEstatico) {
        this.idMetodoEstatico = idMetodoEstatico;
    }

    @Override
    public Tipo chequear() throws SemanticException {
        Clase claseEstatica = TablaDeSimbolos.getClase(idClaseEstatica.getLexema());
        if(claseEstatica == null){
            throw new SemanticException("La clase estatica "+idClaseEstatica.getLexema()+" no existe",idClaseEstatica);
        }

        Metodo metodo = claseEstatica.getMetodoQueConformaParametros(idMetodoEstatico, parametrosActuales);
        if(metodo == null){
            throw new SemanticException("No existe el metodo "+idMetodoEstatico.getLexema(), idMetodoEstatico);
        }else{
            if(!metodo.getEstatico()){
                throw new SemanticException("El metodo no es estatico", idMetodoEstatico);
            }
        }


        if(encadenado == null){
            return metodo.getTipoRetorno();
        }else{
            return encadenado.chequear(metodo.getTipoRetorno());
        }

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
}

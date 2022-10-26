package TablaDeSimbolos.nodosAST.expresion.operandos;

import TablaDeSimbolos.Metodo;
import TablaDeSimbolos.TablaDeSimbolos;
import TablaDeSimbolos.Tipo;
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

    public Token getIdMetodoEstatico() {
        return idMetodoEstatico;
    }

    public void setIdMetodoEstatico(Token idMetodoEstatico) {
        this.idMetodoEstatico = idMetodoEstatico;
    }

    @Override
    public Tipo chequear() throws SemanticException {
        Metodo metodo = TablaDeSimbolos.claseActual.getMetodoQueConformaParametros(idMetodoEstatico, parametrosActuales);
        if(metodo == null){
            throw new SemanticException("No existe el metodo "+idMetodoEstatico.getLexema(), idMetodoEstatico);
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

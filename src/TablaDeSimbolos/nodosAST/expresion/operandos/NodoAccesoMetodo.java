package TablaDeSimbolos.nodosAST.expresion.operandos;

import TablaDeSimbolos.Metodo;
import TablaDeSimbolos.TablaDeSimbolos;
import TablaDeSimbolos.Tipo;
import TablaDeSimbolos.nodosAST.expresion.NodoExpresion;
import exceptions.SemanticException;
import lexycal.Token;

import java.util.List;

public class NodoAccesoMetodo extends NodoAcceso{

    protected Token idMet;
    protected List<NodoExpresion> parametrosActuales;

    public NodoAccesoMetodo(Token id, List<NodoExpresion> parametrosActuales){
        this.idMet = id;
        this.parametrosActuales = parametrosActuales;
    }


    @Override
    public Tipo chequear() throws SemanticException {

        Metodo metodo = TablaDeSimbolos.claseActual.getMetodo(idMet, parametrosActuales);
        if(metodo == null){
            throw new SemanticException("No existe el metodo "+metodo.getId().getLexema(), idMet);
        }
        if(!Tabla)
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

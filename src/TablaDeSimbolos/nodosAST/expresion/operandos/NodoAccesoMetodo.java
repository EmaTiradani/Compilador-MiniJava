package TablaDeSimbolos.nodosAST.expresion.operandos;

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
        return null;
    }
}

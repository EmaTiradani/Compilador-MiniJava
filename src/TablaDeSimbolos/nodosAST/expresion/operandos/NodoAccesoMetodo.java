package TablaDeSimbolos.nodosAST.expresion.operandos;

import TablaDeSimbolos.nodosAST.expresion.NodoExpresion;
import lexycal.Token;

import java.util.List;

public class NodoAccesoMetodo extends NodoAcceso{

    protected Token idMet;
    protected List<NodoExpresion> parametrosActuales;

    public NodoAccesoMetodo(Token id, List<NodoExpresion> parametrosActuales){
        this.idMet = id;
        this.parametrosActuales = parametrosActuales;
    }


}
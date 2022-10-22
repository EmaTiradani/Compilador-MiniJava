package TablaDeSimbolos.nodosAST.expresion.operandos;

import TablaDeSimbolos.Tipo;
import TablaDeSimbolos.nodosAST.expresion.NodoExpresion;
import exceptions.SemanticException;

public class NodoAccesoExpresionParentizada extends NodoAcceso{

    NodoExpresion expresion;

    public NodoAccesoExpresionParentizada(NodoExpresion expresion) {
        this.expresion = expresion;
    }

    @Override
    public Tipo chequear() throws SemanticException {
        if(encadenado == null){
            return expresion.chequear();
        }else{
            return encadenado.chequear(expresion.chequear());
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
            return false;
        }else{
            return encadenado.esLlamable();
        }
    }
}

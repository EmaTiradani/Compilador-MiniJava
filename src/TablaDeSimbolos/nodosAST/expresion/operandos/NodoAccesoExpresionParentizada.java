package TablaDeSimbolos.nodosAST.expresion.operandos;

import TablaDeSimbolos.nodosAST.expresion.NodoExpresion;

public class NodoAccesoExpresionParentizada extends NodoAcceso{

    NodoExpresion expresion;

    public NodoAccesoExpresionParentizada(NodoExpresion expresion) {
        this.expresion = expresion;
    }
}

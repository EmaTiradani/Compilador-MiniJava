package TablaDeSimbolos.nodosAST.expresion.operandos;

import lexycal.Token;

public class NodoAccesoConstructor extends NodoAcceso{

    Token idClase;


    public NodoAccesoConstructor(Token idClase) {
        this.idClase = idClase;
    }
}

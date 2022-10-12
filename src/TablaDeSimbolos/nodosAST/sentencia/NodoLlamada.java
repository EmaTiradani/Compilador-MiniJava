package TablaDeSimbolos.nodosAST.sentencia;

import TablaDeSimbolos.nodosAST.expresion.operandos.NodoAcceso;
import lexycal.Token;

public class NodoLlamada extends NodoSentencia{

    Token tokenLlamada;
    NodoAcceso acceso;

    @Override
    public void chequear() {

    }
}

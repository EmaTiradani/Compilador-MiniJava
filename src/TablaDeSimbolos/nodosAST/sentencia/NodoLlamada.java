package TablaDeSimbolos.nodosAST.sentencia;

import TablaDeSimbolos.nodosAST.expresion.operandos.NodoAcceso;
import lexycal.Token;

public class NodoLlamada extends NodoSentencia{

    Token tokenLlamada;
    NodoAcceso acceso;

    public NodoLlamada(Token tokenLlamada, NodoAcceso acceso) {
        this.tokenLlamada = tokenLlamada;
        this.acceso = acceso;
    }

    @Override
    public void chequear() {

    }

    public Token getTokenLlamada() {
        return tokenLlamada;
    }

    public void setTokenLlamada(Token tokenLlamada) {
        this.tokenLlamada = tokenLlamada;
    }

    public NodoAcceso getAcceso() {
        return acceso;
    }

    public void setAcceso(NodoAcceso acceso) {
        this.acceso = acceso;
    }
}

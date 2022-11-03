package TablaDeSimbolos.nodosAST.sentencia;

import TablaDeSimbolos.nodosAST.expresion.operandos.NodoAcceso;
import exceptions.SemanticException;
import lexycal.Token;

public class NodoLlamada extends NodoSentencia{

    Token tokenLlamada;
    NodoAcceso acceso;

    public NodoLlamada(Token tokenLlamada, NodoAcceso acceso) {
        this.tokenLlamada = tokenLlamada;
        this.acceso = acceso;
    }

    @Override
    public void chequear() throws SemanticException {
        acceso.chequear();
        if(!acceso.esLlamable()){
            throw new SemanticException("el acceso no es llamable", tokenLlamada);
        }
    }

    @Override
    public void generar() {
        acceso.generar();
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

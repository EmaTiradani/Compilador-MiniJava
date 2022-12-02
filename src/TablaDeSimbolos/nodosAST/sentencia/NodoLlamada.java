package TablaDeSimbolos.nodosAST.sentencia;

import TablaDeSimbolos.TablaDeSimbolos;
import TablaDeSimbolos.Tipo;
import TablaDeSimbolos.nodosAST.expresion.operandos.NodoAcceso;
import exceptions.SemanticException;
import lexycal.Token;

public class NodoLlamada extends NodoSentencia{

    Token tokenLlamada;
    NodoAcceso acceso;

    Tipo tipoLlamada;

    public NodoLlamada(Token tokenLlamada, NodoAcceso acceso) {
        this.tokenLlamada = tokenLlamada;
        this.acceso = acceso;
    }

    @Override
    public void chequear() throws SemanticException {
        this.tipoLlamada = acceso.chequear();
        if(!acceso.esLlamable()){
            throw new SemanticException("el acceso no es llamable", tokenLlamada);
        }
    }

    @Override
    public void generar() {
        acceso.generar();

        if(!tipoLlamada.mismoTipo(new Tipo("void"))){
            //Hago un pop de la ultima instruccion si no se guarda en ningun lado, sino explota el stack
            TablaDeSimbolos.gen("POP");
        }
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

package TablaDeSimbolos.nodosAST.expresion.operandos;

import TablaDeSimbolos.TablaDeSimbolos;
import TablaDeSimbolos.Tipo;
import TablaDeSimbolos.nodosAST.sentencia.NodoVarLocal;
import exceptions.SemanticException;
import lexycal.Token;

public class NodoAccesoVar extends NodoAcceso{

    Token idVar;

    public NodoAccesoVar(Token idVar) {
        this.idVar = idVar;
    }

    @Override
    public Tipo chequear() throws SemanticException {
        Tipo tipoVar;
        NodoVarLocal nodoVarLocal = TablaDeSimbolos.getVarLocalClaseActual(idVar.getLexema());

    }

    @Override
    public boolean esAsignable() {
        if(encadenado == null){
            return true;
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

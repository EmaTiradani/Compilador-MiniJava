package TablaDeSimbolos.nodosAST.expresion.operandos;

import TablaDeSimbolos.nodosAST.expresion.NodoExpresion;
import TablaDeSimbolos.nodosAST.expresion.operandos.NodoEncadenado;
import lexycal.Token;

import java.util.ArrayList;
import java.util.List;

public class NodoMetodoEncadenado extends NodoEncadenado {

    protected List<NodoExpresion> parametros;

    public NodoMetodoEncadenado(Token tokenNodoEncadenado) {
        super(tokenNodoEncadenado);
        parametros = new ArrayList<>();
    }


    public List<NodoExpresion> getParametros() {
        return parametros;
    }

    public void setParametros(List<NodoExpresion> parametros) {
        this.parametros = parametros;
    }
}

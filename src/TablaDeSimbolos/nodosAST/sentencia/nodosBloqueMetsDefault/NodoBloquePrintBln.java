package TablaDeSimbolos.nodosAST.sentencia.nodosBloqueMetsDefault;

import TablaDeSimbolos.TablaDeSimbolos;
import TablaDeSimbolos.nodosAST.sentencia.NodoBloque;

public class NodoBloquePrintBln extends NodoBloque {
    public void generar(){
        TablaDeSimbolos.gen("LOAD 3");
        TablaDeSimbolos.gen("BPRINT");
        TablaDeSimbolos.gen("PRNLN");
    }
}

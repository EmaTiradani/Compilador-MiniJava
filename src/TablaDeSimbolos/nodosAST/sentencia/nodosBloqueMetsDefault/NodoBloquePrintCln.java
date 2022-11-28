package TablaDeSimbolos.nodosAST.sentencia.nodosBloqueMetsDefault;

import TablaDeSimbolos.TablaDeSimbolos;
import TablaDeSimbolos.nodosAST.sentencia.NodoBloque;

public class NodoBloquePrintCln extends NodoBloque {

    public void generar(){

        TablaDeSimbolos.gen("LOAD 3");
        TablaDeSimbolos.gen("CPRINT");
        TablaDeSimbolos.gen("PRNLN");

/*        TablaDeSimbolos.gen("STOREFP");
        TablaDeSimbolos.gen("RET " + 1 + " ; Retorno del metodo");*/
    }
}

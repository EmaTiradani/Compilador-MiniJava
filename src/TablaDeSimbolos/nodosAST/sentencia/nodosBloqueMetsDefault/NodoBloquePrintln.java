package TablaDeSimbolos.nodosAST.sentencia.nodosBloqueMetsDefault;

import TablaDeSimbolos.TablaDeSimbolos;
import TablaDeSimbolos.nodosAST.sentencia.NodoBloque;

public class NodoBloquePrintln extends NodoBloque {

    public void generar(){

        //TablaDeSimbolos.gen("LOAD 3");
        TablaDeSimbolos.gen("PRNLN");

        /*TablaDeSimbolos.gen("STOREFP");
        TablaDeSimbolos.gen("RET " + 0 + " ; Retorno del metodo printLn (no tiene parametros)");*/
    }
}

package TablaDeSimbolos.nodosAST.sentencia.nodosBloqueMetsDefault;

import TablaDeSimbolos.TablaDeSimbolos;
import TablaDeSimbolos.nodosAST.sentencia.NodoBloque;

public class NodoBloqueRead extends NodoBloque {

    public void generar(){
        TablaDeSimbolos.gen("LOADFP");
        TablaDeSimbolos.gen("LOADSP");
        TablaDeSimbolos.gen("STOREFP");

        TablaDeSimbolos.gen("LOAD 3");
        TablaDeSimbolos.gen("READ"); //TODO anda?

        TablaDeSimbolos.gen("STOREFP");
        TablaDeSimbolos.gen("RET " + 0 + " ; Retorno del metodo read");
    }
}

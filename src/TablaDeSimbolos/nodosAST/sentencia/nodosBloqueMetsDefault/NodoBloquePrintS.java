package TablaDeSimbolos.nodosAST.sentencia.nodosBloqueMetsDefault;

import TablaDeSimbolos.TablaDeSimbolos;
import TablaDeSimbolos.nodosAST.sentencia.NodoBloque;

public class NodoBloquePrintS extends NodoBloque {

    public void generar(){
        TablaDeSimbolos.gen("LOAD 3");
        TablaDeSimbolos.gen("SPRINT");
    }
}

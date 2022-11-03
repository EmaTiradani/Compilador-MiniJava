package TablaDeSimbolos.nodosAST.sentencia;

import TablaDeSimbolos.TablaDeSimbolos;

public class NodoBloqueDebugPrint extends NodoBloque{

    public void generar(){
        TablaDeSimbolos.gen("LOAD 3");
        TablaDeSimbolos.gen("IPRINT");
    }
}

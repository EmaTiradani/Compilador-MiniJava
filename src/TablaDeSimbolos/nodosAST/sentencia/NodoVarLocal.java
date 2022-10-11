package TablaDeSimbolos.nodosAST.sentencia;

import TablaDeSimbolos.nodosAST.expresion.NodoOperando;
import lexycal.Token;

public class NodoVarLocal extends NodoOperando {
    Token nombre;

    public NodoVarLocal(Token nombre) {
        this.nombre = nombre;
    }

    public Token getNombre() {
        return nombre;
    }

    public void setNombre(Token nombre) {
        this.nombre = nombre;
    }
}

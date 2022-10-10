package semantic;

import lexycal.Token;

public class NodoVar extends NodoOperando{
    Token nombre;

    public NodoVar(Token nombre) {
        this.nombre = nombre;
    }

    public Token getNombre() {
        return nombre;
    }

    public void setNombre(Token nombre) {
        this.nombre = nombre;
    }
}

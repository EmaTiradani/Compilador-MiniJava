package TablaDeSimbolos.nodosAST.sentencia;

import TablaDeSimbolos.TablaDeSimbolos;
import TablaDeSimbolos.Tipo;
import TablaDeSimbolos.nodosAST.expresion.NodoExpresion;
import exceptions.SemanticException;
import lexycal.Token;

public class NodoVarLocal extends NodoSentencia {


    Token nombre;
    NodoExpresion expresion;
    Tipo tipo;

    public NodoVarLocal(Token nombre) {
        this.nombre = nombre;
    }

    public Token getNombre() {
        return nombre;
    }

    public void setNombre(Token nombre) {
        this.nombre = nombre;
    }

    @Override
    public void chequear() throws SemanticException {
        tipo.checkExistencia(nombre.getLinea());
        TablaDeSimbolos.getBloqueActual().insertarVariableLocal(this);// Lanza error si ya hay otra variable con este mismo nombre

        if(expresion != null){
            if(!tipo.checkSubtipo(expresion.chequear())){
                throw new SemanticException(" la expresion no es de un tipo compatible con la variable", nombre);
            }
        }

    }

    public NodoExpresion getExpresion() {
        return expresion;
    }

    public void setExpresion(NodoExpresion expresion) {
        this.expresion = expresion;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }
}

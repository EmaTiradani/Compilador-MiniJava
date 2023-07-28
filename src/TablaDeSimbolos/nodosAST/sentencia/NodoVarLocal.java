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
    int offset;

    public NodoVarLocal(Token nombre) {
        this.nombre = nombre;
        offset = 1;
    }

    public Token getNombre() {
        return nombre;
    }

    public void setNombre(Token nombre) {
        this.nombre = nombre;
    }

    @Override
    public void chequear() throws SemanticException {

        TablaDeSimbolos.getBloqueActual().insertarVariableLocal(this);// Lanza error si ya hay otra variable con este mismo nombre

        if(expresion != null){
            this.tipo = expresion.chequear();
        }
        if(this.tipo == null || this.tipo.mismoTipo(new Tipo("null"))){
            throw new SemanticException(" No se le puede asginar un nulo a una variable local", expresion.getToken());
        }
    }

    @Override
    public void generar() {
        TablaDeSimbolos.gen("RMEM 1 ; Reserva memoria para la variable local "+nombre.getLexema());
        if(expresion != null){
            expresion.generar();
            TablaDeSimbolos.gen("STORE "+offset+" ; Almacena  el valor de la expresion del tope de la pila en la variable local");
        }
    }

    @Override
    public boolean isReturn() {
        return false;
    }

    public void setOffset(int offset){
        this.offset = offset;
    }

    public int getOffset(){
        return offset;
    }

    public NodoExpresion getExpresion() {
        return expresion;
    }

    public void setExpresion(NodoExpresion expresion){
        this.expresion = expresion;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }
}

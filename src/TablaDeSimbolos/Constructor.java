package TablaDeSimbolos;

import exceptions.SemanticException;
import lexycal.Token;


import java.util.ArrayList;

public class Constructor extends Metodo{

    //private Token token;
    private ArrayList<Argumento> argumentos;


    public Constructor(Token nombreClase) {
        super(nombreClase, new TipoMetodo("void"), false, null);
        //this.token = nombreClase;
        argumentos = new ArrayList<Argumento>();
    }

    public void addArguments(ArrayList<Argumento> args){
        this.argumentos = args;
    }

    public ArrayList<Argumento> getArguments(){
        return argumentos;
    }

    /*public Token getToken(){
        return token;
    }*/


    public void checkDec() throws SemanticException {
        for(Argumento arg : argumentos){
            arg.checkDec();
        }
    }

    public void generar() {
        TablaDeSimbolos.metodoActual = this;

        TablaDeSimbolos.gen("LOADFP ; Apila el ED al comienzo del RA del llamador");
        TablaDeSimbolos.gen("LOADSP ; Apila el puntero al comienzo del RA del llamado");
        TablaDeSimbolos.gen("STOREFP ; Actualiza el FP para que apunte al comienzo del RA del llamado");
        body.generar();
        TablaDeSimbolos.gen("STOREFP");
        TablaDeSimbolos.gen("RET "+ offsetParametros+1 + " ; Libero la memoria de los parametros y el this");
    }
}

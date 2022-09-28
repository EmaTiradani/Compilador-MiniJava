package TablaDeSimbolos;

import exceptions.SemanticException;
import lexycal.Token;


import java.util.ArrayList;

public class Constructor {

    private Token token;
    private ArrayList<Argumento> argumentos;


    public Constructor(Token nombreClase) {
        this.token = nombreClase;
    }

    public void addArguments(ArrayList<Argumento> args){
        this.argumentos = args;
    }

    public ArrayList<Argumento> getArguments(){
        return argumentos;
    }

    public Token getToken(){
        return token;
    }


    public void checkDec() throws SemanticException {
        for(Argumento arg : argumentos){
            arg.checkDec();
        }
    }
}

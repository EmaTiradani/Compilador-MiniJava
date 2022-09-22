package TablaDeSimbolos;

import lexycal.Token;
import lexycal.TokenId;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Metodo {

    private Token idMet;
    private TokenId tipoRetorno;
    private ArrayList<Token> atributos;


    public Metodo(Token idMet, TokenId tipoRetorno, ArrayList<Token> atributos){
        this.idMet = idMet;
        this.tipoRetorno = tipoRetorno;
        if(atributos.size() == 0)
            this.atributos = new ArrayList<>();
        else
            this.atributos = atributos;
    }

    public Token getId(){
        return idMet;
    }

    public TokenId getTipoRetorno(){
        return tipoRetorno;
    }

    public ArrayList<Token> getAtributos(){
        return atributos;
    }
}

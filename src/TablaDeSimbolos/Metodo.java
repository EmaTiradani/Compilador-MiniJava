package TablaDeSimbolos;

import lexycal.Token;

import java.util.ArrayList;

public class Metodo {

    private Token idMet;
    private Tipo tipoRetorno;
    private ArrayList<Argumento> argumentos;
    private boolean estatico;


    public Metodo(Token idMet, Tipo tipoRetorno, boolean estatico, ArrayList<Argumento> argumentos){
        this.idMet = idMet;
        this.tipoRetorno = tipoRetorno;
        this.estatico = estatico;
        if(argumentos == null)
            this.argumentos = new ArrayList<>();
        else
            this.argumentos = argumentos;
    }

    public Token getId(){
        return idMet;
    }

    public Tipo getTipoRetorno(){
        return tipoRetorno;
    }

    public ArrayList<Argumento> getAtributos(){
        return argumentos;
    }

    public void addArgumento(Argumento argumento){
        argumentos.add(argumento);
    }

    public boolean getEstatico(){
        return estatico;
    }

    public void print(){
        System.out.println("Soy un metodo");
        argumentos.forEach(argumento -> argumento.print());
    }
}

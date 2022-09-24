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

    public ArrayList<Argumento> getArgumentos(){
        return argumentos;
    }

    public void addArgumento(Argumento argumento){
        argumentos.add(argumento);
    }

    public boolean getEstatico(){
        return estatico;
    }

    public boolean soloCambiaTipoRetorno(Metodo metodo){ //Comparo todos los atribs. menos el ID, que se supone que es el mismo
        return (!metodo.getTipoRetorno().getType().equals(tipoRetorno.getType())
                && compararListaArgumentos(metodo.getArgumentos())
                && metodo.getEstatico()==this.estatico);
    }

    private boolean compararListaArgumentos(ArrayList<Argumento> argumentos){
        boolean sonIguales = true;
        if(argumentos.size() != this.argumentos.size())
            sonIguales = false;
        for(int i=0; i<argumentos.size(); i++){
            if(!compararArgumentos(argumentos.get(i), this.argumentos.get(i)))
                sonIguales = false;
        }
        return sonIguales;
    }

    private boolean compararArgumentos(Argumento argumento1, Argumento argumento2){ //TODO No tendria que comprar el id, no?
        return(/*argumento1.getIdVar().getLexema().equals(argumento2.getIdVar().getLexema())
                && */argumento1.getTipoParametro().getType().equals(argumento2.getTipoParametro().getType()));
    }

}

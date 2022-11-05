package TablaDeSimbolos;

import TablaDeSimbolos.nodosAST.sentencia.NodoBloque;
import exceptions.SemanticException;
import lexycal.Token;

import java.util.ArrayList;
import java.util.List;

public class Metodo {

    private Token idMet;
    private TipoMetodo tipoRetorno;
    private ArrayList<Argumento> argumentos;
    private boolean estatico;
    protected NodoBloque body;
    protected String claseContenedora;

    public Metodo(Token idMet, TipoMetodo tipoRetorno, boolean estatico, ArrayList<Argumento> argumentos){
        this.idMet = idMet;
        this.tipoRetorno = tipoRetorno;
        this.estatico = estatico;
        body = new NodoBloque(); // Sino explota con Object y System
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

    public Argumento getArgumento(String idArg){
        for(Argumento argumento : argumentos){
            if(argumento.getIdVar().getLexema().equals(idArg)){
                return argumento;
            }
        }
        return null;
    }

    public void insertClaseContenedora(String claseContenedora){
        this.claseContenedora = claseContenedora;
    }

    public String getClaseContenedora(){
        return claseContenedora;
    }

    public void addArgumento(Argumento argumento){
        argumentos.add(argumento);
    }

    public boolean getEstatico(){
        return estatico;
    }

    public void insertarBloque(NodoBloque body){
        this.body = body;
    }

    public void checkDec() throws SemanticException {
        tipoRetorno.checkExistencia(idMet.getLinea());
        for(Argumento argumento : argumentos){
            argumento.checkDec();
        }
        checkRepeatedArguments();
    }

    private void checkRepeatedArguments() throws SemanticException {

        for(Argumento argumento : argumentos){
            int contador = 0;
            for(Argumento argumento2 : argumentos){
                if(argumento.getIdVar().getLexema().equals(argumento2.getIdVar().getLexema())){
                    contador++;
                }
                if(contador>1)
                    throw new SemanticException(" El parametro "+argumento.getIdVar().getLexema()+" esta repetido. Metodo: "+idMet.getLexema() , argumento.getIdVar());
            }
        }
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
        else {
            for (int i = 0; i < argumentos.size(); i++) {
                if (!compararArgumentos(argumentos.get(i), this.argumentos.get(i)))
                    sonIguales = false;
            }
        }
        return sonIguales;
    }

    private boolean compararArgumentos(Argumento argumento1, Argumento argumento2){ //TODO No tendria que comprar el id, no?
        return(argumento1.getTipoParametro().getType().equals(argumento2.getTipoParametro().getType()));
    }

    public boolean coincideEncabezado(Metodo metodo){ // Chequea si coinciden encabezados VALIDOS (no dos exactamente iguales, eso no es valido)
        return(metodo.getId().getLexema().equals(idMet.getLexema())
                &&  metodo.compararListaArgumentos(argumentos));
    }

    public boolean mismoEncabezado(Metodo metodo){
        return(metodo.getId().getLexema().equals(idMet.getLexema())
                && metodo.compararListaArgumentos(argumentos)
                && metodo.getTipoRetorno().getType().equals(tipoRetorno.getType()));
    }

    public boolean soloCambiaEstatico(Metodo metodo){

        return(mismoEncabezado(metodo) && coincideEncabezado(metodo) && estatico!=metodo.getEstatico());
    }

    public boolean conformanParametros(List<Tipo> parametros){
        if(parametros.size() == argumentos.size()){
            for(int i=0; i<parametros.size(); i++){
                if(!argumentos.get(i).getTipoParametro().checkSubtipo(parametros.get(parametros.size()-i-1))){
                    return false;
                }
            }
        }else{
            return false;
        }
        return true;
    }

    public boolean esMain() {
        if(estatico && idMet.getLexema().equals("main") && argumentos.size() == 0 && tipoRetorno.getType().equals("void"))
            return true;
        else
            return false;
    }


    public void checkSentencias() throws SemanticException {
        TablaDeSimbolos.metodoActual = this;
        body.chequear();
    }

    public void  generar(){
        TablaDeSimbolos.metodoActual = this;
        TablaDeSimbolos.gen(idMet.getLexema()+": LOADFP"); // mas de 1 metodo, poner un if
        TablaDeSimbolos.gen("LOADSP");
        TablaDeSimbolos.gen("STOREFP");
        body.generar();
        TablaDeSimbolos.gen("STOREFP");
        TablaDeSimbolos.gen("RET "+argumentos.size()); // Si el metodo es dynamic es +1
    }
}

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
    protected int offset;
    protected int offsetParametros;
    protected ArrayList<Clase> clasesEnConflicto;
    private boolean conflictoSolucionado;

    public Metodo(Token idMet, TipoMetodo tipoRetorno, boolean estatico, ArrayList<Argumento> argumentos){
        this.idMet = idMet;
        this.tipoRetorno = tipoRetorno;
        this.estatico = estatico;
        body = new NodoBloque(); // Sino explota con Object y System
        if(argumentos == null)
            this.argumentos = new ArrayList<>();
        else
            this.argumentos = argumentos;

        offset = -1;
        offsetParametros = 0;
        conflictoSolucionado = false;
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

    public void insertOffsetEnClase(int offset){
        this.offset = offset;
    }

    public int getOffsetEnClase(){
        return offset;
    }

    public boolean getConflictoSolucionado(){
        return conflictoSolucionado;
    }

    public void setConflictoSolucionado(){
        conflictoSolucionado = true;
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
        return estatico && idMet.getLexema().equals("main") && argumentos.size() == 0 && tipoRetorno.getType().equals("void");
    }

    public void insertClaseQueDefine(Clase clase){
        clasesEnConflicto.add(clase);
    }

    public ArrayList<Clase> getClasesEnConflicto(){
        return clasesEnConflicto;
    }

    public int getOffsetConflictos(){// Recorre las clases en conflicto, y retorna mayor cantidad de metodos sin conflictos que haya en una clase
        int offsetConflictos = 0;
        for(Clase clase : clasesEnConflicto){
            if(clase.getCantMetodosSinConflictos()>offsetConflictos)
                offsetConflictos = clase.getCantMetodosSinConflictos();
        }
        return offsetConflictos;
    }


    public void checkSentencias() throws SemanticException {
        TablaDeSimbolos.metodoActual = this;
        body.chequear();
    }

    public void  generar(){
        if(estatico){ // El offset arranca en 3, no tiene this
            offsetParametros = 3;
        }else{ // El offset arranca en 4 si tiene this
            offsetParametros = 4;
        }
        for(int i=0; i<argumentos.size(); i++){
            argumentos.get(argumentos.size()-i-1).setOffset(offsetParametros);
            offsetParametros++;
        }

        TablaDeSimbolos.metodoActual = this;
        if(idMet.getLexema().equals("main")){
            TablaDeSimbolos.gen(idMet.getLexema() + ":"); // mas de 1 metodo, poner un if
        }else
            TablaDeSimbolos.gen(idMet.getLexema()+claseContenedora + ":"); // mas de 1 metodo, poner un if
        TablaDeSimbolos.gen("LOADFP");
        TablaDeSimbolos.gen("LOADSP");
        TablaDeSimbolos.gen("STOREFP");
        body.generar();
        TablaDeSimbolos.gen("STOREFP");
        if(estatico){
            TablaDeSimbolos.gen("RET "+argumentos.size());
        }else{
            TablaDeSimbolos.gen("RET "+(argumentos.size()+1)); // Si el metodo es dynamic es +1 por el this
        }
        TablaDeSimbolos.gen("");
    }
}

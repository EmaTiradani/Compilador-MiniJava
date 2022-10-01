package TablaDeSimbolos;

import exceptions.SemanticException;
import lexycal.Token;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static lexycal.TokenId.idClase;
import static lexycal.TokenId.idMetVar;

public class Interfaz extends Clase{

    private Token nombreInterface;
    private ArrayList<String> clasesQueExtiende;
    //private HashMap<String, ArrayList<Metodo>>  metodos;

    boolean consolidado, notHerenciaCircular;


    public Interfaz(Token nombreInterface){
        this.nombreInterface = nombreInterface;
        listaInterfaces = new ArrayList<>();
        metodos = new HashMap<>();

        consolidado = false;
        notHerenciaCircular = false;
    }

    public Token getToken(){
        return nombreInterface;
    }

    @Override
    public String getNombreClase() {
        return nombreInterface.getLexema();
    }

    public boolean herenciaCircular(){ return notHerenciaCircular;}

    public void insertarAtributo(Atributo atributo) throws SemanticException {
        // Aca no deberia entrar nunca
    }

    @Override
    public void insertarMetodo(Metodo metodo) throws SemanticException {
        if(!metodo.getEstatico()) {
            if (metodos.containsKey(metodo.getId().getLexema())) {
                boolean puedeSerInsertado = false;
                for (Metodo met : metodos.get(metodo.getId().getLexema())) {
                    if (met.soloCambiaTipoRetorno(metodo)) {// Java no soporta sobrecarga dep. del contexto si pasa eso, error
                        throw new SemanticException("esta mal redefinido", metodo.getId());
                    } else {
                        puedeSerInsertado = true;
                    }
                }
                if (puedeSerInsertado) {
                    metodos.get(metodo.getId().getLexema()).add(metodo);
                }
                //throw new SemanticException("esta mal redefinido", metodo.getId());
            } else {
                ArrayList<Metodo> listaMetodos = new ArrayList<Metodo>();
                listaMetodos.add(metodo);
                metodos.put(metodo.getId().getLexema(), listaMetodos);
            }
        }else{
            throw new SemanticException("Metodo estatico en una interfaz", metodo.getId());
        }
    }

    public boolean estaBienDeclarada() throws SemanticException{
        if(!nombreInterface.getLexema().equals("Object")){
            //checkHerenciaExplicitaDeclarada();
            //checkConstructoresBienDeclarados();
            checkExtends();
            checkHerenciaCircular(new ArrayList<Token>());
            checkMetodosBienDeclarados();
        }
        return false;
    }

    public void checkExtends() throws SemanticException {
        for(String interfaceAncestra : listaInterfaces){
            if(!TablaDeSimbolos.existeInterfaz(interfaceAncestra)){
                throw new SemanticException("no esta declarada", new Token(idClase, interfaceAncestra, nombreInterface.getLinea()));
            }
        }

    }
    /*private void checkHerenciaExplicitaDeclarada() throws SemanticException {
        if(!TablaDeSimbolos.existeClase(nombreClasePadre)){
            throw new SemanticException("no esta declarada", new Token(idClase, nombreClasePadre, nombreClase.getLinea()));
        }
    }*/

    /*private void checkConstructoresBienDeclarados() throws SemanticException {
        for(Constructor constructor : constructores){
            constructor.checkDec();
        }
    }*/

    public void checkHerenciaCircular(ArrayList<Token> listaClases) throws SemanticException {
        listaClases.add(nombreInterface);

        for(String interfaceAncestra : listaInterfaces) {
            TablaDeSimbolos.getInterfaz(interfaceAncestra).checkExtends();// mmmm TODO

            if (!TablaDeSimbolos.getInterfaz(interfaceAncestra).herenciaCircular()) {
                if (listaClases.contains(TablaDeSimbolos.getInterfaz(interfaceAncestra).getToken())) {
                    throw new SemanticException(" Hay herencia circular", new Token(idMetVar, interfaceAncestra, nombreInterface.getLinea()));
                }
                TablaDeSimbolos.getInterfaz(interfaceAncestra).checkHerenciaCircular(listaClases);
            }
        }
        listaClases.remove(nombreInterface);
    }

    private void checkMetodosBienDeclarados() throws SemanticException {
        for(Map.Entry<String,ArrayList<Metodo>> listaMetodos : metodos.entrySet()){
            for(Metodo metodo : listaMetodos.getValue()){
                metodo.checkDec();
            }
        }
    }


    @Override
    public void consolidar() throws SemanticException {
        for(String interfaceAncestra : listaInterfaces) {
            Interfaz ancestro = TablaDeSimbolos.getInterfaz(interfaceAncestra);
            ancestro.checkExtends();// mmmm TODO
            if(ancestro.consolidado){
                importarMetodosDePadre(ancestro);
                consolidado = true;
            }else{
                ancestro.consolidar();
                importarMetodosDePadre(ancestro);
                consolidado = true;
            }
        }
    }

    private void importarMetodosDePadre(Interfaz interfaceAncestra) throws SemanticException {
        HashMap<String, ArrayList<Metodo>> metodosAncestro = interfaceAncestra.metodos;
        for(Map.Entry<String, ArrayList<Metodo>> listaMetodos : metodosAncestro.entrySet()){
            for(Metodo metodo : listaMetodos.getValue()){
                insertarMetodo(metodo);
            }
        }
    }
    private void checkEncabezadosBienDeclarados(){

    }

    public void insertarAncestro(Interfaz interfaz){
        listaInterfaces.add(interfaz.getNombreClase());
    }

    public void print(){
        for(Map.Entry<String, ArrayList<Metodo>> listaMetodos : metodos.entrySet()){
            for(Metodo metodo : listaMetodos.getValue()){
                System.out.println("\nNombre de metodo: " + metodo.getId().getLexema());
            }
        }
    }

}

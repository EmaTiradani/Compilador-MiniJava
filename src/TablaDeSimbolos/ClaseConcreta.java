package TablaDeSimbolos;

import exceptions.SemanticException;
import lexycal.Token;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static lexycal.TokenId.idClase;

public class ClaseConcreta extends Clase{


    private Token nombreClase;
    private String nombreClasePadre;
    //public ArrayList<String> implemented;

    private HashMap<String, Atributo> atributos;
    private HashMap<String, ArrayList<Metodo>> metodos;

    boolean consolidado;
    boolean notHerenciaCircular;

    private Metodo metodoMain = null;

    ArrayList<Constructor> constructores;

    public ClaseConcreta(Token nombreClase){
        this.nombreClase = nombreClase;
        this.nombreClasePadre = "Object";
        atributos = new HashMap<>();
        listaInterfaces = new ArrayList<>();
        metodos = new HashMap<>();
        constructores = new ArrayList<>();

        consolidado = false;
        notHerenciaCircular = false;

        Constructor constructor = new Constructor(nombreClase);
        constructores.add(constructor);

        /*if(nombreClasePadre.equals("Object")){
            consolidado = true;
            herenciaCircular = true;
            //herencia circular entra en consolidado? O chequeo por separado?
        }*/
    }

    public String getNombreClase(){
        return nombreClase.getLexema();
    }

    public Token getToken(){ return nombreClase;}

    public ArrayList<Constructor> getConstructores(){
        return constructores;
    }

    public boolean herenciaCircular(){
        return notHerenciaCircular;
    }

    public void noTieneHerenciaCircular(){
        notHerenciaCircular = true;
    }

    public void insertarAtributo(Atributo atributo) throws SemanticException {
        if(!atributos.containsKey(atributo.getId())){
            atributos.put(atributo.getId(), atributo);
        }else{
            Atributo atributoRepetido = atributos.get(atributo.getId());
            throw new SemanticException("Atributo con el mismo ID", atributoRepetido.getToken());
        }
    }

    public void insertarMetodo(Metodo metodo) throws SemanticException {

        if(metodos.containsKey(metodo.getId().getLexema())){
            boolean puedeSerInsertado = false;
            for(Metodo met : metodos.get(metodo.getId().getLexema())){
                if(met.soloCambiaTipoRetorno(metodo)){// Java no soporta sobrecarga dep. del contexto si pasa eso, error
                    throw new SemanticException("esta mal redefinido", met.getId());
                }else{
                    /*ArrayList<Metodo> mets = metodos.get(metodo.getId().getLexema());
                    mets.add(metodo);*/
                    puedeSerInsertado = true;
                }
            }
            if(puedeSerInsertado){
                metodos.get(metodo.getId().getLexema()).add(metodo);
            }
            //throw new SemanticException("esta mal redefinido", metodo.getId());
        }else{
            ArrayList<Metodo> listaMetodos = new ArrayList<Metodo>();
            listaMetodos.add(metodo);
            metodos.put(metodo.getId().getLexema(), listaMetodos);
        }
        if(metodo.esMain())
            metodoMain = metodo;
    }

    public void insertarPadre(String nombreClasePadre) throws SemanticException {
        this.nombreClasePadre = nombreClasePadre;
    }

    public HashMap<String, Atributo> getAtributos(){
        return atributos;
    }

    public HashMap<String,ArrayList<Metodo>> getMetodos(){
        return metodos;
    }

    public String getNombreClasePadre(){
        return nombreClasePadre;
    }

    public boolean estaBienDeclarada() throws SemanticException {
        boolean tengoMain = false;
        if(!nombreClase.getLexema().equals("Object")){
            checkHerenciaExplicitaDeclarada();
            checkConstructoresBienDeclarados();
            checkHerenciaCircular(new ArrayList<String>());
            checkMetodosBienDeclarados();
            checkAtributosBienDeclarados();
            tengoMain = checkMain();
        }
        return tengoMain;
    }

    public void consolidar() throws SemanticException {
        if(TablaDeSimbolos.getClase(nombreClasePadre).consolidado){
            insertarMetodoYAtributosDePadre();
            consolidado = true;
        }else{
            TablaDeSimbolos.getClase(nombreClasePadre).consolidar();
            consolidar();
        }
        checkImplementaTodosLosMetodosDeSuInterfaz();
    }

    private void insertarMetodoYAtributosDePadre() throws SemanticException {
        // Inserto los atributos del padre
        HashMap<String,ArrayList<Metodo>> metodosClasePadre = TablaDeSimbolos.getClase(nombreClasePadre).getMetodos();
        for(Map.Entry<String, ArrayList<Metodo>> listaMetodos : metodosClasePadre.entrySet()){
            for(Metodo metodo : listaMetodos.getValue()){
                insertarMetodo(metodo);
            }
        }
        // Inserto los atributos del padre
        HashMap<String,Atributo> atributosClasePadre = TablaDeSimbolos.getClase(nombreClasePadre).getAtributos();
        for(Map.Entry<String, Atributo> atributo : atributosClasePadre.entrySet()){
            insertarAtributo(atributo.getValue());
        }
    }

    private boolean checkMain(){
        return (!(metodoMain == null));
    }

    private void checkHerenciaExplicitaDeclarada() throws SemanticException {
        if(!TablaDeSimbolos.existeClase(nombreClasePadre)){
            throw new SemanticException("no esta declarada", new Token(idClase, nombreClasePadre, nombreClase.getLinea()));
        }
    }

    private void checkConstructoresBienDeclarados() throws SemanticException {
        for(Constructor constructor : constructores){
            constructor.checkDec();
        }
    }

    private void checkHerenciaCircular(ArrayList<String> listaClases) throws SemanticException {
        listaClases.add(nombreClase.getLexema());
        TablaDeSimbolos.getClase(nombreClasePadre).checkHerenciaExplicitaDeclarada();// mmmm TODO
        if (!TablaDeSimbolos.getClase(nombreClasePadre).herenciaCircular()) {
            if (listaClases.contains(nombreClasePadre)) {
                throw new SemanticException(" Hay herencia circular", TablaDeSimbolos.getClase(nombreClasePadre).getToken());
            }
            TablaDeSimbolos.getClase(nombreClasePadre).checkHerenciaCircular(listaClases);
        }
    }

    private void checkMetodosBienDeclarados() throws SemanticException {
        for(Map.Entry<String,ArrayList<Metodo>> listaMetodos : metodos.entrySet()){
            for(Metodo metodo : listaMetodos.getValue()){
                metodo.checkDec();
            }
        }
    }

    private void checkImplementaTodosLosMetodosDeSuInterfaz() throws SemanticException {
        for(String interfaceQueImplementa : listaInterfaces){
            Interfaz interfaceAncestra = TablaDeSimbolos.getInterfaz(interfaceQueImplementa);
            /*if(!interfaceImplementada(interfaceAncestra)){
                throw new SemanticException("no esta declarada", new Token(idClase, interfaceAncestra.nombreClase.getLexema(), interfaceAncestra.nombreClase.getLinea()));
            }*/
            interfaceImplementada(interfaceAncestra);
        }
    }

    private void checkAtributosBienDeclarados(){
        for(Map.Entry<String, Atributo> atributo : atributos.entrySet()){
            //if(atributo.getValue().getId())
        }
    }

    private void interfaceImplementada(Interfaz interfaz) throws SemanticException {
        for(Map.Entry<String,ArrayList<Metodo>> listaMetodos : interfaz.metodos.entrySet()){
            for(Metodo metodo : listaMetodos.getValue()){
                if(!estaImplementado(metodo))
                    throw new SemanticException("El metodo "+ metodo.getId().getLexema()+" de la interfaz "+interfaz.getNombreClase() +
                            " no está implementado en la clase "+nombreClase.getLexema());
            }
        }
    }

    private boolean estaImplementado(Metodo metodo){
        for(Map.Entry<String,ArrayList<Metodo>> listaMetodos : metodos.entrySet()){
            for(Metodo met : listaMetodos.getValue()){
                if(metodo.coincideEncabezado(met))
                    return true;
            }
        }
        return false;
    }


    public void print(){

        for(Map.Entry<String, Atributo> atributo : atributos.entrySet()){
            atributo.getValue().print();
        }

        for(Map.Entry<String, ArrayList<Metodo>> listaMetodos : metodos.entrySet()){
            for(Metodo metodo : listaMetodos.getValue()){
                System.out.println("\nNombre de metodo: " + metodo.getId().getLexema());
            }
        }
    }


}

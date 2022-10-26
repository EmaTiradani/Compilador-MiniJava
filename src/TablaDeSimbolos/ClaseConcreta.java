package TablaDeSimbolos;

import exceptions.SemanticException;
import lexycal.Token;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static lexycal.TokenId.*;

public class ClaseConcreta extends Clase{


    private Token nombreClase;
    private String nombreClasePadre;

    private HashMap<String, Atributo> atributos;
    private HashMap<String, ArrayList<Metodo>> metodos;

    boolean consolidado;
    boolean notHerenciaCircular;

    private Metodo metodoMain;

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

        metodoMain = new Metodo(new Token(idMetVar, "main", 0), new TipoMetodo("void"), true, null);

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

    @Override
    public ArrayList<String> getAncestros() {
        ArrayList<String> ancestros = new ArrayList<>();
        ancestros.add(nombreClase.getLexema());

        for(String interfaz : listaInterfaces){
            ancestros.addAll(TablaDeSimbolos.getInterfaz(interfaz).getAncestros());
        }
        ancestros.add(nombreClasePadre);
        if(nombreClasePadre.equals("Object")){
            return ancestros;
        }else{
            ancestros.addAll(TablaDeSimbolos.getClase(nombreClasePadre).getAncestros());
            return ancestros;
        }

    }

    public void noTieneHerenciaCircular(){
        notHerenciaCircular = true;
    }

    public void insertarAtributo(Atributo atributo) throws SemanticException {
        if(!atributos.containsKey(atributo.getId())){
            atributos.put(atributo.getId(), atributo);
        }else{
            throw new SemanticException(" Atributo con el mismo ID", atributo.getToken());
        }
    }

    public void insertarMetodo(Metodo metodo) throws SemanticException {

        if(metodos.containsKey(metodo.getId().getLexema())){
            boolean puedeSerInsertado = false;
            for(Metodo met : metodos.get(metodo.getId().getLexema())){
                if(met.soloCambiaTipoRetorno(metodo) || met.mismoEncabezado(metodo) || met.coincideEncabezado(metodo) || met.soloCambiaEstatico(metodo)){// Java no soporta sobrecarga dep. del contexto si pasa eso, error
                    throw new SemanticException("esta mal redefinido", metodo.getId());
                }else{
                    puedeSerInsertado = true;
                }
            }
            if(puedeSerInsertado){
                metodos.get(metodo.getId().getLexema()).add(metodo);
            }
        }else{
            ArrayList<Metodo> listaMetodos = new ArrayList<Metodo>();
            listaMetodos.add(metodo);
            metodos.put(metodo.getId().getLexema(), listaMetodos);
        }
        if(metodo.esMain() && metodo.getId().getLinea()>metodoMain.getId().getLinea())
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
            checkHerenciaCircular(new ArrayList<>());
            checkMetodosBienDeclarados();
            checkAtributosBienDeclarados();
            checkImplementsNotRepeated();
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
        // Inserto los metodos del padre
        HashMap<String,ArrayList<Metodo>> metodosClasePadre = TablaDeSimbolos.getClase(nombreClasePadre).getMetodos();
        for(Map.Entry<String, ArrayList<Metodo>> listaMetodos : metodosClasePadre.entrySet()){
            for(Metodo metodo : listaMetodos.getValue()){
                insertarMetodoPadre(metodo);
            }
        }
        // Inserto los atributos del padre
        HashMap<String,Atributo> atributosClasePadre = TablaDeSimbolos.getClase(nombreClasePadre).getAtributos();
        for(Map.Entry<String, Atributo> atributo : atributosClasePadre.entrySet()){
            insertarAtributosDeAncestros(atributo.getValue());
        }
    }

    // Este metodo es un insertarMetodo especifico, ya que en caso de tener dos metodos exactamente iguales en una clase padre y una clase hijo, no debera dar error, la inserto
    private void insertarMetodoPadre(Metodo metodo) throws SemanticException {
        if(metodos.containsKey(metodo.getId().getLexema())){
            boolean puedeSerInsertado = false;
            for(Metodo met : metodos.get(metodo.getId().getLexema())){
                if(met.soloCambiaTipoRetorno(metodo) || met.soloCambiaEstatico(metodo)){// Java no soporta sobrecarga dep. del contexto si pasa eso, error
                    throw new SemanticException("esta mal redefinido", met.getId());
                }else{
                    puedeSerInsertado = true;
                }
            }
            if(puedeSerInsertado){
                metodos.get(metodo.getId().getLexema()).add(metodo);
            }
        }else{
            ArrayList<Metodo> listaMetodos = new ArrayList<Metodo>();
            listaMetodos.add(metodo);
            metodos.put(metodo.getId().getLexema(), listaMetodos);
        }
        if(metodo.esMain() && metodo.getId().getLinea()>metodoMain.getId().getLinea())
            metodoMain = metodo;
    }

    public void insertarAtributosDeAncestros(Atributo atributo) throws SemanticException {
        if(!atributos.containsKey(atributo.getId())){
            atributos.put(atributo.getId(), atributo);
        }else{
            Atributo atributoRepetido = atributos.get(atributo.getId());
            throw new SemanticException(" Atributo con el mismo ID", atributoRepetido.getToken());
        }
    }

    private boolean checkMain(){
        return !(metodoMain.getId().getLinea() == 0);
    }

    private void checkHerenciaExplicitaDeclarada() throws SemanticException {
        if(!TablaDeSimbolos.existeClase(nombreClasePadre)){
            throw new SemanticException("no esta declarada", new Token(idClase, nombreClasePadre, nombreClase.getLinea()));
        }
        for(String interfaceAncestra : listaInterfaces){
            if(!TablaDeSimbolos.existeInterfaz(interfaceAncestra)){
                throw new SemanticException("no esta declarada", new Token(idClase, interfaceAncestra, nombreClase.getLinea()));
            }
        }
    }

    private void checkImplementsNotRepeated() throws SemanticException {
        for(String interfaz : listaInterfaces){
            int contador = 0;
            for(String interfaz2 : listaInterfaces){
                if(interfaz2.equals(interfaz))
                    contador++;
            }
            if(contador>1)
                throw new SemanticException("Interface repetida", new Token(nombreClase.getTokenId(), interfaz, nombreClase.getLinea()));
        }
    }

    private void checkConstructoresBienDeclarados() throws SemanticException {
        for(Constructor constructor : constructores){
            constructor.checkDec();
        }
    }

    private void checkHerenciaCircular(ArrayList<Token> listaClases) throws SemanticException {
        listaClases.add(nombreClase);
        ClaseConcreta claseAncestra = TablaDeSimbolos.getClase(nombreClasePadre);
        claseAncestra.checkHerenciaExplicitaDeclarada();// mmmm TODO

        if (!claseAncestra.herenciaCircular()) {
            if (listaClases.contains(claseAncestra.getToken())) {
                    throw new SemanticException(" Hay herencia circular", new Token(idMetVar, claseAncestra.getNombreClase(), nombreClase.getLinea()));
            }
            claseAncestra.checkHerenciaCircular(listaClases);
        }
        listaClases.remove(nombreClase);
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
            TablaDeSimbolos.getInterfaz(interfaceQueImplementa).consolidar();
            Interfaz interfaceAncestra = TablaDeSimbolos.getInterfaz(interfaceQueImplementa);
            interfaceImplementada(interfaceAncestra);
        }
    }

    private void checkAtributosBienDeclarados() throws SemanticException {
        for(Map.Entry<String, Atributo> atributo : atributos.entrySet()){
            Tipo tipoAtributo = atributo.getValue().getTipo();
            if(!tipoAtributo.isPrimitive && !TablaDeSimbolos.existeClase(tipoAtributo.getType()) && !TablaDeSimbolos.existeInterfaz(tipoAtributo.getType())){
                throw new SemanticException(" El atributo: "+atributo.getValue().getId()+
                        "es de un tipo clase "+tipoAtributo.getType()+" que no esta declarado", new Token(idClase, tipoAtributo.id, atributo.getValue().getToken().getLinea()));
            }
        }
    }

    private void interfaceImplementada(Interfaz interfaz) throws SemanticException {
        for(Map.Entry<String,ArrayList<Metodo>> listaMetodos : interfaz.metodos.entrySet()){
            for(Metodo metodo : listaMetodos.getValue()){
                if(!estaImplementado(metodo))
                    throw new SemanticException(" El metodo "+ metodo.getId().getLexema()+" de la interfaz "+interfaz.getNombreClase() +
                            " no está implementado en la clase "+nombreClase.getLexema(), new Token(idClase, interfaz.getNombreClase(), nombreClase.getLinea()));
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

    public void checkSentencias() throws SemanticException {
        for(Map.Entry<String,ArrayList<Metodo>> listaMetodos : metodos.entrySet()){
            for(Metodo metodo : listaMetodos.getValue()){
                metodo.checkSentencias();
            }
        }
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

package TablaDeSimbolos;

import TablaDeSimbolos.nodosAST.expresion.NodoExpresion;
import exceptions.SemanticException;
import lexycal.Token;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static lexycal.TokenId.*;

public class ClaseConcreta extends Clase{


    private Token nombreClase;
    private String nombreClasePadre;

    private HashMap<String, Atributo> atributos;
    private HashMap<String, ArrayList<Metodo>> metodos;
    private HashMap<String, ArrayList<Metodo>> metodosIniciales;

    boolean consolidado;
    boolean generado;
    boolean notHerenciaCircular;

    private Metodo metodoMain;

    ArrayList<Constructor> constructores;

    // Numero del ultimo offset, ya que algunos quedaran en 0, sirve para cuando se necesite saber el offset final del padre para armar el offset de una clase hija
    private int offsetFinalVT;
    private int cantMetodosSinConflictos;

    private int offsetCIR;
    private Map<Integer, Metodo> mapeoMetodosDinamicos;

    public ClaseConcreta(Token nombreClase){
        this.nombreClase = nombreClase;
        this.nombreClasePadre = "Object";
        atributos = new HashMap<>();
        listaInterfaces = new ArrayList<>();
        metodos = new HashMap<>();
        metodosIniciales = new HashMap<>();
        constructores = new ArrayList<>();
        mapeoMetodosDinamicos = new HashMap<>();

        consolidado = false;
        notHerenciaCircular = false;

        Constructor constructor = new Constructor(nombreClase);
        constructor.setClaseContenedora(this);
        constructores.add(constructor);

        metodoMain = new Metodo(new Token(idMetVar, "main", 0), new TipoMetodo("void"), true, null);

        offsetCIR = 1;
        offsetActualVT = 1;
        cantMetodosSinConflictos = 0;

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

    public Map<Integer, Metodo> getMetodosDinamicos(){
        return mapeoMetodosDinamicos;
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
                metodo.setClaseQueDefine(this);
            }
        }else{
            ArrayList<Metodo> listaMetodos = new ArrayList<Metodo>();
            listaMetodos.add(metodo);
            metodos.put(metodo.getId().getLexema(), listaMetodos);
            metodo.setClaseQueDefine(this);
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

    public Atributo getAtributo(String idVar){
        return atributos.get(idVar);
    }

    public HashMap<String,ArrayList<Metodo>> getMetodos(){
        return metodos;
    }

    public String getNombreClasePadre(){
        return nombreClasePadre;
    }

    public void setMethodOffset(String methodName, int methodOffset) {
        metodos.get(methodName).get(0).insertOffsetEnClase(methodOffset);
    }

    public int getCantMetodosSinConflictos(){
        return cantMetodosSinConflictos;
    }

    public void calculateMetodosSinConflictos(){
        for(Map.Entry<String,ArrayList<Metodo>> listaMetodos : metodos.entrySet()){
            for(Metodo metodo : listaMetodos.getValue()){
                if(metodo.getOffsetEnClase() != -1 && !metodo.getEstatico())
                    cantMetodosSinConflictos++;
            }
        }
    }

    public int getOffsetFinal(){
        return offsetFinalVT;
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

        if(!consolidado){
            guardarMetodosIniciales();// Me guardo los metodos que define esta clase (V2 modified)

            if(TablaDeSimbolos.getClase(nombreClasePadre).consolidado){
                insertarMetodoYAtributosDePadre();
                consolidado = true;
            }else{
                TablaDeSimbolos.getClase(nombreClasePadre).consolidar();
                consolidar();
                consolidado = true;
            }
            checkImplementaTodosLosMetodosDeSuInterfaz();

            calculateMetodosSinConflictos();
        }

    }

    private void guardarMetodosIniciales(){
        for(Map.Entry<String,ArrayList<Metodo>> listaMetodos : metodos.entrySet()){
            Metodo metodo = listaMetodos.getValue().get(0);
            ArrayList<Metodo> metodosConElMismoNombre = new ArrayList<Metodo>();
            metodosConElMismoNombre.add(metodo);
            metodosIniciales.put(metodo.getId().getLexema(), metodosConElMismoNombre);

            // V2 Ahora esto lo que hace es "reportar" a la tabla de simbolos los metodos definidos por esta clase
            TablaDeSimbolos.addMethod(metodo, this);
        }
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
                if(!metodo.getEstatico()) {
                    metodo.insertOffsetEnClase(-1); // En caso de que haya un conflicto no le asigno ningun offset, lo hago despues de finalizar la consolidacion
                    metodo.insertClaseEnConflicto(this);
                    metodo.insertClaseEnConflicto(TablaDeSimbolos.getClase(nombreClasePadre));
                    metodos.get(metodo.getId().getLexema()).get(0).insertOffsetEnClase(-1);
                    TablaDeSimbolos.getClase(nombreClasePadre).propagarConflicto(metodo, this);
                }
                metodos.get(metodo.getId().getLexema()).add(metodo);
                metodo.setClaseQueDefine(this);
            }
        }else{
            ArrayList<Metodo> listaMetodos = new ArrayList<Metodo>();
            listaMetodos.add(metodo);
            metodos.put(metodo.getId().getLexema(), listaMetodos);
            if(!metodo.getEstatico()) {
                metodo.insertOffsetEnClase(-1); // En caso de que haya un conflicto no le asigno ningun offset, lo hago despues de finalizar la consolidacion
                metodo.insertClaseEnConflicto(this);
                metodo.insertClaseEnConflicto(TablaDeSimbolos.getClase(nombreClasePadre));
                TablaDeSimbolos.getClase(nombreClasePadre).propagarConflicto(metodo, this);

            }
        }
    }

    public void propagarConflicto(Metodo metodo, Clase clase){
        if(nombreClase.getLexema() != "Object") {
            if (metodos.containsKey(metodo.getId().getLexema())) {
                metodos.get(metodo.getId().getLexema()).get(0).insertClaseEnConflicto(clase);
                for (String interfaceQueImplementa : listaInterfaces) {
                    TablaDeSimbolos.getInterfaz(interfaceQueImplementa).propagarConflicto(metodo, clase);
                }
            }
            TablaDeSimbolos.getClase(nombreClasePadre).propagarConflicto(metodo, clase);
        }
    }

    public void insertarAtributo(Atributo atributo) throws SemanticException {
        if(!atributos.containsKey(atributo.getId())){
            atributos.put(atributo.getId(), atributo);
        }else{
            throw new SemanticException(" Atributo con el mismo ID", atributo.getToken());
        }
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

    private void interfaceImplementada(Interfaz interfaz) throws SemanticException {
        for(Map.Entry<String,ArrayList<Metodo>> listaMetodos : interfaz.metodos.entrySet()){
            for(Metodo metodo : listaMetodos.getValue()){
                if(!estaImplementado(metodo))
                    throw new SemanticException(" El metodo "+ metodo.getId().getLexema()+" de la interfaz "+interfaz.getNombreClase() +
                            " no está implementado en la clase "+nombreClase.getLexema(), new Token(idClase, interfaz.getNombreClase(), nombreClase.getLinea()));
                metodos.get(metodo.getId().getLexema()).get(0).insertOffsetEnClase(-1);
                metodos.get(metodo.getId().getLexema()).get(0).insertClaseEnConflicto(interfaz);
                metodos.get(metodo.getId().getLexema()).get(0).insertClaseEnConflicto(this);
                metodo.insertOffsetEnClase(-1); // Asumo todos los metodos entre interfaces y clases como en conflicto
                metodo.insertClaseEnConflicto(interfaz);
                metodo.insertClaseEnConflicto(this);
                metodo.setClaseQueDefine(this);
                metodos.get(metodo.getId().getLexema()).get(0).setClaseQueDefine(this);
                // Tambien seteo esto para el metodo que esta implementado en esta clase
                Metodo metodoImplementado = metodos.get(metodo.getId().getLexema()).get(0);
                metodoImplementado.insertOffsetEnClase(-1);
                metodoImplementado.insertClaseEnConflicto(interfaz);
                metodoImplementado.insertClaseEnConflicto(this);
                metodoImplementado.setClaseQueDefine(this);

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

    private void checkAtributosBienDeclarados() throws SemanticException {
        for(Map.Entry<String, Atributo> atributo : atributos.entrySet()){
            Tipo tipoAtributo = atributo.getValue().getTipo();
            if(!tipoAtributo.isPrimitive && !TablaDeSimbolos.existeClase(tipoAtributo.getType()) && !TablaDeSimbolos.existeInterfaz(tipoAtributo.getType())){
                throw new SemanticException(" El atributo: "+atributo.getValue().getId()+
                        "es de un tipo clase "+tipoAtributo.getType()+" que no esta declarado", new Token(idClase, tipoAtributo.id, atributo.getValue().getToken().getLinea()));
            }
        }
    }

    public Metodo getMetodoQueConformaParametros(Token idMet, List<NodoExpresion> parametros) throws SemanticException {
        List<Tipo> tiposDeLosParametros = new ArrayList<>();
        for(NodoExpresion parametro : parametros){
            tiposDeLosParametros.add(parametro.chequear());
        }

        ArrayList<Metodo> metodosPosibles = metodos.get(idMet.getLexema());

        if(metodosPosibles != null){
            for(Metodo metodo : metodosPosibles){
                if(metodo.conformanParametros(tiposDeLosParametros)){
                    return metodo;
                }
            }
        }
        return null;
    }

    public void checkSentencias() throws SemanticException {
        TablaDeSimbolos.claseActual = this;

        for(Map.Entry<String,ArrayList<Metodo>> listaMetodos : metodos.entrySet()){
            Metodo metodo = listaMetodos.getValue().get(0);
            metodo.checkSentencias();
        }
    }

    public void generar(){
        if(!generado){
            // Aca capaz haya que ver si es la clase Object, y cuidado con System

            offsetActualVT = TablaDeSimbolos.getClase(nombreClasePadre).getOffsetFinal();
            if(TablaDeSimbolos.getClase(nombreClasePadre).generado){

                TablaDeSimbolos.claseActual = this;

                TablaDeSimbolos.gen(".DATA");
                //System.out.println("if Clase: "+ this.getNombreClase() + metodos.size());
                generarDataVTTableVersion();

                TablaDeSimbolos.gen(".CODE ");
                generarMetodos();
                generado = true;

            }else{
                if(nombreClasePadre == "Object"){
                    TablaDeSimbolos.claseActual = this;

                    TablaDeSimbolos.gen(".DATA");
                    //System.out.println("else Clase: "+ this.getNombreClase() + metodos.size());
                    generarDataVTTableVersion();

                    TablaDeSimbolos.gen(".CODE ");
                    generarMetodos();
                    generado = true;
                }else {
                    TablaDeSimbolos.getClase(nombreClasePadre).generar();
                    generar();
                    generado = true;
                }
            }
        }
    }

    private void generarDataVTTableVersion(){
        String dataVT = "VT_"+this.getNombreClase();
        int maximoOffsetClase = 0;
        if(obtenerCantMetodosDinamicos() == 0 || (obtenerCantMetodosDinamicos() == 1 && metodos.containsKey("debugPrint"))){
            dataVT += ": NOP";
        }else {
            for (ArrayList<Metodo> listaMetodo : metodos.values()) {
                Metodo metodo = listaMetodo.get(0);
                if (!metodo.getEstatico()) {
                    if (metodo.getOffsetEnClase() == -1) {
                        metodo.insertOffsetEnClase(proximoOffsetDisponible());
                    }
                    //System.out.println("Clase: " + this.getNombreClase() + " metodo: " + metodo.getId().getLexema() + " offset: " + metodo.getOffsetEnClase());
                    if (metodo.getOffsetEnClase() > maximoOffsetClase)
                        maximoOffsetClase = metodo.getOffsetEnClase();
                }
            }
            dataVT += ": DW ";

            for (int i = 0; i <= maximoOffsetClase; i++) {
                Metodo metodo = getMethodByOffset(i);
                if (metodo == null) {
                    dataVT += "0,";
                } else {
                    if (metodo.getClaseContenedora().equals(this.nombreClase.getLexema())) {
                        dataVT += metodo.getId().getLexema() + this.getNombreClase(); // Esto me va a generar nombreDeMetodoNombreDeClase, Ej.: m1A
                    } else {
                        dataVT += metodo.getId().getLexema() + metodo.getClaseQueDefine().getNombreClase();
                    }
                    dataVT += ",";

                }
            }
                dataVT = dataVT.substring(0, dataVT.length() - 1); // Elimino la ultima coma que queda luego de agregar el ultimo idMet

        }

        TablaDeSimbolos.gen(dataVT);
    }

    private int obtenerCantMetodosDinamicos(){
        int cant = 0;
        for (ArrayList<Metodo> listaMetodo : metodos.values()) {
            Metodo metodo = listaMetodo.get(0);
            if (!metodo.getEstatico()) {
                cant++;
            }
        }
        return cant;
    }

    private int proximoOffsetDisponible(){
        int offset = -1;
        for(int i=0; i<metodos.size(); i++) {
            if(getMethodByOffset(i) == null)
                return i;
        }
        return offset;
    }

    private Metodo getMethodByOffset(int offset){
        for (ArrayList<Metodo> listaMetodo : metodos.values()) {
            Metodo metodo = listaMetodo.get(0);
            if (!metodo.getEstatico() && metodo.getOffsetEnClase() == offset) {
                return metodo;
            }
        }
        return null;
    }

    private void generarDataVT(){ // Deprecated, now using Table Version
        String dataVT = "VT_"+this.getNombreClase();
        //offsetFinalVT +=
        if(offsetFinalVT == 0){
            dataVT += ": NOP";
        }else{
            dataVT += ": DW ";
            for(int i = 0; i<offsetFinalVT+metodos.size(); i++){
                Metodo metodo = mapeoMetodosDinamicos.get(i);
                if(metodo == null){
                    dataVT += "0,";
                }else {
                    if (metodo.getClaseContenedora().equals(this.nombreClase.getLexema())){
                        dataVT += metodo.getId().getLexema() + this.getNombreClase(); // Esto me va a generar nombreDeMetodoNombreDeClase, Ej.: m1A
                        metodos.get(metodo.getId().getLexema()).get(0).insertOffsetEnClase(i);
                    } else{
                        dataVT += metodo.getId().getLexema() + metodo.getClaseQueDefine().getNombreClase();
                        metodos.get(metodo.getId().getLexema()).get(0).insertOffsetEnClase(i);
                    }

                    dataVT += ",";
                }
            }
            dataVT = dataVT.substring(0, dataVT.length()-1); // Elimino la ultima coma que queda luego de agregar el ultimo idMet
        }

        TablaDeSimbolos.gen(dataVT);
    }

    private void generarMetodos(){
        for(Map.Entry<String,ArrayList<Metodo>> listaMetodos : metodosIniciales.entrySet()){
            Metodo metodo = listaMetodos.getValue().get(0);

            if(!metodo.getId().getLexema().equals("debugPrint")/* && metodo.getClaseQueDefine().getNombreClase().equals(nombreClase.getLexema())*/)
                metodo.generar();
        }
        // Codigo para el constructor, cambiar en caso de hacer algun logro de constructores (no va a pasar jaja)
        constructores.get(0).generar();
    }

    public void asignarOffsetAtributos(){
        if(!nombreClasePadre.equals("Object"))// Si la clase padre es object, comienza en 1
            offsetCIR = TablaDeSimbolos.getClase(nombreClasePadre).getOffsetCIR();
        for(Map.Entry<String, Atributo> atributo : atributos.entrySet()){
            if(!TablaDeSimbolos.getClase(nombreClasePadre).getAtributos().containsValue(atributo.getValue())) {
                atributo.getValue().setOffset(offsetCIR);
                offsetCIR++;
            }
        }
    }

    private void traerMayorConjuntoClasesEnConflicto(){

        for(String interfaceQueImplementa : listaInterfaces) {
            Interfaz interfaz = TablaDeSimbolos.getInterfaz(interfaceQueImplementa);
            for(Map.Entry<String,ArrayList<Metodo>> listaMetodos : interfaz.metodos.entrySet()){
                Metodo metodo = listaMetodos.getValue().get(0);
                ArrayList<Clase> clasesConflictoMetodoInterfaz = metodo.getClasesEnConflicto();
                ArrayList<Clase> clasesConflictoMetodoThis = metodos.get(metodo.getId().getLexema()).get(0).getClasesEnConflicto();
                if(clasesConflictoMetodoInterfaz.size() > clasesConflictoMetodoThis.size())
                    metodos.get(metodo.getId().getLexema()).get(0).setClasesEnConflicto(clasesConflictoMetodoInterfaz);
                else
                    metodo.setClasesEnConflicto(clasesConflictoMetodoThis);

            }
        }

        ClaseConcreta padre = TablaDeSimbolos.getClase(nombreClasePadre);
        for(Map.Entry<String,ArrayList<Metodo>> listaMetodos : padre.getMetodos().entrySet()){
            Metodo metodo = listaMetodos.getValue().get(0);
            ArrayList<Clase> clasesConflictoMetodoPadre = metodo.getClasesEnConflicto();
            ArrayList<Clase> clasesConflictoMetodoThis = metodos.get(metodo.getId().getLexema()).get(0).getClasesEnConflicto();
            if(clasesConflictoMetodoPadre.size() > clasesConflictoMetodoThis.size())
                metodos.get(metodo.getId().getLexema()).get(0).setClasesEnConflicto(clasesConflictoMetodoPadre);
            else
                metodo.setClasesEnConflicto(clasesConflictoMetodoThis);
        }

    }

    private void asignarOffsetMetodosSinConflictos(){
        for(Map.Entry<String,ArrayList<Metodo>> listaMetodos : metodos.entrySet()){
            Metodo metodo = listaMetodos.getValue().get(0);
            if(!metodo.getEstatico() && !metodo.getConflictoSolucionado()){
                metodo.insertOffsetEnClase(offsetActualVT);
                mapeoMetodosDinamicos.put(offsetActualVT, metodo);
                offsetActualVT++;
            }
        }
        offsetFinalVT += offsetActualVT;
    }

    private void setearOffsetMaximo(Metodo metodo){
        int offsetMaximo = 1;
        for(Clase clase : metodo.getClasesEnConflicto()){
            if(clase.getOffsetActualVT() > offsetMaximo){
                offsetMaximo = clase.getOffsetActualVT();
            }
            clase.offsetActualVT++;
        }
        for(String nombreInterfaz : listaInterfaces){
            Interfaz interfaz = TablaDeSimbolos.getInterfaz(nombreInterfaz);
            if(interfaz.metodos.containsKey(metodo.getId().getLexema())){
                if(interfaz.getOffset() > offsetMaximo)
                    offsetMaximo = interfaz.getOffset();
            }
        }
        metodo.insertOffsetEnClase(offsetMaximo);
    }

    public int getOffsetActualVT(){
        return offsetActualVT;
    }

    public int getOffsetCIR(){
        return offsetCIR;
    }

    public ArrayList<Clase> getClasesEnConflicto(String nombreMetodo){
        return metodos.get(nombreMetodo).get(0).getClasesEnConflicto();
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

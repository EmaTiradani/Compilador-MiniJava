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

    boolean consolidado, notHerenciaCircular;

    private int offsetFinal;
    private int cantMetodosSinConflictos;

    private boolean generado;

    private Map<Integer, Metodo> mapeoMetodosDinamicos;

    //public int offsetActualVT;


    public Interfaz(Token nombreInterface){
        this.nombreInterface = nombreInterface;
        clasesQueExtiende = new ArrayList<>();
        listaInterfaces = new ArrayList<>();
        metodos = new HashMap<>();
        mapeoMetodosDinamicos = new HashMap<>();

        consolidado = false;
        notHerenciaCircular = false;

        offsetFinal = 0;
        cantMetodosSinConflictos = 0;
        generado = false;
    }

    public Token getToken(){
        return nombreInterface;
    }

    @Override
    public String getNombreClase() {
        return nombreInterface.getLexema();
    }

    @Override
    public HashMap<String, ArrayList<Metodo>> getMetodos() {
        return metodos;
    }

    public boolean herenciaCircular(){ return notHerenciaCircular;}

    public int getOffset(){
        return offsetFinal;
    }

    @Override
    public ArrayList<String> getAncestros() {
        ArrayList<String> ancestros = new ArrayList<>();
        ancestros.add(nombreInterface.getLexema());

        for(String clase : listaInterfaces){
            ancestros.addAll(TablaDeSimbolos.getInterfaz(clase).getAncestros());
        }
        return ancestros;
    }

    @Override
    public int getCantMetodosSinConflictos() {
        return cantMetodosSinConflictos;
    }

    public int getOffsetActualVT(){ // Aunque no tenga VT, es el offset de los metodos
        return offsetActualVT;
    }

    @Override
    public Map<Integer, Metodo> getMetodosDinamicos() {
        return mapeoMetodosDinamicos;
    }


    public void propagarConflicto(Metodo metodo, Clase clase) {
        if(metodos.containsKey(metodo.getId().getLexema())) {
            metodos.get(metodo.getId().getLexema()).get(0).insertClaseEnConflicto(clase);
            for (String interfaceQueImplementa : listaInterfaces) {
                TablaDeSimbolos.getInterfaz(interfaceQueImplementa).propagarConflicto(metodo, clase);
            }
        }
    }

    public void insertarAtributo(Atributo atributo) throws SemanticException {
        // Aca no deberia entrar nunca
    }

    @Override
    public void insertarMetodo(Metodo metodo) throws SemanticException {
        if(!metodo.getEstatico()) {
            if (metodos.containsKey(metodo.getId().getLexema())) {
                boolean puedeSerInsertado = false;
                for (Metodo met : metodos.get(metodo.getId().getLexema())) {
                    if (met.soloCambiaTipoRetorno(metodo) || met.mismoEncabezado(metodo) || met.coincideEncabezado(metodo)) {// Java no soporta sobrecarga dep. del contexto si pasa eso, error
                        throw new SemanticException("esta mal redefinido", metodo.getId());
                    } else {
                        puedeSerInsertado = true;
                    }
                }
                if (puedeSerInsertado) {
                    metodos.get(metodo.getId().getLexema()).add(metodo);
                }
            } else {
                ArrayList<Metodo> listaMetodos = new ArrayList<Metodo>();
                listaMetodos.add(metodo);
                metodos.put(metodo.getId().getLexema(), listaMetodos);
                //mapeoMetodosDinamicos.put(offsetActualVT, metodo);
            }
        }else{
            throw new SemanticException("Metodo estatico en una interfaz", metodo.getId());
        }
    }


    public boolean estaBienDeclarada() throws SemanticException{
        if(!nombreInterface.getLexema().equals("Object")){
            checkExtends();
            checkHerenciaCircular(new ArrayList<Token>());
            checkMetodosBienDeclarados();
            checkExtendsNotRepeated();
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

    /*private void setOffsetDeAncestros(){
        int offsetDeLosAncestros = 0;
        for(String interfaceAncestra : listaInterfaces) {
            Interfaz ancestro = TablaDeSimbolos.getInterfaz(interfaceAncestra);
            if(ancestro.consolidado){
                offset = ancestro.getOffset();
                consolidado = true;
            }else{
                ancestro.consolidar();
                importarMetodosDePadre(ancestro);
                consolidado = true;
            }
        }
        this.offset = offsetDeLosAncestros;
    }*/

    public void consolidar() throws SemanticException {
        //offsetActualVT = maxOffsetFinalPadres
        for(String interfaceAncestra : listaInterfaces) {
            Interfaz ancestro = TablaDeSimbolos.getInterfaz(interfaceAncestra);
            ancestro.checkExtends();
            if(ancestro.consolidado){
                //offset = ancestro.getOffset();
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
                insertarMetodoPadre(metodo);
            }
        }
    }

    private void insertarMetodoPadre(Metodo metodo) throws SemanticException {
        if(metodos.containsKey(metodo.getId().getLexema())){
            boolean puedeSerInsertado = false;
            for(Metodo met : metodos.get(metodo.getId().getLexema())){
                if(met.soloCambiaTipoRetorno(metodo)){// Java no soporta sobrecarga dep. del contexto si pasa eso, error
                    throw new SemanticException("esta mal redefinido", metodo.getId());
                }else{
                    puedeSerInsertado = true;
                }
            }
            if(puedeSerInsertado){
                metodos.get(metodo.getId().getLexema()).add(metodo);
                if(!metodo.getEstatico()) {
                    metodo.insertOffsetEnClase(-1); // En caso de que haya un conflicto no le asigno ningun offset, lo hago despues de finalizar la consolidacion
                    metodo.insertClaseEnConflicto(this);
                }
            }
        }else{
            ArrayList<Metodo> listaMetodos = new ArrayList<Metodo>();
            listaMetodos.add(metodo);
            metodos.put(metodo.getId().getLexema(), listaMetodos);
            if(!metodo.getEstatico()){
                cantMetodosSinConflictos++;
            }
        }
    }

    private void checkExtendsNotRepeated() throws SemanticException {
        for(String interfaz : listaInterfaces){
            int contador = 0;
            for(String interfaz2 : listaInterfaces){
                if(interfaz2.equals(interfaz))
                    contador++;
            }
            if(contador>1)
                throw new SemanticException("Interface repetida", new Token(nombreInterface.getTokenId(), interfaz, nombreInterface.getLinea()));
        }
    }

    public void insertarAncestro(Interfaz interfaz){
        listaInterfaces.add(interfaz.getNombreClase());
    }

    public ArrayList<Clase> getClasesEnConflicto(String nombreMetodo){
        return metodos.get(nombreMetodo).get(0).getClasesEnConflicto();
    }

    public void generarOffsets(){
        if(!generado){
            if(listaInterfaces.size() == 0){
                offsetActualVT = 0;
                asignarOffsetMetodosEnConflicto();
                for(Map.Entry<String,ArrayList<Metodo>> listaMetodos : metodos.entrySet()) {
                    for (Metodo metodo : listaMetodos.getValue()) {
                        if (!metodo.getConflictoSolucionado()) {// Si no tienen el conflicto solucionado es que nunca estuvieron en conflicto
                            metodo.insertOffsetEnClase(offsetActualVT);
                            offsetActualVT++;
                        }
                    }
                }
            }
            else{
                for(String interfaz : listaInterfaces){
                    TablaDeSimbolos.getInterfaz(interfaz).generarOffsets();
                    if(TablaDeSimbolos.getInterfaz(interfaz).getOffset() > offsetActualVT)
                        offsetActualVT = TablaDeSimbolos.getInterfaz(interfaz).getOffset();
                }
                asignarOffsetMetodosEnConflicto();
                for(Map.Entry<String,ArrayList<Metodo>> listaMetodos : metodos.entrySet()) {
                    for (Metodo metodo : listaMetodos.getValue()) {
                        if (!metodo.getConflictoSolucionado()) {// Si no tienen el conflicto solucionado es que nunca estuvieron en conflicto
                            metodo.insertOffsetEnClase(offsetActualVT);
                            offsetActualVT++;
                        }
                    }
                }
            }
        }
        generado = true;
    }

    private void asignarOffsetMetodosEnConflicto(){
        traerMayorConjuntoClasesEnConflicto();
        int conflictosSolucionados = 0;

        for(Map.Entry<String,ArrayList<Metodo>> listaMetodos : metodos.entrySet()){
            Metodo metodo = listaMetodos.getValue().get(0);

            // -1 porque significa que es un metodo con conflictos
            if(!metodo.getEstatico() && metodo.getOffsetEnClase() == -1){
                int offsetConflictos = metodo.getOffsetConflictos();
                if(offsetConflictos > offsetInicialConflictos)
                    offsetInicialConflictos = offsetConflictos;
            }
        }

        for(Map.Entry<String,ArrayList<Metodo>> listaMetodos : metodos.entrySet()){
            Metodo metodo = listaMetodos.getValue().get(0);

            // -1 porque significa que es un metodo con conflictos
            if(!metodo.getEstatico() && metodo.getOffsetEnClase() == -1){
                metodo.setConflictoSolucionado(this);
                //int offsetConflictos = metodo.getOffsetConflictos();
                /*if(offsetConflictos > offsetFinalVT)
                    offsetFinalVT = offsetConflictos;*/
                metodo.insertOffsetEnClase(offsetInicialConflictos+conflictosSolucionados);
                metodo.expandirOffset(offsetInicialConflictos+conflictosSolucionados);
                mapeoMetodosDinamicos.put(offsetInicialConflictos+conflictosSolucionados, metodo);
                conflictosSolucionados++;
            }
        }
        //offsetFinalVT += (offsetInicialConflictos+conflictosSolucionados);

        /*for(Map.Entry<String,ArrayList<Metodo>> listaMetodos : metodos.entrySet()){
            for(Metodo metodo : listaMetodos.getValue()){
                if(!metodo.getEstatico() && metodo.getOffsetEnClase() == -1){
                    metodo.setConflictoSolucionado(this);
                    int offsetConflictos = metodo.getOffsetConflictos();
                    if(offsetConflictos > offsetFinal)
                        offsetFinal = offsetConflictos;
                    metodo.insertOffsetEnClase(offsetConflictos);
                }
            }
        }*/
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

    }

    public void print(){
        for(Map.Entry<String, ArrayList<Metodo>> listaMetodos : metodos.entrySet()){
            for(Metodo metodo : listaMetodos.getValue()){
                System.out.println("\nNombre de metodo: " + metodo.getId().getLexema());
            }
        }
    }

}

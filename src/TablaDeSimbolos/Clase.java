package TablaDeSimbolos;

import exceptions.SemanticException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Clase {


    private String nombreClase;
    private String nombreClasePadre;
    public ArrayList<String> implemented;

    private HashMap<String, Atributo> atributos;
    private HashMap<String, ArrayList<Metodo>> metodos;

    boolean consolidado;
    boolean herenciaCircular;

    public Clase(String nombreClase, String nombreClasePadre){
        this.nombreClase = nombreClase;
        this.nombreClasePadre = nombreClasePadre;
        atributos = new HashMap<>();
        implemented = new ArrayList<>();
        metodos = new HashMap<>();

        consolidado = false;
        herenciaCircular = false;

        if(nombreClasePadre.equals("Object")){
            consolidado = true;
            herenciaCircular = true;
            //herencia circular entra en consolidado? O chequeo por separado?
        }
    }

    public String getNombreClase(){
        return nombreClase;
    }

    public void insertarAtributo(Atributo atributo) throws SemanticException {
        if(!atributos.containsKey(atributo.getId())){
            atributos.put(atributo.getId(), atributo);
        }else{
            throw new SemanticException("Atributo con el mismo ID", "Error");
        }
    }

    public void insertarMetodo(Metodo metodo) throws SemanticException {

        if(metodos.containsKey(metodo.getId().getLexema())){
            /*for(Metodo met : metodos.get(metodo.getId().getLexema())){
                if(met.soloCambiaTipoRetorno(metodo)){// Java no soporta sobrecarga dep. del contexto si pasa eso, error
                    throw new SemanticException("Metodo sobrecargado", "Sobrecarga dep. del contexto");
                }else{
                    ArrayList<Metodo> mets = metodos.get(metodo.getId().getLexema());
                    mets.add(metodo); // ?? que pasa?
                }
            }*/
            throw new SemanticException("Metodo sobrecargado", "Distinto metodo");
        }else{
            ArrayList<Metodo> listaMetodos = new ArrayList<Metodo>();
            listaMetodos.add(metodo);
            metodos.put(metodo.getId().getLexema(), listaMetodos);
        }

        //System.out.println("Metodo insertado");
    }

    public void insertarPadre(String nombreClasePadre){
        this.nombreClasePadre = nombreClasePadre;
    }

    public HashMap<String, Atributo> getAtributos(){
        return atributos;
    }

    public HashMap<String,ArrayList<Metodo>> getMetodos(){
        return metodos;
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

package TablaDeSimbolos;

import exceptions.SemanticException;
import lexycal.Token;

import java.util.ArrayList;
import java.util.HashMap;

public class Clase {

    //private Token nombreClase;
    //private Token nombreClasePadre;
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

    public void insertarMetodo(Metodo metodo){
        // TODO Lo puedo hacer sin tener el logro de los constructores?
        // Tengo que chequear si esta bien la sobrecarga? Es decir, tengo que chequear que no haya 2 metodos con el mismo encabezado en la misma clase?

        /*if(!metodos.containsKey(metodo.getId())){
            metodos.put(metodo.getId(), metodo);
        }*/
        metodos.get(metodo.getId()).add(metodo);
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
        metodos.forEach((s,m) -> {
            System.out.println("Nombre de metodo: " + s + "\n | ");
            m.print();

    }


}

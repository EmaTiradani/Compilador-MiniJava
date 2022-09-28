package TablaDeSimbolos;

import lexycal.Token;
import java.util.ArrayList;

public class Interfaz {

    private Token nombreInterface;
    private ArrayList<String> clasesQueExtiende;
    private ArrayList<Metodo> listaEncabezados;



    public Interfaz(Token nombreInterface){
        this.nombreInterface = nombreInterface;
        clasesQueExtiende = new ArrayList<>();
        listaEncabezados = new ArrayList<Metodo>();
    }

    public Token getToken(){
        return nombreInterface;
    }

    public String getNombre(){
        return nombreInterface.getLexema();
    }

}

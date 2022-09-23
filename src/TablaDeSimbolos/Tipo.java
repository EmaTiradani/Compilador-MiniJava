package TablaDeSimbolos;

import lexycal.TokenId;

public class Tipo {

    private boolean isPrimitive;
    private String id;

    public Tipo(String id){
        this.id = id;
    }

    public String getType(){
        return id;
    }



}

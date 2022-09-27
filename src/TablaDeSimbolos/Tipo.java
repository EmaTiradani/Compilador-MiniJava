package TablaDeSimbolos;

import lexycal.TokenId;

public class Tipo {

    private boolean isPrimitive;
    private String id;

    public Tipo(String id){
        this.id = id;
        isPrimitive = false;
    }

    public String getType(){
        return id;
    }

    public boolean isPrimitive(){
        return isPrimitive;
    }

    public void setPrimitive(){
        isPrimitive = true;
    }

}

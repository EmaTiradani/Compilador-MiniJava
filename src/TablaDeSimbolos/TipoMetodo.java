package TablaDeSimbolos;

public class TipoMetodo extends Tipo{


    public TipoMetodo(String id) {
        super(id);
        if(id.equals("void")){
            isPrimitive = true;
        }
    }
}

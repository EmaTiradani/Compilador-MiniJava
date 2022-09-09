package syntactic;

import lexycal.TokenId;

import java.util.*;

import static lexycal.TokenId.*; //Static? mmm

public class Firsts {

    //Map<String, TokenId> map = new HashMap<String, TokenId>();
    // Hay que cambiar esto por Map<Object,ArrayList<Object>> multiMap = new HashMap<>();
    Map<String, ArrayList<TokenId>> map = new HashMap<String, ArrayList<TokenId>>();
    // Despues le pido la lista de primeros y listo, pero hay que armar la lista de primeros, F.


    public Firsts(){//Si no matchea por algo es por el static
        map.put("Inicial", new ArrayList<TokenId>());
        map.put("ListaClases", new ArrayList<TokenId>());
        map.put("Clase", new ArrayList<TokenId>());
        map.put("ClaseConcreta", new ArrayList<TokenId>());
        map.put("Interface", new ArrayList<TokenId>());
        map.put("HeredaDe", new ArrayList<TokenId>());
        map.put("ImplementaA", new ArrayList<TokenId>());
        map.put("ExtiendeA", new ArrayList<TokenId>());
        map.put("ListaTipoReferencia", new ArrayList<TokenId>());
        map.put("ListaMiembros", new ArrayList<TokenId>());
        map.put("ListaEncabezados", new ArrayList<TokenId>());
        map.put("Miembro", new ArrayList<TokenId>());
        map.put("Atributo", new ArrayList<TokenId>());
        map.put("Metodo", new ArrayList<TokenId>());
        map.put("EncabezadoMetodo", new ArrayList<TokenId>());
        map.put("Visibilidad", new ArrayList<TokenId>());
        map.put("Inicial", new ArrayList<TokenId>());
        map.put("Inicial", new ArrayList<TokenId>());
        map.put("Inicial", new ArrayList<TokenId>());
        map.put("Inicial", new ArrayList<TokenId>());
        map.put("Inicial", new ArrayList<TokenId>());
        map.put("Inicial", new ArrayList<TokenId>());
        map.put("Inicial", new ArrayList<TokenId>());
        map.put("Inicial", new ArrayList<TokenId>());
        map.put("Inicial", new ArrayList<TokenId>());
        map.put("Inicial", new ArrayList<TokenId>());
        map.put("Inicial", new ArrayList<TokenId>());
        map.put("Inicial", new ArrayList<TokenId>());
        map.put("Inicial", new ArrayList<TokenId>());
        map.put("Inicial", new ArrayList<TokenId>());
        map.put("Inicial", new ArrayList<TokenId>());
        map.put("Inicial", new ArrayList<TokenId>());
        map.put("Inicial", new ArrayList<TokenId>());
        map.put("Inicial", new ArrayList<TokenId>());
        map.put("Inicial", new ArrayList<TokenId>());
        map.put("Inicial", new ArrayList<TokenId>());
        map.put("Inicial", new ArrayList<TokenId>());
        map.put("Inicial", new ArrayList<TokenId>());
        map.put("Inicial", new ArrayList<TokenId>());
        map.put("Inicial", new ArrayList<TokenId>());
        map.put("Inicial", new ArrayList<TokenId>());
        map.put("Inicial", new ArrayList<TokenId>());
        map.put("Inicial", new ArrayList<TokenId>());
        map.put("Inicial", new ArrayList<TokenId>());
        map.put("Inicial", new ArrayList<TokenId>());
        map.put("Inicial", new ArrayList<TokenId>());
        map.put("Inicial", new ArrayList<TokenId>());
        map.put("Inicial", new ArrayList<TokenId>());
        map.put("Inicial", new ArrayList<TokenId>());
        map.put("Inicial", new ArrayList<TokenId>());
        map.put("Inicial", new ArrayList<TokenId>());
        map.put("Inicial", new ArrayList<TokenId>());
        map.put("Inicial", new ArrayList<TokenId>());
        map.put("Inicial", new ArrayList<TokenId>());
        map.put("Inicial", new ArrayList<TokenId>());
        map.put("Inicial", new ArrayList<TokenId>());


        map.get("Inicial").add(kw_interface);
        map.get("Inicial").add(kw_class);
        map.get("ListaClases").add(kw_class);
        map.get("ListaClases").add(kw_interface);
        //map.get("ListaClases").add(EOF); Aca va el epsilon?
        /*map.get("ClaseConcreta", kw_class);
        map.get("Interface", kw_interface);
        map.get("HeredaDe", kw_extends);
        map.get("ListaTipoReferencia", idClase);
        //map.get("ListaMiembros", )
        //map.get("ListaEncabezados", )
        //map.get("Miembro", )
        //map.get("Atributo", )
        //map.get("Metodo", )
        //map.get("EncabezadoMetodo", );
        //map.get("TipoMetodo", );
        map.get("Visibilidad", kw_public);
        map.get("Visibilidad", kw_private);
        map.get("Tipo", kw_boolean);
        map.get("Tipo", kw_char);
        map.get("Tipo", kw_int);
        map.get("Tipo", idClase);
        map.get("TipoPrimitivo", kw_boolean);
        map.get("TipoPrimitivo", kw_char);
        map.get("TipoPrimitivo", kw_int);
        map.get("ListaDecAtrs", idMetVar);
        map.get("EstaticoOpt", kw_static);
        //map.get("TipoMetodo", )
        map.get("ArgsFormales", punt_parentIzq);
        map.get("ListaArgsFormalesOpt", punt_parentIzq);
        //map.get("ListaArgsFormalesOpt", kw_void); epsilon ??
        map.get("ArgFormal", kw_public);
        map.get("ArgFormal", kw_private);
        map.get("Bloque", punt_llaveIzq);
        //map.get("ListaSentencias", )
        //map.get("Sentencia", );
        //map.get("Asignacion", );
        map.get("TipoDeAsignacion", asignacion);
        map.get("TipoDeAsignacion", incremento);
        map.get("TipoDeAsignacion", decremento);
        map.get("Llamada", kw_this);
        map.get("Llamada", idMetVar);
        map.get("Llamada", kw_new);
        map.get("Llamada", punt_parentIzq);
        map.get("Llamada", idClase);
        //Faltan de llamada

        map.get("VarLocal", kw_var);
        map.get("Return", kw_return);
        //map.get("ExpresionOpt",);
        map.get("If", kw_if);
        map.get("While", kw_while);
        //map.get("Expresion", );
        //map.get("ExpresionRec", );
        map.get("OperadorBinario", op_or);
        map.get("OperadorBinario", op_and);
        map.get("OperadorBinario", op_igual);
        map.get("OperadorBinario", op_distinto);
        map.get("OperadorBinario", op_menor);
        map.get("OperadorBinario", op_mayor);
        map.get("OperadorBinario", op_mayorIgual);
        map.get("OperadorBinario", op_menorIgual);
        map.get("OperadorBinario", op_suma);
        map.get("OperadorBinario", op_resta);
        map.get("OperadorBinario", op_multiplicacion);
        map.get("OperadorBinario", op_division);
        map.get("OperadorBinario", op_modulo);
        //map.get("ExpresionUnaria", );
        //map.get("ExpresionUnaria", )
        *//*map.get("Operando", asignacion);
        map.get("Operando", op_suma);
        map.get("Operando", op_resta);
        map.get("Operando", op_negacion);
        map.get("Operando", kw_this);
        map.get("Operando", idMetVar);
        map.get("Operando", kw_new);
        map.get("Operando", punt_parentIzq);
        map.get("Operando", asignacion);
        map.get("Operando", asignacion);*//* //Flashe y lo puse 2 veces con 2 cosas distintas, ver que onda


        map.get("OperadorUnario", asignacion);
        map.get("OperadorUnario", op_suma);
        map.get("OperadorUnario", op_resta);
        map.get("OperadorUnario", op_negacion);
        map.get("Literal", kw_null);
        map.get("Literal", kw_true);
        map.get("Literal", kw_false);
        map.get("Literal", intLiteral);
        map.get("Literal", charLiteral);
        map.get("Literal", stringLiteral);
        //map.get("Acceso", )
        map.get()*/















    }

    public boolean isFirst(String production, TokenId tokenId){
        return map.get(production).contains(tokenId);
    }
}

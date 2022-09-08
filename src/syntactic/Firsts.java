package syntactic;

import lexycal.TokenId;

import java.util.HashMap;
import java.util.Map;

import static lexycal.TokenId.*; //Static? mmm

public class Firsts {

    Map<String, TokenId> map = new HashMap<String, TokenId>();

    public Firsts(){//Si no matchea por algo es por el static
        map.put("Inicial", kw_class);
        map.put("Inicial", kw_interface);
        map.put("ListaClases", kw_class);
        map.put("ListaClases", kw_interface);
        map.put("ListaClases", EOF);
        map.put("ClaseConcreta", kw_class);
        map.put("Interface", kw_interface);
        map.put("HeredaDe", kw_extends);
        map.put("ListaTipoReferencia", idClase);
        //map.put("ListaMiembros", )
        //map.put("ListaEncabezados", )
        //map.put("Miembro", )
        //map.put("Atributo", )
        //map.put("Metodo", )
        //map.put("EncabezadoMetodo", );
        //map.put("TipoMetodo", );
        map.put("Visibilidad", kw_public);
        map.put("Visibilidad", kw_private);
        map.put("Tipo", kw_boolean);
        map.put("Tipo", kw_char);
        map.put("Tipo", kw_int);
        map.put("Tipo", idClase);
        map.put("TipoPrimitivo", kw_boolean);
        map.put("TipoPrimitivo", kw_char);
        map.put("TipoPrimitivo", kw_int);
        map.put("ListaDecAtrs", idMetVar);
        map.put("EstaticoOpt", kw_static);
        //map.put("TipoMetodo", )
        map.put("ArgsFormales", punt_parentIzq);
        map.put("ListaArgsFormalesOpt", punt_parentIzq);
        //map.put("ListaArgsFormalesOpt", kw_void); epsilon ??
        map.put("ArgFormal", kw_public);
        map.put("ArgFormal", kw_private);
        map.put("Bloque", punt_llaveIzq);
        //map.put("ListaSentencias", )
        //map.put("Sentencia", );
        //map.put("Asignacion", );
        map.put("TipoDeAsignacion", asignacion);
        map.put("TipoDeAsignacion", incremento);
        map.put("TipoDeAsignacion", decremento);
        map.put("Llamada", kw_this);
        map.put("Llamada", idMetVar);
        map.put("Llamada", kw_new);
        map.put("Llamada", punt_parentIzq);
        map.put("Llamada", idClase);
        //Faltan de llamada

        map.put("VarLocal", kw_var);
        map.put("Return", kw_return);
        //map.put("ExpresionOpt",);
        map.put("If", kw_if);
        map.put("While", kw_while);
        //map.put("Expresion", );
        //map.put("ExpresionRec", );
        map.put("OperadorBinario", op_or);
        map.put("OperadorBinario", op_and);
        map.put("OperadorBinario", op_igual);
        map.put("OperadorBinario", op_distinto);
        map.put("OperadorBinario", op_menor);
        map.put("OperadorBinario", op_mayor);
        map.put("OperadorBinario", op_mayorIgual);
        map.put("OperadorBinario", op_menorIgual);
        map.put("OperadorBinario", op_suma);
        map.put("OperadorBinario", op_resta);
        map.put("OperadorBinario", op_multiplicacion);
        map.put("OperadorBinario", op_division);
        map.put("OperadorBinario", op_modulo);
        //map.put("ExpresionUnaria", );
        //map.put("ExpresionUnaria", )
        /*map.put("Operando", asignacion);
        map.put("Operando", op_suma);
        map.put("Operando", op_resta);
        map.put("Operando", op_negacion);
        map.put("Operando", kw_this);
        map.put("Operando", idMetVar);
        map.put("Operando", kw_new);
        map.put("Operando", punt_parentIzq);
        map.put("Operando", asignacion);
        map.put("Operando", asignacion);*/ //Flashe y lo puse 2 veces con 2 cosas distintas, ver que onda


        map.put("OperadorUnario", asignacion);
        map.put("OperadorUnario", op_suma);
        map.put("OperadorUnario", op_resta);
        map.put("OperadorUnario", op_negacion);
        map.put("Literal", kw_null);
        map.put("Literal", kw_true);
        map.put("Literal", kw_false);
        map.put("Literal", intLiteral);
        map.put("Literal", charLiteral);
        map.put("Literal", stringLiteral);
        map.put("Acceso", )















    }
}

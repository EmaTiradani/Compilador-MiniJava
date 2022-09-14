package syntactic;

import lexycal.Token;
import lexycal.TokenId;

import java.util.*;

import static lexycal.TokenId.*; //Static? mmm

public class Firsts {

    //Map<String, TokenId> map = new HashMap<String, TokenId>();
    // Hay que cambiar esto por Map<Object,ArrayList<Object>> multiMap = new HashMap<>();
    Map<String, ArrayList<TokenId>> map = new HashMap<String, ArrayList<TokenId>>();
    // Despues le pido la lista de primeros y listo, pero hay que armar la lista de primeros, F.


    public Firsts(){//Si no matchea por algo es por el static
        map.put("Inicial", new ArrayList<>());
        map.put("ListaClases", new ArrayList<>());
        map.put("Clase", new ArrayList<>());
        map.put("ClaseConcreta", new ArrayList<>());
        map.put("Interface", new ArrayList<>());
        map.put("HeredaDe", new ArrayList<>());
        map.put("ImplementaA", new ArrayList<>());
        map.put("ExtiendeA", new ArrayList<>());
        map.put("ListaTipoReferencia", new ArrayList<>());
        map.put("ListaMiembros", new ArrayList<>());
        map.put("ListaEncabezados", new ArrayList<>());
        map.put("Miembro", new ArrayList<>());
        map.put("Atributo", new ArrayList<TokenId>());
        map.put("Metodo", new ArrayList<TokenId>());
        map.put("EncabezadoMetodo", new ArrayList<TokenId>());
        map.put("Visibilidad", new ArrayList<TokenId>());
        map.put("Tipo", new ArrayList<TokenId>());
        map.put("TipoPrimitivo", new ArrayList<TokenId>());
        map.put("ListaDecAtrs", new ArrayList<TokenId>());
        map.put("ListaDecAtrsFact", new ArrayList<TokenId>());
        map.put("EstaticoOpt", new ArrayList<TokenId>());
        map.put("TipoMetodo", new ArrayList<TokenId>());
        map.put("ArgsFormales", new ArrayList<TokenId>());
        map.put("ListaArgsFormalesOpt", new ArrayList<TokenId>());
        map.put("ListaArgsFormales", new ArrayList<TokenId>());
        map.put("ListaArgsFormalesFact", new ArrayList<TokenId>());
        map.put("ArgFormal", new ArrayList<TokenId>());
        map.put("Bloque", new ArrayList<TokenId>());
        map.put("ListaSentencias", new ArrayList<TokenId>());
        map.put("Sentencia", new ArrayList<TokenId>());
        map.put("AsignacionOLlamada", new ArrayList<TokenId>());
        map.put("Asignacion", new ArrayList<TokenId>());
        map.put("TipoDeAsignacion", new ArrayList<TokenId>());
        map.put("Llamada", new ArrayList<TokenId>());
        map.put("VarLocal", new ArrayList<TokenId>());
        map.put("Return", new ArrayList<TokenId>());
        map.put("ExpresionOpt", new ArrayList<TokenId>());
        map.put("If", new ArrayList<TokenId>());
        map.put("While", new ArrayList<TokenId>());
        map.put("Expresion", new ArrayList<TokenId>());
        map.put("OperadorBinario", new ArrayList<>());
        map.put("ExpresionUnaria", new ArrayList<TokenId>());
        map.put("OperadorUnario", new ArrayList<TokenId>());
        map.put("Operando", new ArrayList<TokenId>());
        map.put("Literal", new ArrayList<TokenId>());
        map.put("Acceso", new ArrayList<TokenId>());
        map.put("Primario", new ArrayList<TokenId>());
        map.put("AccesoVarOMetodo", new ArrayList<TokenId>());
        map.put("AccesoThis", new ArrayList<TokenId>());
        map.put("AccesoVar", new ArrayList<TokenId>());
        map.put("AccesoConstructor", new ArrayList<TokenId>());
        map.put("ExpresionParentizada", new ArrayList<TokenId>());
        map.put("AccesoMetodo", new ArrayList<TokenId>());
        map.put("AccesoMetodoEstatico", new ArrayList<TokenId>());
        map.put("ArgsActuales", new ArrayList<TokenId>());
        map.put("ListaExpsOpt", new ArrayList<TokenId>());
        map.put("ListaExps", new ArrayList<TokenId>());
        map.put("ListaExpsFac", new ArrayList<TokenId>());
        map.put("EncadenadoOpt", new ArrayList<TokenId>());
        map.put("VarOMetEncadenado", new ArrayList<TokenId>());


        map.get("Inicial").add(kw_interface);
        map.get("Inicial").add(kw_class);
        map.get("ListaClases").add(kw_class);
        map.get("ListaClases").add(kw_interface);
        map.get("ListaClases").add(EOF); //Aca va el epsilon?
        map.get("ClaseConcreta").add(kw_class);
        map.get("Interface").add(kw_interface);
        map.get("HeredaDe").add(kw_extends);
        map.get("ListaTipoReferencia").add(idClase);
        map.get("ImplementaA").add(kw_implements);
        map.get("Miembro").add(kw_public);
        map.get("Miembro").add(kw_private);
        map.get("Miembro").add(kw_static);
        map.get("Miembro").add(kw_void);
        map.get("Miembro").add(kw_boolean);
        map.get("Miembro").add(kw_char);
        map.get("Miembro").add(kw_int);
        map.get("Miembro").add(idClase);

        //map.get("ListaMiembros", )
        //map.get("ListaEncabezados", )
        //map.get("Miembro", )
        //map.get("Atributo", )
        //map.get("Metodo", )
        //map.get("EncabezadoMetodo", );firsts.isFirst("HeredaDe", tokenActual)
        //map.get("TipoMetodo", );
        map.get("Visibilidad").add(kw_public);
        map.get("Visibilidad").add(kw_private);
        map.get("Tipo").add(kw_boolean);
        map.get("Tipo").add(kw_char);
        map.get("Tipo").add(kw_int);
        map.get("Tipo").add(idClase);
        map.get("TipoPrimitivo").add(kw_boolean);
        map.get("TipoPrimitivo").add(kw_char);
        map.get("TipoPrimitivo").add(kw_int);
        map.get("ListaDecAtrs").add(idMetVar);
        map.get("EstaticoOpt").add(kw_static);
        //map.get("TipoMetodo", )
        map.get("ArgsFormales").add(punt_parentIzq);
        map.get("ListaArgsFormalesOpt").add(punt_parentIzq);
        //map.get("ListaArgsFormalesOpt", kw_void); epsilon ??
        map.get("ArgFormal").add(kw_public);
        map.get("ArgFormal").add(kw_private);
        map.get("Bloque").add(punt_llaveIzq);
        //map.get("ListaSentencias", )
        map.get("Sentencia").add(kw_this);
        map.get("Sentencia").add(idMetVar);
        map.get("Sentencia").add(kw_new);
        map.get("Sentencia").add(idClase);
        map.get("Sentencia").add(punt_parentIzq);
        map.get("Sentencia").add(kw_var);
        map.get("Sentencia").add(kw_return);
        map.get("Sentencia").add(kw_if);
        map.get("Sentencia").add(kw_while);
        map.get("Sentencia").add(punt_llaveIzq);

        //map.get("Asignacion", );
        map.get("TipoDeAsignacion").add(asignacion);
        map.get("TipoDeAsignacion").add(incremento);
        map.get("TipoDeAsignacion").add(decremento);
        map.get("Llamada").add(kw_this);
        map.get("Llamada").add(idMetVar);
        map.get("Llamada").add(kw_new);
        map.get("Llamada").add(punt_parentIzq);
        map.get("Llamada").add(idClase);
        //Faltan de llamada

        map.get("VarLocal").add(kw_var);
        map.get("Return").add(kw_return);
        //map.get("ExpresionOpt",);
        map.get("If").add(kw_if);
        map.get("While").add(kw_while);
        //map.get("Expresion").add();
        //map.get("ExpresionRec", );
        map.get("OperadorBinario").add(op_or);
        map.get("OperadorBinario").add(op_and);
        map.get("OperadorBinario").add(op_igual);
        map.get("OperadorBinario").add(op_distinto);
        map.get("OperadorBinario").add(op_menor);
        map.get("OperadorBinario").add(op_mayor);
        map.get("OperadorBinario").add(op_mayorIgual);
        map.get("OperadorBinario").add(op_menorIgual);
        map.get("OperadorBinario").add(op_suma);
        map.get("OperadorBinario").add(op_resta);
        map.get("OperadorBinario").add(op_multiplicacion);
        map.get("OperadorBinario").add(op_division);
        map.get("OperadorBinario").add(op_modulo);
        map.get("ExpresionUnaria").add(asignacion);
        map.get("ExpresionUnaria").add(op_suma);
        map.get("ExpresionUnaria").add(op_resta);
        map.get("ExpresionUnaria").add(op_negacion);
        map.get("ExpresionUnaria").add(op_suma);
        map.get("ExpresionUnaria").add(op_resta);
        map.get("ExpresionUnaria").add(op_negacion);
        map.get("ExpresionUnaria").add(kw_this);
        map.get("ExpresionUnaria").add(idMetVar);
        map.get("ExpresionUnaria").add(kw_new);
        map.get("ExpresionUnaria").add(punt_parentIzq);
        map.get("ExpresionUnaria").add(asignacion);
        //map.get("ExpresionUnaria", )
        //*map.get("Operando", asignacion);
        map.get("Operando").add(op_suma);
        map.get("Operando").add(op_resta);
        map.get("Operando").add(op_negacion);
        map.get("Operando").add(kw_this);
        map.get("Operando").add(idMetVar);
        map.get("Operando").add(kw_new);
        map.get("Operando").add(punt_parentIzq);
        map.get("Operando").add(asignacion);
        map.get("Operando").add(asignacion);//* //Flashe y lo puse 2 veces con 2 cosas distintas, ver que onda


        map.get("OperadorUnario").add(asignacion);
        map.get("OperadorUnario").add(op_suma);
        map.get("OperadorUnario").add(op_resta);
        map.get("OperadorUnario").add(op_negacion);
        map.get("Literal").add(kw_null);
        map.get("Literal").add(kw_true);
        map.get("Literal").add(kw_false);
        map.get("Literal").add(intLiteral);
        map.get("Literal").add(charLiteral);
        map.get("Literal").add(stringLiteral);
        map.get("Acceso").add(idMetVar); //Los de primario

        map.get("Primario").add(kw_this);
        map.get("Primario").add(idMetVar);
        map.get("Primario").add(kw_new);
        map.get("Primario").add(idClase);
        map.get("Primario").add(punt_parentIzq);
        map.get("AccesoThis").add(kw_this);
        map.get("AccesoVar").add(idMetVar);
        map.get("AccesoConstructor").add(kw_new);
        map.get("ExpresionParentizada").add(punt_parentIzq);
        map.get("AccesoMetodo").add(idMetVar);
        map.get("AccesoMetodoEstatico").add(idClase);
        map.get("ArgsActuales").add(punt_parentIzq);
        //map.get("ListaExpsOpt").add()
        //map.get("ListaExps").add() //Lo de expresion
        map.get("EncadenadoOpt").add(punt_punto);
        map.get("VarOMetEncadenado").add(punt_punto);
        map.get("VarOMetEncadenado").add(punt_parentIzq);
















    }

    public boolean isFirst(String production, Token token){
        return map.get(production).contains(token.getTokenId());
    }
}

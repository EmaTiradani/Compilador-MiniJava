package Analizador_Lexico;

import java.util.Dictionary;

public enum TokenId {

    kw_class,
    kw_interface,
    kw_extends,
    kw_implements,
    kw_public,
    kw_private,
    kw_static,
    kw_void,
    kw_boolean,
    kw_char,
    kw_int,
    kw_if,
    kw_else,
    kw_while,
    kw_return,
    kw_var,
    kw_this,
    kw_new,
    kw_null,
    kw_true,
    kw_false,

    idClase,
    idMetVar,

    intLiteral,
    charLiteral,
    stringLiteral,


    punt_parentIzq,
    punt_parentDer,
    punt_llaveIzq,
    punt_llaveDer,
    punt_puntoYComa,
    punt_coma,
    punt_punto,

    op_mayor,
    op_menor,
    op_negacion,
    op_igual,
    op_mayorIgual,
    op_menorIgual,
    op_distinto,
    op_suma,
    op_resta,
    op_multiplicacion,
    op_division,
    op_and,
    op_or,
    op_modulo,

    asignacion,
    incremento,
    decremento,

    EOF

}

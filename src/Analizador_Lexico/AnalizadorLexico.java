package Analizador_Lexico;

import FileManager.FileManager;

import java.io.IOException;

public class AnalizadorLexico {

    String lexema;
    char caracterActual;
    FileManager fileManager;

    public AnalizadorLexico(FileManager fileManager){
        this.fileManager = fileManager;
    }

    public Token getToken(){
        lexema = "";
        return e0();
    }
//    /t es para tabs
    public Token e0() throws ExcepcionLexica{

        switch(caracterActual){
            case ' ' : return e0();

            case '"' : return e12();

            case ' ';

            default: throw new ExcepcionLexica(lexema, nroLinea);
        }
    }

    private  Token e12() throws IOException, ExcepcionLexica {

       actualizarLexema();

        /*if(caracterActual != '\n'){
            return
        }*/
        //SHITCH
        if(caracterActual == '\\'){
            return e13();
        }
        else{
            if(caracterActual == '\n')
        }

    }

    private Token e13() throws IOException, ExcepcionLexica {
        actualizarLexema();
        if(caracterActual == '\\'){
            e13();
        }
        else{
            if(caracterActual == '\n') throw new ExcepcionLexica();
        }
        else{

        }
    }

    private void actualizarLexema() throws IOException {
        lexema = lexema + caracterActual;
        caracterActual = fileManager.getNextChar();
    }
}

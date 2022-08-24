package Analizador_Lexico;

import FileManager.FileManager;

public class AnalizadorLexico {

    String lexema;
    char caracterActual;
    FileManager fileManager;

    public AnalizadorLexico(FileManager fileManager){
        this.fileManager = fileManager;
    }

    public Token getToken(){
        return new Token(new TokenId(), "nice", 3);
    }
}

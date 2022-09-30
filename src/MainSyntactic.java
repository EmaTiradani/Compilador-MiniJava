import TablaDeSimbolos.TablaDeSimbolos;
import exceptions.LexicalException;
import exceptions.SemanticException;
import exceptions.SyntacticException;
import fileManager.FileManager;
import lexycal.AnalizadorLexico;
import syntactic.SyntacticParser;

import java.io.FileNotFoundException;
import java.io.IOException;

public class MainSyntactic {

    public static void main(String[] args){

        //String sourceCodeFile = args[0];

        SyntacticParser syntacticParser = null;

        try {
            //FileManager fileManager = new FileManager(sourceCodeFile);
            FileManager fileManager = new FileManager("C:\\Users\\ema_c\\Desktop\\Compiladores\\Etapa 1\\Compilador\\MiniJavaSourceCode.txt"); //Cable
            //FileManager fileManager = new FileManager("C:\\Users\\default.LAPTOP-9ASHTB0Q\\Desktop\\Lenguajes\\Proyecto\\proyecto-ldp\\Compilador-MiniJava\\MiniJavaSourceCode.txt");

            AnalizadorLexico analizadorLexico = new AnalizadorLexico(fileManager);
            syntacticParser = new SyntacticParser(analizadorLexico);
            TablaDeSimbolos tablaDeSimbolos = new TablaDeSimbolos();
            syntacticParser.startAnalysis();
            TablaDeSimbolos.checkDec();
            TablaDeSimbolos.consolidar();
            TablaDeSimbolos.print();

            System.out.println("Compilacion exitosa\n\n[SinErrores]");
        }
        catch (LexicalException | SyntacticException | SemanticException e) {
            System.out.println(e.getMessage());
        }
        catch (FileNotFoundException e){
            System.out.println("No se encontro un archivo en el primer argumento");
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}

import Exceptions.ExcepcionLexica;
import Exceptions.SyntacticException;
import FileManager.FileManager;
import lexycal.AnalizadorLexico;
import syntactic.SyntacticParser;

import java.io.FileNotFoundException;
import java.io.IOException;

public class MainSyntactic {

    public static void main(String[] args){

        String sourceCodeFile = args[0];
        //String sourceCodeFile = "C:\\Users\\default.LAPTOP-9ASHTB0Q\\Desktop\\Lenguajes\\Proyecto\\proyecto-ldp\\Compilador-MiniJava\\MiniJavaSourceCode.txt";
        //String sourceCodeFile = "C:\\Users\\ema_c\\Desktop\\Compiladores\\Etapa 1\\Compilador\\MiniJavaSourceCode.txt" ;

        SyntacticParser syntacticParser = null;

        try {
            FileManager fileManager = new FileManager(sourceCodeFile);
            AnalizadorLexico analizadorLexico = new AnalizadorLexico(fileManager);
            syntacticParser = new SyntacticParser(analizadorLexico);
            syntacticParser.startAnalysis();//Puede producir null pointer

            System.out.println("Compilacion exitosa\n\n[SinErrores]");
        }
        catch (ExcepcionLexica | SyntacticException e) {
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

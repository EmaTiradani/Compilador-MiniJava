import exceptions.LexicalException;
import exceptions.SyntacticException;
import fileManager.FileManager;
import lexycal.AnalizadorLexico;
import syntactic.SyntacticParser;

import java.io.FileNotFoundException;
import java.io.IOException;

public class MainSyntactic {

    public static void main(String[] args){

        String sourceCodeFile = args[0];

        SyntacticParser syntacticParser = null;

        try {
            FileManager fileManager = new FileManager(sourceCodeFile);
            AnalizadorLexico analizadorLexico = new AnalizadorLexico(fileManager);
            syntacticParser = new SyntacticParser(analizadorLexico);
            syntacticParser.startAnalysis();

            System.out.println("Compilacion exitosa\n\n[SinErrores]");
        }
        catch (LexicalException | SyntacticException e) {
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

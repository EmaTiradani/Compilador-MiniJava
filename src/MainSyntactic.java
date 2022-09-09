import Exceptions.ExcepcionLexica;
import Exceptions.SyntacticException;
import FileManager.FileManager;
import lexycal.AnalizadorLexico;
import syntactic.SyntacticParser;

import java.io.IOException;

public class MainSyntactic {

    public static void main(String[] args) throws IOException {

        FileManager fileManager = new FileManager(args[0]);
        AnalizadorLexico analizadorLexico = new AnalizadorLexico(fileManager);

        SyntacticParser syntacticParser = null;
        try {
            syntacticParser = new SyntacticParser(analizadorLexico);
        } catch (ExcepcionLexica e) {
            System.out.println(e.getMessage());
        }

        try {
            syntacticParser.startAnalysis();//Puede producir null pointer
        } catch (ExcepcionLexica | SyntacticException e) {
            System.out.println(e.getMessage());
        }

    }
}

import FileManager.FileManager;
import lexycal.AnalizadorLexico;
import syntactic.SyntacticParser;

import java.io.IOException;

public class MainSyntactic {

    public static void main(String[] args) throws IOException {

        FileManager fileManager = new FileManager(args[0]);
        AnalizadorLexico analizadorLexico = new AnalizadorLexico(fileManager);
        SyntacticParser syntacticParser = new SyntacticParser(analizadorLexico);

        syntacticParser.startAnalysis();

    }
}

import TablaDeSimbolos.TablaDeSimbolos;
import exceptions.LexicalException;
import exceptions.SemanticException;
import exceptions.SyntacticException;
import fileManager.FileManager;
import lexycal.AnalizadorLexico;
import syntactic.SyntacticParser;
import java.io.*;

import java.io.FileNotFoundException;
import java.io.IOException;

public class MainSyntactic {

    public static void main(String[] args){

        //String sourceCodeFile = args[0];

        SyntacticParser syntacticParser = null;

        try {
            //FileManager fileManager = new FileManager(sourceCodeFile);
            // Lo de abajo son cables para testing
            FileManager fileManager = new FileManager("C:\\Users\\ema_c\\Desktop\\Compiladores\\Etapa 1\\Compilador\\MiniJavaSourceCode.java"); //Cable
            //FileManager fileManager = new FileManager("C:\\Users\\default.LAPTOP-9ASHTB0Q\\Desktop\\Lenguajes\\Proyecto\\proyecto-ldp\\Compilador-MiniJava\\MiniJavaSourceCode.java");

            AnalizadorLexico analizadorLexico = new AnalizadorLexico(fileManager);
            syntacticParser = new SyntacticParser(analizadorLexico);
            TablaDeSimbolos tablaDeSimbolos = new TablaDeSimbolos();
            syntacticParser.startAnalysis(); // Primera pasada
            // Segunda pasada
            TablaDeSimbolos.checkDec();
            TablaDeSimbolos.consolidar();
            // Chequeo de sentencias
            TablaDeSimbolos.checkSentencias();


            generate(null);

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

    private static void generate(String outputFileName) throws IOException {
        File file;
        FileWriter writer;
        BufferedWriter bufferedWriter;
        PrintWriter printWriter;

        TablaDeSimbolos.generar();

        try{
            if(outputFileName == null){
                file = new File("Output_File.txt");
            }else{
                file = new File(outputFileName);
            }
            writer = new FileWriter(file);
            bufferedWriter = new BufferedWriter(writer);
            printWriter = new PrintWriter(bufferedWriter);

            //writer.write("Hola");

            for(String instruction : TablaDeSimbolos.listaInstrucciones){
                writer.write(instruction);
                writer.write("\n");
            }

            writer.close();
            bufferedWriter.close();

        } catch (Exception e){
            System.out.println("Error al generar el archivo de salida");
        }
    }
}

package uk.ac.gla.teamL.actions;

import chrriis.grammar.model.*;
import chrriis.grammar.rrdiagram.*;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.psi.PsiFile;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by Rosie on 02/02/2015.
 */
public class createDiagram extends AnAction{

    @Override
    public void actionPerformed(@NotNull AnActionEvent anActionEvent) {
        PsiFile file = anActionEvent.getData(LangDataKeys.PSI_FILE);
        //System.out.println("file name: "+file.getName());
        if (file.getName().endsWith(".bnf")){
            Reader reader = null;
            Grammar grammar = null;
            String svg;

            try {
                reader = new BufferedReader(new FileReader("C:\\Users\\Rosie\\IdeaProjects\\EBNFTest\\src\\Test.bnf"));
            } catch (FileNotFoundException e) {
                System.err.println("could not read file");
                e.printStackTrace();
            }

            BNFToGrammar bnfToGrammar = new BNFToGrammar();

            try {
                grammar = bnfToGrammar.convert(reader);
            } catch (IOException e) {
                System.err.println("could not convert to grammar");
                e.printStackTrace();
            } finally {
                if(reader != null)
                {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        System.err.println("could not close reader");
                        e.printStackTrace();
                    }
                }
            }

            String svgObjs ="";
            GrammarToRRDiagram grammarToRRDiagram = new GrammarToRRDiagram();
            for(Rule rule: grammar.getRules()) {
                RRDiagram rrDiagram = grammarToRRDiagram.convert(rule);
                RRDiagramToSVG rrDiagramToSVG = new RRDiagramToSVG();
                svg = rrDiagramToSVG.convert(rrDiagram);

                //add each svg object to a string to be written to a html file later
                svgObjs = svgObjs+("\n<br>\n" + svg.toString());

            }
            createFile(svgObjs, file);
        }else{
            //not a bnf file
        }
    }

    public void createFile(String svg, PsiFile file){

        if (file.getName().endsWith(".bnf")) {

            String name = file.getName().split("\\.")[0];
            Path path = Paths.get(file.getContainingDirectory().getVirtualFile().getPath(), name + ".html");
            try {
                if (!Files.exists(path)) {
                    path = Files.createFile(path);
                }

                //create the html header and footer and put svg content inbetween
                String htmlHeader = "<!DOCTYPE html>\n" + "<html>\n" + "<body>\n";
                String htmlFooter = "\n</body>\n" + "</html>";
                String all = htmlHeader+svg+htmlFooter;

                Files.write(path, all.getBytes());
            } catch (IOException e) {
                System.err.println("cannot write to file");
                //e.printStackTrace();
            }
        }

    }
}

package uk.ac.gla.teamL.actions;

import chrriis.grammar.model.*;
import chrriis.grammar.rrdiagram.*;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.annotations.NotNull;
import uk.ac.gla.teamL.EBNFFile;
import uk.ac.gla.teamL.editor.Annotations;
import uk.ac.gla.teamL.psi.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.intellij.psi.util.PsiTreeUtil.findChildrenOfAnyType;
import static uk.ac.gla.teamL.EBNFUtil.notNull;

/**
 * Created by Rosie on 02/02/2015.
 */
public class createDiagram extends AnAction{

    private static enum Type {
        lexer, literal, parser
    }

    Map<String, String> canonicalNames;
    Map<String, Type> type;

    String indent;


    @Override
    public void actionPerformed(@NotNull AnActionEvent anActionEvent) {
        PsiFile file = anActionEvent.getData(LangDataKeys.PSI_FILE);
        //System.out.println("file name: "+file.getName());
        if (file.getName().endsWith(".bnf")){

            String svgObjs = createDiagram(file.getVirtualFile().getPath(),null);
            createFile(svgObjs, file);

        }else if(file.getName().endsWith(".ebnf")){
            //translate ebnf file to bnf then pass back

            String translate = translate((EBNFFile)file);
            Reader readme = new StringReader(translate);

            String svgObjs = createDiagram("",readme);
            createFile(svgObjs, file);

        }else{
            //not a bnf file
        }
    }

    public String createDiagram(String file, Reader read){

        Reader reader= null;
        Grammar grammar = null;
        String svg;

        if(file.equals("")){
            reader = read;
        }else if(read==null){
            try {
                reader = new BufferedReader(new FileReader(file));
            } catch (FileNotFoundException e) {
                System.err.println("could not read file");
                e.printStackTrace();
            }
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
            svgObjs = svgObjs +("\n<br><br>" + "<font face=\"Veranda\" ><b>" + rule.getName() + "</b></font><br>" + svg.toString());

        }
        //System.out.println(svgObjs);
        return svgObjs;
    }


    public void createFile(String svg, PsiFile file){

        if (file.getName().endsWith(".bnf")||file.getName().endsWith(".ebnf")) {

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

    /////////////////////////////////////////////////////////////////////////////////////////
    ///////////////EBNF->BNF translator
    ////////////////////////////////////////////////////////////////////////////////


    public String translate(EBNFFile file) {
        StringBuilder builder = new StringBuilder();
        generate(file, builder);
        return builder.toString();
    }

    private void generate(EBNFFile program, StringBuilder builder) {
        this.canonicalNames = new HashMap<>();
        this.type = new HashMap<>();

        int indentSize = 4;

        {
            StringBuilder indentBuilder = new StringBuilder();
            for (int i = 0; i < indentSize; i++) {
                indentBuilder.append(" ");
            }
            indent = indentBuilder.toString();
        }

        builder.append("(* Auto Generated Code. *)\n(* Translates eBNF to (RR)BNF *)\n\n");

        for (EBNFAssignment assignment: notNull(findChildrenOfAnyType(program, EBNFAssignment.class))) {
            String name = assignment.getName().toLowerCase();

            if (isLiteral(assignment)) {
                this.canonicalNames.put(name, name);
                this.type.put(name, Type.literal);
            } else if (isLexRule(assignment)) {
                this.canonicalNames.put(name, name);
                this.type.put(name, Type.lexer);
            } else {
                String newName = Character.toLowerCase(name.charAt(0)) + assignment.getName().substring(1);
                this.canonicalNames.put(name, newName);
                this.type.put(name, Type.parser);
            }
        }

        List<StringBuilder> Rules = new ArrayList<>();


        for (EBNFAssignment assignment: notNull(findChildrenOfAnyType(program, EBNFAssignment.class))) {
            StringBuilder rule = new StringBuilder();

            generateRule(assignment, rule);
            Rules.add(rule);
        }

        generateAllRules(builder,Rules);
    }

    private void generateAllRules(StringBuilder builder, List<StringBuilder> rules) {
        if (rules.size() == 0) {
            return;
        }
        for (StringBuilder rule : rules) {
            builder.append(rule.toString());
            builder.append("\n");
        }
    }


    private void generateRule(EBNFAssignment assignment, StringBuilder builder) {
        List<EBNFAnnotation> annotations = assignment.getAnnotationList();
        boolean isIgnored = hasAnnotation(assignment, Annotations.ignored);

        builder.append(this.canonicalNames.get(assignment.getName().toLowerCase()));
        builder.append(" ::= ");

        generateRules(assignment.getRules(), builder);

        if (isIgnored) {
            switch (this.type.get(assignment.getName().toLowerCase())) {
                case lexer:
                    builder.append("(* Ignored *)");
                    break;
                case parser:
                    builder.append("(* Cannot ignore parser rules. *)");
                    break;
                case literal:
                    builder.append("(* Ignored *)");
                    break;
            }
        }
        builder.append(";");

        builder.append("\n");
    }

    private void generateRules(EBNFRules rule, StringBuilder builder) {

        for (PsiElement element : rule.getChildren()) {
            if (element instanceof EBNFRulesSegment) {
                generateRuleSegment((EBNFRulesSegment) element, builder);
            } else if (element instanceof EBNFOr) {
                builder.append("|");
            } else {
                System.err.println(
                        "Rule structure doesnt match expected structure for " +
                                this.getClass().getName() +
                                " check generateRules()."
                );
            }
            builder.append(" ");
        }
    }

    private void generateRuleSegment(EBNFRulesSegment rulesSegment, StringBuilder builder) {
        for (PsiElement element : rulesSegment.getChildren()) {
            if (element instanceof EBNFRuleElement) {
                generateRuleElement((EBNFRuleElement) element, builder);
            } else {
                System.err.println(
                        "Rule structure doesnt match expected structure for " +
                                this.getClass().getName() +
                                " check generateRuleSegment()."
                );
            }
        }
    }

    private void generateRuleElement(EBNFRuleElement ruleElement, StringBuilder builder) {
        EBNFPredicate predicate = ruleElement.getPredicate();
        boolean negated = predicate != null && predicate.getText().contains("!");

        if (negated) {
            builder.append("!");
        }

        // So you can copy for the arbitrary quantifier.
        StringBuilder local = new StringBuilder();

        if (ruleElement.getAny() != null) {
            local.append(".");
        } else if (ruleElement.getIdentifier() != null) {
            local.append(this.canonicalNames.get(ruleElement.getIdentifier().getName().toLowerCase()));
        } else if (ruleElement.getRange() != null) {
            EBNFRange range = ruleElement.getRange();
            local.append(createString(range.getGetLowerBound().getString()));
            local.append("..");
            local.append(createString(range.getGetUpperBound().getString()));
        } else if (ruleElement.getString() != null) {
            local.append(createString(ruleElement.getString().getString()));
        } else if (ruleElement.getNestedRules() != null){
            local.append("( ");
            generateRules(ruleElement.getNestedRules().getRules(), local);
            local.append(")");
        }

        if (ruleElement.getQuantifier() != null) {
            if (ruleElement.getQuantifier().getArbitraryQuantifier() != null) {
                EBNFArbitraryQuantifier arbitraryQuantifier = ruleElement.getQuantifier().getArbitraryQuantifier();

                int lowerBound = arbitraryQuantifier.getGetLowerBound().getValue();

                for (int i = 0; i < lowerBound; i++) {
                    builder.append(local).append(" ");
                }

                if (arbitraryQuantifier.getGetUpperBound() != null) {

                    int upperBound = arbitraryQuantifier.getGetUpperBound().getValue();

                    int openedNested = 0;
                    for (int i = lowerBound; i < upperBound; i++) {
                        builder.append("(");
                        builder.append(local);
                        openedNested++;
                    }

                    for (int i = openedNested; i > 0; i--) {
                        builder.append(")?");
                    }

                } else {
                    builder.append("*");
                }

            } else {
                builder.append(local);
                switch (ruleElement.getQuantifier().getText()) {
                    case "?":
                        builder.append("? ");
                        break;
                    case "+":
                        builder.append("+ ");
                        break;
                    case "*":
                        builder.append("* ");
                        break;
                }
            }
        } else {
            builder.append(local).append(" ");
        }
    }

    public static String createString(String string) {
        StringBuilder builder = new StringBuilder();
        boolean containsSingle = string.contains("'");
        boolean containsDouble = string.contains("\"");

        //for the case when ''' occurs, need to change to "'" in order for it to be read
        if (containsSingle && !containsDouble){
            builder.append("\"");
            builder.append(string);
            builder.append("\"");
        } else if (containsDouble && !containsSingle) {
            builder.append("\'");
            builder.append(string);
            builder.append("\'");
        } else {
            builder.append('"');
            builder.append(string.replace("\"", "\\\""));
            builder.append('"');
        }

        return builder.toString();
    }


    public static boolean isLiteral(EBNFAssignment assignment) {
        return hasAnnotation(assignment, Annotations.literal);
    }


    public static boolean isLexRule(EBNFAssignment assignment) {
        return hasAnnotation(assignment, Annotations.ignored)
                || PsiTreeUtil.findChildrenOfType(assignment.getRules(), EBNFIdentifier.class).size() <= 0
                && PsiTreeUtil.findChildrenOfType(assignment.getRules(), EBNFAny.class).size() <= 0;
    }

    public static boolean hasAnnotation(EBNFAssignment assignment, Annotations label) {
        List<EBNFAnnotation> annotations = assignment.getAnnotationList();

        for (EBNFAnnotation annotation: annotations) {
            if (annotation.getName().equals(label.identifier)) {
                return true;
            }
        }

        return false;
    }
}

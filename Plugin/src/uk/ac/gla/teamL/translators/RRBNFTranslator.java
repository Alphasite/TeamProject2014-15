package uk.ac.gla.teamL.translators;

import com.intellij.psi.PsiElement;
import com.intellij.psi.util.PsiTreeUtil;
import uk.ac.gla.teamL.EBNFFile;
import uk.ac.gla.teamL.editor.Annotations;
import uk.ac.gla.teamL.psi.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.intellij.psi.util.PsiTreeUtil.findChildrenOfAnyType;
import static uk.ac.gla.teamL.EBNFUtil.notNull;

/**
 * User: nishad
 * Date: 12/12/14
 * Time: 13:27
 *
 * Edited by Rosie to translate to (RR)BNF instead of Antlr3
 * 23/01/15
 */

public class RRBNFTranslator implements Translator {

    private static enum Type {
        lexer, literal, parser
    }

    Map<String, String> canonicalNames;
    Map<String, Type> type;

    String indent;

    @Override
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
                    builder.append("(* $channel=HIDDEN *)");
                    break;
                case parser:
                    builder.append("(* Cannot ignore parser rules. *)");
                    break;
                case literal:
                    builder.append("(* $channel=HIDDEN *)");
                    break;
            }
        }
        builder.append(";");

        builder.append("\n");
    }

    private void generateRules(EBNFRules rule, StringBuilder builder) {

        for (PsiElement element : rule.getChildren()) {
            if (element instanceof EBNFRuleElement) {
                generateRuleElement((EBNFRuleElement) element, builder);
            } else if (element instanceof EBNFOr) {
                builder.append("|");
            }
            builder.append(" ");
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
                        builder.append("?");
                        break;
                    case "+":
                        builder.append("+");
                        break;
                    case "*":
                        builder.append("*");
                        break;
                }
            }
        } else {
            builder.append(local).append(" ");
        }
    }

    public static String createString(String string) {
        StringBuilder builder = new StringBuilder();

        //for the case when ''' occurs, need to change to "'" in order for it to be read
        if(string.equals("'")){
            builder.append("\"");
            builder.append(string);
            builder.append("\"");
        }else {
            builder.append("\'");
            builder.append(string);
            builder.append("\'");
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

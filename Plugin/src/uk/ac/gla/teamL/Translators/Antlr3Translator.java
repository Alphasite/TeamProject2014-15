package uk.ac.gla.teamL.translators;

import com.intellij.psi.PsiElement;
import com.intellij.psi.util.PsiTreeUtil;
import uk.ac.gla.teamL.EBNFFile;
import uk.ac.gla.teamL.parser.psi.*;

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
 */
public class Antlr3Translator implements Translator {
    Map<String, String> canonicalNames;
    Map<String, Boolean> isLexRule;

    @Override
    public String translate(EBNFFile file) {
        StringBuilder builder = new StringBuilder();
        generate(file, builder);
        return builder.toString();
    }

    private void generate(EBNFFile program, StringBuilder builder) {
        this.canonicalNames = new HashMap<>();
        this.isLexRule = new HashMap<>();

        builder.append("// Auto Generated Code. \n\n");
        builder.append("grammar ");
        builder.append(program.getVirtualFile().getNameWithoutExtension());
        builder.append(";");
        builder.append("\n");

        for (EBNFAssignment assignment: notNull(findChildrenOfAnyType(program, EBNFAssignment.class))) {
            String name = assignment.getName().toLowerCase();

            if (isLexRule(assignment)) {
                this.canonicalNames.put(name, name.toUpperCase());
                this.isLexRule.put(name, true);
            } else {
                String newName = Character.toLowerCase(name.charAt(0)) + assignment.getName().substring(1);
                this.canonicalNames.put(name, newName);
                this.isLexRule.put(name, false);
            }
        }

        List<StringBuilder> lexRules = new ArrayList<>();
        List<StringBuilder> parseRules = new ArrayList<>();

        for (EBNFAssignment assignment: notNull(findChildrenOfAnyType(program, EBNFAssignment.class))) {
            StringBuilder rule = new StringBuilder();
            generateRule(assignment, rule);

            if (this.isLexRule.get(assignment.getName().toLowerCase())) {
                lexRules.add(rule);
            } else {
                parseRules.add(rule);
            }
        }

        builder.append("\n// Parser Rules \n");
        for (StringBuilder rule: parseRules) {
            builder.append(reformatLine(rule.toString()));
            builder.append("\n");
        }

        builder.append("\n// Lexer Rules \n");
        for (StringBuilder rule: lexRules) {
            builder.append(rule);
        }
    }

    private void generateRule(EBNFAssignment assignment, StringBuilder builder) {
        List<EBNFAnnotation> annotations = assignment.getAnnotationList();
        boolean isIgnored = false;

        for (EBNFAnnotation annotation: annotations) {
            switch (annotation.getName()) {
                case "Ignored":
                    isIgnored = true;
                    break;
                default:
                    // Do nothing.
                    break;
            }
        }

        builder.append(this.canonicalNames.get(assignment.getName().toLowerCase()));
        builder.append(" : ");

        generateRules(assignment.getRules(), builder);

        builder.append(";");

        // TODO check if capitalisation is correct for the name.
        if (isIgnored) {
            builder.append("\t { $channel=HIDDEN; }");
        }

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
            builder.append("~");
        }

        // So you can copy for the arbitrary quantifier.
        StringBuilder local = new StringBuilder();

        if (ruleElement.getAny() != null) {
            local.append(".");
        } else if (ruleElement.getIdentifier() != null) {
            local.append(ruleElement.getIdentifier().getName());
        } else if (ruleElement.getRange() != null) {
            EBNFRange range = ruleElement.getRange();
            local.append("'").append(range.getGetLowerBound().getString()).append("'");
            local.append("..");
            local.append("'").append(range.getGetUpperBound().getString()).append("'");
        } else if (ruleElement.getString() != null) {
            local.append("\"");
            local.append(escapeString(ruleElement.getString().getString()));
            local.append("\"");
        } else if (ruleElement.getNestedRules() != null){
            local.append("( ");
            generateRules(ruleElement.getNestedRules().getRules(), local);
            local.append(")");
        }

        local.append(" ");

        if (ruleElement.getQuantifier() != null) {
            if (ruleElement.getQuantifier().getArbitraryQuantifier() != null) {
                EBNFArbitraryQuantifier arbitraryQuantifier = ruleElement.getQuantifier().getArbitraryQuantifier();

                int lowerBound = arbitraryQuantifier.getGetLowerBound().getValue();

                for (int i = 0; i < lowerBound; i++) {
                    builder.append(local);
                }


                /* Get it in the form a{2,4}
                 * a a (a(a)?)?
                 */
                if (arbitraryQuantifier.getGetUpperBound() != null) {

                    int upperBound = arbitraryQuantifier.getGetUpperBound().getValue();

                    int openedNested = 0;
                    for (int i = lowerBound; i < upperBound; i++) {
                        builder.append("(");
                        builder.append(arbitraryQuantifier);
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
            builder.append(local);
        }
    }

    public static String escapeString(String string) {
        return string.replace("\"", "\\\"");
    }

    public static boolean isLexRule(EBNFAssignment assignment) {
        return PsiTreeUtil.findChildrenOfType(assignment.getRules(), EBNFIdentifier.class).size() <= 0
            && PsiTreeUtil.findChildrenOfType(assignment.getRules(), EBNFAny.class).size() <= 0;
    }

    public static String reformatLine(String line) {
        if (line.length() > 60) {
            int offset = line.indexOf(":");

            StringBuilder newLine = new StringBuilder(line.substring(0, offset));

            String offsetString; {
                StringBuilder offsetStringBuilder = new StringBuilder("\n");
                for (int i = 0; i < offset; i++) {
                    offsetStringBuilder.append(" ");
                }
                offsetString = offsetStringBuilder.toString();
            }

            boolean inString = false;
            boolean escape = false;

            for (char c: line.substring(offset).toCharArray()) {
                if (escape) {
                    escape = false;
                } else {
                    switch (c) {
                        case '"':
                            inString = !inString;
                            break;

                        case '\\':
                            escape = true;
                            break;

                        case '|':
                        case '(':
                        case ')':
                            newLine.append(offsetString);
                            break;
                        default:
                            break;
                    }
                }
                newLine.append(c);
            }

            return newLine.toString();
        } else {
            return line;
        }
    }
}

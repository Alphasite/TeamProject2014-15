package uk.ac.gla.teamL.translators;

import com.intellij.psi.PsiElement;
import com.intellij.psi.util.PsiTreeUtil;
import uk.ac.gla.teamL.EBNFFile;
import uk.ac.gla.teamL.editor.Annotations;
import uk.ac.gla.teamL.psi.*;

import java.util.*;

import static com.intellij.psi.util.PsiTreeUtil.findChildrenOfAnyType;
import static uk.ac.gla.teamL.EBNFUtil.notNull;
import static uk.ac.gla.teamL.translators.TranslatorUtils.hasAnnotation;

public class YaccTranslator {
    private static final String INDENT = " ";
    private static final int INDENT_SIZE = 30;

    private int identifierNumber = 1;

    Map<String, String> identifiers = new HashMap<>();
    Map<String, String> tokens = new HashMap<>();

    // terminal symbols
    StringBuilder terminals = new StringBuilder();
    // grammar rules
    StringBuilder nonTerminals = new StringBuilder();

    public void translate(EBNFFile file) {
        initNameTable(file);

        translateTerminals(file);
        translateNonTerminals(file);
    }

    public String getLex() {
        return "%{\n" +
                "#include <stdio.h>\n" +
                "#include <string.h>\n" +
                "#include \"y.tab.h\"\n" +
                "%}\n\n" + "%%\n\n" + terminals.toString() + "\n%%\n";
    }

    public String getYacc() {
        StringBuilder yacc = new StringBuilder();
        yacc.append("%{\n" +
                "#include <stdio.h>\n" +
                "#include <string.h>\n" +
                "\n" +
                "void yyerror(const char *str)\n" +
                "{\n" +
                "    fprintf(stderr,\"error: %s\\n\",str);\n" +
                "}\n" +
                "\n" +
                "int yywrap()\n" +
                "{\n" +
                "    return 1;\n" +
                "}\n\n" +
                "main()\n{\n" +
                "    yyparse();\n" +
                "}\n\n" +
                "%}\n\n" +
                "%token");

        for (String token : tokens.keySet())
            yacc.append(" ").append(token);

        yacc.append("\n\n%%\n\n").append(nonTerminals);

        return yacc.toString();
    }

    /**
     * Saves variable identifiers into identifiers HashMap
     * Converts terminal symbol identifiers to upper case
     *
     * @param file
     */
    private void initNameTable(EBNFFile file) {
        for (EBNFAssignment assignment : notNull(findChildrenOfAnyType(file, EBNFAssignment.class)))
            if (isTerminalSymbol(assignment))
                identifiers.put(assignment.getName(), assignment.getName().toUpperCase());
            else
                identifiers.put(assignment.getName(), assignment.getName());
    }

    /**
     * Appends terminal symbols
     *
     * @param file
     */
    public void translateTerminals(EBNFFile file) {
        for (EBNFAssignment assignment : notNull(findChildrenOfAnyType(file, EBNFAssignment.class)))
            if (isTerminalSymbol(assignment))
                if (hasAnnotation(assignment, Annotations.ignored))
                    translateTerminal("", assignment.getRules());
                else
                    translateTerminal(assignment.getName(), assignment.getRules());
    }

    private void translateNonTerminals(EBNFFile file) {
        for (EBNFAssignment assignment : notNull(findChildrenOfAnyType(file, EBNFAssignment.class))) {
            String name = assignment.getName();
            EBNFRules rules = assignment.getRules();

            if (!isTerminalSymbol(assignment))
                translateNonTerminal(name, rules, nonTerminals);
        }
    }

    /**
     * Checks if rule is terminal
     * Rule is terminal if it does not contain other identifiers
     *
     * @param assignment
     * @return
     */
    public static boolean isTerminalSymbol(EBNFAssignment assignment) {
        return PsiTreeUtil.findChildrenOfType(assignment.getRules(), EBNFIdentifier.class).size() <= 0;
    }

    private void addToken(String name, String rule) {
        tokens.put(rule, identifiers.get(name));
    }

    public void translateTerminal(String name, EBNFRules rules) {
        StringBuilder literal = new StringBuilder();
        translateTerminalRule(Arrays.asList(rules.getChildren()), literal);
        translateTerminal(name, literal.toString());
    }

    public void translateTerminal(String name, String rules) {
        if (! name.equals(""))
            addToken(name, rules);

        int indentOffset = terminals.length();
        terminals.append(rules);
        indentOffset = terminals.length() - indentOffset;

        if (name.equals("")) {
            terminals.append(getIndent(indentOffset)).append("/* ignored */\n");
        }
        else {
            terminals.append(getIndent(indentOffset)).append("return ".concat(identifiers.get(name)).concat(";\n"));
        }

    }

    private void translateTerminalRule(List<PsiElement> rules, StringBuilder output) {
        for (PsiElement element : rules) {
            if (element instanceof EBNFRange) {
                output.append("[")
                        .append(((EBNFRange) element).getGetLowerBound().getString())
                        .append("-")
                        .append(((EBNFRange) element).getGetUpperBound().getString())
                        .append("]");
            } else if (element instanceof EBNFString) {
                output.append('"').append(((EBNFString) element).getString()).append('"');
            } else if (element instanceof EBNFOr) {
                output.append("|");
            } else if (element instanceof EBNFQuantifier) {
                output.append(element.getFirstChild().getText());
            } else if (element instanceof EBNFAny) {
                output.append(element.getFirstChild().getText());
            } else if (element instanceof EBNFNestedRules) {
                output.append("(");
                translateTerminalRule(Arrays.asList(element.getChildren()), output);
                output.append(")");
            } else {
                translateTerminalRule(Arrays.asList(element.getChildren()), output);
            }
        }
    }

    /**
     * Adds a terminal rule with the specified name, rules and output
     *
     * @param name   name of the terminal rule
     * @param rules
     * @param output
     */
    private void translateNonTerminal(String name, EBNFRules rules, StringBuilder output) {
        StringBuilder append = new StringBuilder();
        int indentSize = output.length();
        output.append(identifiers.get(name)).append(":");
        indentSize = output.length() - indentSize;
        translateNonTerminalRule(rules, output, append, indentSize + 1);
        output.append(" ;\n\n");
        output.append(append);
    }

    /**
     * Translates EBNFRules to yacc
     *
     * @param rules
     * @param output
     * @param append
     * @param indentSize
     */
    private void translateNonTerminalRule(EBNFRules rules, StringBuilder output, StringBuilder append, int indentSize) {
        for (PsiElement element : rules.getChildren())
            if (element instanceof EBNFRulesSegment)
                for (EBNFRuleElement ruleElement : ((EBNFRulesSegment) element).getRuleElementList())
                    translateNonTerminalRuleElement(ruleElement, output, append, indentSize);
            else if (element instanceof EBNFOr)
                output.append("\n").append(getIndent(0, indentSize)).append("|");
    }

    /**
     * Translates individual rule elements into yacc
     *
     * @param ruleElement
     * @param output
     * @param append
     * @param indentSize
     */
    private void translateNonTerminalRuleElement(EBNFRuleElement ruleElement, StringBuilder output, StringBuilder append, int indentSize) {
        String quantifier = "";
        if (ruleElement.getQuantifier() != null)
            quantifier = ruleElement.getQuantifier().getText();

        output.append(" ");

        if (ruleElement.getString() != null) {
            String rule = "\"".concat(escapeString(ruleElement.getString().getString())).concat("\"").concat(quantifier);
            String existingIdentifier = tokens.get(rule);

            if (existingIdentifier != null) {
                output.append(existingIdentifier);
            } else {
                String identifier = createUniqueIdentifier(true);
                translateTerminal(identifier, rule);
                output.append(identifier);
            }
        } else if (ruleElement.getIdentifier() != null) {
            switch (quantifier) {
                case "*":
                    output.append(defineAnyNumberIdentifier(ruleElement.getIdentifier().getName(), append));
                    break;
                case "+":
                    output.append(defineOneOrMoreIdentifier(ruleElement.getIdentifier().getName(), append));
                    break;
                case "?":
                    output.append(defineOneOrNoneIdentifier(ruleElement.getIdentifier().getName(), append));
                    break;
                default:
                    output.append(identifiers.get(ruleElement.getIdentifier().getName()));
            }
        } else if (ruleElement.getNestedRules() != null) {
            String identifier = createUniqueIdentifier(false);
            translateNonTerminal(identifier, ruleElement.getNestedRules().getRules(), append);

            switch (quantifier) {
                case "*":
                    output.append(defineAnyNumberIdentifier(identifier, append));
                    break;
                case "+":
                    output.append(defineOneOrMoreIdentifier(identifier, append));
                    break;
                case "?":
                    output.append(defineOneOrNoneIdentifier(identifier, append));
                    break;
                default:
                    output.append(identifier);
            }
        }
    }

    private String createUniqueIdentifier(boolean terminal) {
        String identifier;

        do {
            String nr = Integer.toString(identifierNumber);

            if (terminal)
                identifier = "TOKEN_".concat(nr);
            else
                identifier = "rule_".concat(nr);

            identifierNumber++;
        } while (identifiers.containsKey(identifier));

        identifiers.put(identifier, identifier);
        return identifier;
    }

    private String defineAnyNumberIdentifier(String identifier, StringBuilder output) {
        String name = identifier.concat("_any");

        if (identifiers.get(name) == null) {
            int offset = output.length();
            output.append(name).append(": ");
            offset = output.length() - offset;

            output.append("/* empty */\n").append(getIndent(0, offset))
                    .append("| ")
                    .append(name).append(" ").append(identifiers.get(identifier)).append(" ;\n\n");
        } else {
            System.err.println("Name already taken");
        }

        return name;
    }

    private String defineOneOrNoneIdentifier(String identifier, StringBuilder output) {
        String name = identifier.concat("_one_or_none");

        if (identifiers.get(name) == null) {
            int offset = output.length();
            output.append(name).append(": ");
            offset = output.length() - offset;

            output.append("/* empty */\n").append(getIndent(0, offset))
                    .append("| ")
                    .append(identifiers.get(identifier)).append(" ;\n\n");
        } else {
            System.err.println("Name already taken");
        }

        return name;
    }

    private String defineOneOrMoreIdentifier(String identifier, StringBuilder output) {
        String name = identifier.concat("_one_or_more");

        if (identifiers.get(name) == null) {
            int offset = output.length();
            output.append(name).append(": ");
            offset = output.length() - offset;

            output.append(identifiers.get(identifier)).append("\n").append(getIndent(0, offset))
                    .append("| ")
                    .append(name).append(" ").append(identifiers.get(identifier)).append(" ;\n\n");
        } else {
            System.err.println("Name already taken");
        }

        return name;
    }

    public String getIndent(int offset) {
        return getIndent(offset, INDENT_SIZE);
    }

    public String getIndent(int offset, int size) {
        String indentation = "";
        for (int i = 0; i < Math.max(size - offset, 1); i++) indentation += INDENT;

        return indentation;
    }

    public static String escapeString(String string) {
        return string.replace("'", "\'");
    }
}

package uk.ac.gla.teamL.translators;

import com.intellij.psi.PsiElement;
import uk.ac.gla.teamL.EBNFFile;
import uk.ac.gla.teamL.editor.Annotations;
import uk.ac.gla.teamL.parser.EBNFParserUtil;
import uk.ac.gla.teamL.psi.*;

import java.util.*;

import static com.intellij.psi.util.PsiTreeUtil.findChildrenOfAnyType;
import static uk.ac.gla.teamL.EBNFUtil.notNull;
import static uk.ac.gla.teamL.translators.TranslatorUtils.*;

/**
 * User: nishad
 * Date: 12/12/14
 * Time: 13:27
 */
public class Antlr3Translator implements uk.ac.gla.teamL.translators.Translator {
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

        builder.append("// Auto Generated Code. \n\n");
        builder.append("grammar ");
        builder.append(program.getVirtualFile().getNameWithoutExtension());
        builder.append(";");
        builder.append("\n\n");

        //TODO maybe remove this.
        builder.append("options {\n\toutput=AST;\n}\n");

        for (EBNFAssignment assignment: notNull(findChildrenOfAnyType(program, EBNFAssignment.class))) {
            String name = assignment.getName().toLowerCase();

            if (isLiteral(assignment)) {
                this.canonicalNames.put(name, name.toUpperCase());
                this.type.put(name, Type.literal);
            } else if (isLexRule(assignment)) {
                this.canonicalNames.put(name, name.toUpperCase());
                this.type.put(name, Type.lexer);
            } else {
                String newName = Character.toLowerCase(name.charAt(0)) + assignment.getName().substring(1);
                this.canonicalNames.put(name, newName);
                this.type.put(name, Type.parser);
            }
        }

        List<StringBuilder> lexRules = new ArrayList<>();
        List<StringBuilder> parseRules = new ArrayList<>();
        List<StringBuilder> literals = new ArrayList<>();

        for (EBNFAssignment assignment: notNull(findChildrenOfAnyType(program, EBNFAssignment.class))) {
            StringBuilder rule = new StringBuilder();

            switch (this.type.get(assignment.getName().toLowerCase())) {
                case lexer:
                    generateRule(assignment, rule);
                    lexRules.add(rule);
                    break;
                case parser:
                    generateRule(assignment, rule);
                    parseRules.add(rule);
                    break;
                case literal:
                    generateLiteral(assignment, rule);
                    literals.add(rule);
                    break;
            }
        }

        generateLiterals(builder, literals);
        generateParserRules(builder, parseRules);
        generateLexerRules(builder, lexRules);
    }

    private void generateLexerRules(StringBuilder builder, List<StringBuilder> lexRules) {
        if (lexRules.size() == 0) {
            return;
        }

        builder.append("\n// Lexer Rules \n");
        for (StringBuilder rule: lexRules) {
            builder.append(reformatLine(rule.toString()));
        }
    }

    private void generateParserRules(StringBuilder builder, List<StringBuilder> parseRules) {
        if (parseRules.size() == 0) {
            return;
        }

        builder.append("\n// Parser Rules \n");
        for (StringBuilder rule: parseRules) {
            builder.append(reformatLine(rule.toString()));
            builder.append("\n");
        }
    }

    private void generateLiterals(StringBuilder builder, List<StringBuilder> literals) {
        if (literals.size() == 0) {
            return;
        }

        builder.append("\n// Literals \n");
        builder.append("tokens { \n");
        for (StringBuilder token: literals) {
            builder.append(indent);
            builder.append(token);
            builder.append("\n");
        }
        builder.append("}\n");
    }

    private void generateRule(EBNFAssignment assignment, StringBuilder builder) {
        boolean isIgnored = hasAnnotation(assignment, Annotations.ignored);

        builder.append(this.canonicalNames.get(assignment.getName().toLowerCase()));
        builder.append(": ");

        generateRules(assignment.getRules(), builder);

        // TODO check if capitalisation is correct for the name.
        if (isIgnored) {
            switch (this.type.get(assignment.getName().toLowerCase())) {
                case lexer:
                    builder.append("{$channel=HIDDEN;}");
                    break;
                case parser:
                    builder.append("// Cannot ignore parser rules.");
                    break;
                case literal:
                    builder.append("{$channel=HIDDEN;}");
                    break;
            }

        } else {
            switch (this.type.get(assignment.getName().toLowerCase())) {
                case parser:
                    generateParserRuleASTReWrite(
                        this.canonicalNames.get(assignment.getName().toLowerCase()),
                        assignment.getRules(),
                        builder
                    );

                    break;
                default:
                    break;
            }
        }

        builder.append(";");

        builder.append("\n");
    }

    public void generateLiteral(EBNFAssignment assignment, StringBuilder builder) {
        List<EBNFRuleElement> ruleElementList = assignment.getRules().getRuleElementList();
        int size = ruleElementList.size();

        if (size == 0 || ruleElementList.get(0).getString() == null) {
            builder.append("// Token: (");
            builder.append(assignment.getName());
            builder.append(") has no valid tokens.");
        } else {
            if (size > 1) {
                builder.append("// This token was truncated as it contained >1 children.");
            }

            EBNFString ruleElement = ruleElementList.get(0).getString();

            assert ruleElement != null;

            builder.append(this.canonicalNames.get(assignment.getName().toLowerCase()));
            builder.append(" = ");
            builder.append(createString(ruleElement.getString()));
            builder.append(";");
        }
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


                /* Get it in the form a{2,4}
                 * a a (a(a)?)?
                 */
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

    public void generateParserRuleASTReWrite(String name, EBNFRules ruleElementList, StringBuilder builder) {
        // TODO remove this or fix it to include named variables.

        builder.append("^(");
        builder.append(" '");
        builder.append(name);
        builder.append("' ");

        Map<String, Integer> identifiers = new HashMap<>();
        for (EBNFIdentifier identifier: EBNFParserUtil.findIdentifiers(ruleElementList)) {
            if (identifiers.containsKey(identifier.getName())) {
                identifiers.put(identifier.getName(), identifiers.get(identifier.getName()) + 1);
            } else {
                identifiers.put(identifier.getName(), 1);
            }
        }

        for (String identifier: identifiers.keySet()) {
            builder.append(this.canonicalNames.get(identifier.toLowerCase()));

            if (identifiers.get(identifier) > 1) {
                builder.append("*");
            }

            builder.append(" ");
        }

        builder.append(" )");

    }

    public static String createString(String string) {
        StringBuilder builder = new StringBuilder();

        builder.append('\'');
        builder.append(escapeString(string));
        builder.append('\'');

        return builder.toString();
    }

    public static String reformatLine(String line) {
        int offset = line.indexOf(":");
        StringBuilder newLine = new StringBuilder(line.substring(0, offset));

        String offsetString; {
            if (line.length() > 60) {

                StringBuilder offsetStringBuilder = new StringBuilder("\n");
                for (int i = 0; i < offset; i++) {
                    offsetStringBuilder.append(" ");
                }
                offsetString = offsetStringBuilder.toString();
            } else {
                offsetString = "";
            }
        }

        boolean inString = false;
        boolean escape = false;
        boolean inWhiteSpace = false;
        boolean inCommand = false;
        boolean inASTDeclaration = false;

        for (char c: line.substring(offset).toCharArray()) {
            if (c == ' ') {
                inWhiteSpace = true;
                continue;
            } else if (inWhiteSpace) {
                inWhiteSpace = false;

                if (c != ';' && !inCommand) {
                    newLine.append(" ");
                }
            }

            if (c == '^') {
                inASTDeclaration = true;
            }

            if (escape) {
                escape = false;
            } else if (inASTDeclaration) {
                if (c == ')') {
                    inASTDeclaration = false;
                }
            } else {
                switch (c) {
                    case '{':
                        if (!inString) {
                            inCommand = true;
                        }

                        break;

                    case '}':
                        if (!inString) {
                            inCommand = false;
                        }

                        break;

                    case '"':
                    case '\'':
                        if (!escape) {
                            inString = !inString;
                        }

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
    }
}

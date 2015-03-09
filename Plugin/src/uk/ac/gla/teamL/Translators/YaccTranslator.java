package uk.ac.gla.teamL.translators;

import uk.ac.gla.teamL.EBNFFile;
import uk.ac.gla.teamL.parser.psi.EBNFAssignment;
import uk.ac.gla.teamL.parser.psi.EBNFRuleElement;
import uk.ac.gla.teamL.parser.psi.EBNFString;

import java.util.List;

import static com.intellij.psi.util.PsiTreeUtil.findChildrenOfAnyType;
import static uk.ac.gla.teamL.EBNFUtil.notNull;

/**
 * Created by Henrikas Elsbergas on 27/01/15.
 */
public class YaccTranslator extends Translator {
    private static final String INDENT = " ";
    private static final int INDENT_SIZE = 30;

    private int declarationNumber = 1;

    @Override
    public String translate(EBNFFile file) {
        StringBuilder declarations = new StringBuilder();
        StringBuilder literals = new StringBuilder();
        StringBuilder rules = new StringBuilder();

        for (EBNFAssignment assignment : notNull(findChildrenOfAnyType(file, EBNFAssignment.class))) {
            String name = assignment.getName().toLowerCase();

            if (isLiteral(assignment)) {
                appendDeclaration(assignment, declarations);
                appendLiteral(assignment, literals);
            } else if (isLexRule(assignment)) {

            } else {

            }
        }

        return declarations.toString()
                .concat("%%")
                .concat("\n")
                .concat(literals.toString())
                .concat("%%")
                .concat(rules.toString());
    }

    private void appendDeclaration(EBNFAssignment assignment, StringBuilder declarations) {
        List<EBNFRuleElement> ruleElementList = assignment.getRules().getRuleElementList();
        EBNFString ruleElement = ruleElementList.get(0).getString();

        assert ruleElement != null;

        declarations.append("#define").append(" ").append(ruleElement.toString().toUpperCase()).append(" ")
                .append(declarationNumber).append("\n");
    }

    public void appendLiteral(EBNFAssignment assignment, StringBuilder builder) {
        List<EBNFRuleElement> ruleElementList = assignment.getRules().getRuleElementList();
        EBNFString ruleElement = ruleElementList.get(0).getString();

        assert ruleElement != null;

        String regexp = createString(ruleElement.getString());
        builder.append(regexp).append(" ").append("return ".concat(assignment.getName().toUpperCase()).concat(";\n"));
    }

    public static String createString(String string) {
        StringBuilder builder = new StringBuilder();

        builder.append('\'');
        builder.append(escapeString(string));
        builder.append('\'');

        return builder.toString();
    }

    public String getIndent(String str) {
        String indentation = "";
        for (int i = 0; i < Math.max(INDENT_SIZE - str.length(), 1); i++) indentation += INDENT;

        return indentation;
    }
}

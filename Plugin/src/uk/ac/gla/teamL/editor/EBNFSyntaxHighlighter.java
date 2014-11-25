package uk.ac.gla.teamL.editor;

import com.intellij.lexer.Lexer;
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase;
import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NotNull;
import uk.ac.gla.teamL.parser.EBNFLexer;
import uk.ac.gla.teamL.parser.psi.EBNFTypes;

import static com.intellij.openapi.editor.colors.TextAttributesKey.createTextAttributesKey;

/**
 * User: nishad
 * Date: 16/11/14
 * Time: 23:25
 */
public class EBNFSyntaxHighlighter extends SyntaxHighlighterBase {

    public static final TextAttributesKey KEYWORDS = createTextAttributesKey("EBNF_KEYWORDS", DefaultLanguageHighlighterColors.KEYWORD);
    public static final TextAttributesKey IDENTIFIER = createTextAttributesKey("EBNF_OPERATORS", DefaultLanguageHighlighterColors.IDENTIFIER);
    public static final TextAttributesKey OPERATORS = createTextAttributesKey("EBNF_OPERATORS", DefaultLanguageHighlighterColors.OPERATION_SIGN);
    public static final TextAttributesKey BRACKETS = createTextAttributesKey("EBNF_BRACETS", DefaultLanguageHighlighterColors.BRACKETS);
    public static final TextAttributesKey TERMINAL = createTextAttributesKey("EBNF_TERMINAL", DefaultLanguageHighlighterColors.SEMICOLON);
    public static final TextAttributesKey STRING = createTextAttributesKey("EBNF_STRING", DefaultLanguageHighlighterColors.STRING);
    public static final TextAttributesKey BLOCK_COMMENT = createTextAttributesKey("EBNF_COMMENT", DefaultLanguageHighlighterColors.BLOCK_COMMENT);
    public static final TextAttributesKey LINE_COMMENT = createTextAttributesKey("EBNF_COMMENT", DefaultLanguageHighlighterColors.LINE_COMMENT);
    public static final TextAttributesKey NUMBER = createTextAttributesKey("EBNF_NUMBER", DefaultLanguageHighlighterColors.NUMBER);

    private static final TextAttributesKey[] KEYWORD_KEYS = new TextAttributesKey[]{KEYWORDS};
    private static final TextAttributesKey[] OPERATORS_KEYS = new TextAttributesKey[]{OPERATORS};
    private static final TextAttributesKey[] TERMINAL_KEYS = new TextAttributesKey[]{TERMINAL};
    private static final TextAttributesKey[] STRING_KEYS = new TextAttributesKey[]{STRING};
    private static final TextAttributesKey[] BLOCK_COMMENT_KEYS = new TextAttributesKey[]{BLOCK_COMMENT};
    private static final TextAttributesKey[] LINE_COMMENT_KEYS = new TextAttributesKey[]{LINE_COMMENT};
    private static final TextAttributesKey[] BRACKETS_KEYS = new TextAttributesKey[]{BRACKETS};
    private static final TextAttributesKey[] NUMBER_KEYS = new TextAttributesKey[]{NUMBER};
    private static final TextAttributesKey[] IDENTIFIERS_KEYS = new TextAttributesKey[]{IDENTIFIER};
    private static final TextAttributesKey[] EMPTY_KEYS = new TextAttributesKey[]{};


    @NotNull
    @Override
    public Lexer getHighlightingLexer() {
        return new EBNFLexer();
    }

    @NotNull
    @Override
    public TextAttributesKey[] getTokenHighlights(IElementType iElementType) {
        if (iElementType.equals(EBNFTypes.ASSIGNMENT)
                    | iElementType.equals(EBNFTypes.EQ)
                    | iElementType.equals(EBNFTypes.NEGATION_OPERATOR)
                    | iElementType.equals(EBNFTypes.ONE_OR_MORE)
                    | iElementType.equals(EBNFTypes.ZERO_OR_MORE)
                    | iElementType.equals(EBNFTypes.ZERO_OR_ONE)
                    | iElementType.equals(EBNFTypes.RANGE)
                    | iElementType.equals(EBNFTypes.ANY_OPERATOR)
                    | iElementType.equals(EBNFTypes.LIST_SEPERATOR)) {
            return OPERATORS_KEYS;
        } else if (iElementType.equals(EBNFTypes.STRING_DOUBLEQUOTES)
                    | iElementType.equals(EBNFTypes.STRING_SINGLEQUOTES)
                    | iElementType.equals(EBNFTypes.STRING_TRIPLEQUOTES)) {
            return STRING_KEYS;
        } else if (iElementType.equals(EBNFTypes.COMMENT_BLOCK)) {
            return BLOCK_COMMENT_KEYS;
        } else if (iElementType.equals(EBNFTypes.COMMENT_SINGLELINE)) {
            return LINE_COMMENT_KEYS;
        } else if (iElementType.equals(EBNFTypes.LCB)
                    | iElementType.equals(EBNFTypes.RCB)
                    | iElementType.equals(EBNFTypes.LSB)
                    | iElementType.equals(EBNFTypes.RSB)
                    | iElementType.equals(EBNFTypes.LB)
                    | iElementType.equals(EBNFTypes.LB)) {
            return BRACKETS_KEYS;
        } else if (iElementType.equals(EBNFTypes.TERMINAL)) {
            return TERMINAL_KEYS;
        } else if (iElementType.equals(EBNFTypes.ID)) {
            return IDENTIFIERS_KEYS;
        } else if (iElementType.equals(EBNFTypes.LET)) {
            return KEYWORD_KEYS;
        } else if (iElementType.equals(EBNFTypes.NUMBER)) {
            return NUMBER_KEYS;
        } else {
            return EMPTY_KEYS;
        }
    }
}

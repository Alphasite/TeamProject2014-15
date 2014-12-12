package uk.ac.gla.teamL;

import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.fileTypes.SyntaxHighlighter;
import com.intellij.openapi.options.colors.AttributesDescriptor;
import com.intellij.openapi.options.colors.ColorDescriptor;
import com.intellij.openapi.options.colors.ColorSettingsPage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import uk.ac.gla.teamL.editor.EBNFSyntaxHighlighter;

import javax.swing.*;
import java.util.Map;

/**
 * User: nishad
 * Date: 18/11/14
 * Time: 10:37
 */
public class EBNFColourSettingsPage implements ColorSettingsPage {
//    private static final TextAttributesKey[] OPERATORS_KEYS = new TextAttributesKey[]{OPERATORS};
//    private static final TextAttributesKey[] TERMINAL_KEYS = new TextAttributesKey[]{TERMINAL};
//    private static final TextAttributesKey[] STRING_KEYS = new TextAttributesKey[]{STRING};
//    private static final TextAttributesKey[] BLOCK_COMMENT_KEYS = new TextAttributesKey[]{BLOCK_COMMENT};
//    private static final TextAttributesKey[] LINE_COMMENT_KEYS = new TextAttributesKey[]{LINE_COMMENT};
//    private static final TextAttributesKey[] BRACKETS_KEYS = new TextAttributesKey[]{BRACKETS};
//    private static final TextAttributesKey[] IDENTIFIERS_KEYS = new TextAttributesKey[]{IDENTIFIER};
//    private static final TextAttributesKey[] EMPTY_KEYS = new TextAttributesKey[]{};

    private static final AttributesDescriptor[] DESCRIPTORS = new AttributesDescriptor[] {
        new AttributesDescriptor("Operators", EBNFSyntaxHighlighter.OPERATORS),
        new AttributesDescriptor("String", EBNFSyntaxHighlighter.STRING),
        new AttributesDescriptor("Brackets", EBNFSyntaxHighlighter.BRACKETS),
        new AttributesDescriptor("Block Comments", EBNFSyntaxHighlighter.BLOCK_COMMENT),
        new AttributesDescriptor("Line Comments", EBNFSyntaxHighlighter.LINE_COMMENT),
        new AttributesDescriptor("Identifiers", EBNFSyntaxHighlighter.IDENTIFIER),
        new AttributesDescriptor("Semi-Colon", EBNFSyntaxHighlighter.TERMINAL),
        new AttributesDescriptor("Keywords", EBNFSyntaxHighlighter.KEYWORDS),
        new AttributesDescriptor("Literals", EBNFSyntaxHighlighter.NUMBER),
        new AttributesDescriptor("Annotations", EBNFSyntaxHighlighter.ANNOTATION)
    };

    @Nullable
    @Override
    public Icon getIcon() {
        return EBNFIcon.FILE;
    }

    @NotNull
    @Override
    public SyntaxHighlighter getHighlighter() {
        return new EBNFSyntaxHighlighter();
    }

    @NotNull
    @Override
    public String getDemoText() {
        return ""+
                       "//Yo, this is a comment. \n" +
                       "let andThis = isARule* andAnother+ | orIsIt? .*; \n" +
                       "\n" +
                       "let thisIsARange = ['a'..'b']; \n" +
                       "let somePeople = \"like strings\"; \n" +
                       "@Ignored \n"+
                       "let otherPeople = '''Do not'''; \n" +
                       "\n" +
                       "/* Blocks on the other hand \n" +
                       " * suck \n" +
                       " */";
    }

    @Nullable
    @Override
    public Map<String, TextAttributesKey> getAdditionalHighlightingTagToDescriptorMap() {
        return null;
    }

    @NotNull
    @Override
    public AttributesDescriptor[] getAttributeDescriptors() {
        return DESCRIPTORS;
    }

    @NotNull
    @Override
    public ColorDescriptor[] getColorDescriptors() {
        return ColorDescriptor.EMPTY_ARRAY;
    }

    @NotNull
    @Override
    public String getDisplayName() {
        return "EBNF";
    }
}

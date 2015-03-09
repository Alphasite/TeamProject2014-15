package uk.ac.gla.teamL.editor.codeFormatter;

import com.intellij.formatting.*;
import com.intellij.lang.ASTNode;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.codeStyle.CodeStyleSettings;
import com.intellij.psi.tree.TokenSet;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import uk.ac.gla.teamL.EBNFLanguage;
import uk.ac.gla.teamL.psi.EBNFTypes;

/**
 * User: nishad
 * Date: 09/02/15
 * Time: 11:52
 */
public class EBNFFormattingModelBuilder implements FormattingModelBuilder {

    /**
     * Requests building the formatting model for a section of the file containing
     * the specified PSI element and its children.
     *
     * @param element  the top element for which formatting is requested.
     * @param settings the code style settings used for formatting.
     * @return the formatting model for the file.
     */
    @NotNull
    @Override
    public FormattingModel createModel(PsiElement element, CodeStyleSettings settings) {
        TokenSet bothSideSpaceElementSet = TokenSet.create(
            EBNFTypes.EQ,
            EBNFTypes.IDENTIFIER,
            EBNFTypes.OR
        );

        TokenSet lineBrokenElements = TokenSet.create(
            EBNFTypes.ANNOTATION,
            EBNFTypes.ASSIGNMENT,
            EBNFTypes.COMMENT_BLOCK,
            EBNFTypes.COMMENT_SINGLELINE,
            EBNFTypes.OR
        );

        SpacingBuilder spacingBuilder = new SpacingBuilder(settings, EBNFLanguage.INSTANCE)
                                                .before(EBNFTypes.QUANTIFIER)
                                                    .none()
                                                .after(EBNFTypes.QUANTIFIER)
                                                    .spaces(1)
                                                .after(EBNFTypes.ANNOTATION)
                                                    .spaces(1)
                                                .around(bothSideSpaceElementSet)
                                                    .spaces(1)
                                                .around(lineBrokenElements)
                                                    .lineBreakInCode()
                                                .before(EBNFTypes.COMMENT_SINGLELINE)
                                                    .spaces(1)
                                                .betweenInside(EBNFTypes.LB, EBNFTypes.RB, EBNFTypes.NESTED_RULES)
                                                    .lineBreakOrForceSpace(true, true);

        return FormattingModelProvider.createFormattingModelForPsiFile(
            element.getContainingFile(),
            new EBNFBlock(
                element.getNode(),
                Wrap.createWrap(WrapType.NONE, false),
                Alignment.createAlignment(),
                spacingBuilder
            ),
            settings
        );
    }

    /**
     * Returns the TextRange which should be processed by the formatter in order to calculate the
     * indent for a new line when a line break is inserted at the specified offset.
     *
     * @param file            the file in which the line break is inserted.
     * @param offset          the line break offset.
     * @param elementAtOffset the parameter at {@code offset}
     * @return the range to reformat, or null if the default range should be used
     */
    @Nullable
    @Override
    public TextRange getRangeAffectingIndent(PsiFile file, int offset, ASTNode elementAtOffset) {
//        if ()

        return null;
    }
}

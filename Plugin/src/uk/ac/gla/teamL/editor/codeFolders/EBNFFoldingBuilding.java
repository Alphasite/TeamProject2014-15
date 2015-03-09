package uk.ac.gla.teamL.editor.codeFolders;

import com.intellij.lang.ASTNode;
import com.intellij.lang.folding.FoldingBuilderEx;
import com.intellij.lang.folding.FoldingDescriptor;
import com.intellij.openapi.editor.Document;
import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import uk.ac.gla.teamL.EBNFFile;
import uk.ac.gla.teamL.psi.EBNFAssignment;
import uk.ac.gla.teamL.psi.EBNFNestedRules;
import uk.ac.gla.teamL.psi.EBNFTypes;

import java.util.ArrayList;
import java.util.List;

import static com.intellij.psi.util.PsiTreeUtil.findChildrenOfType;
import static uk.ac.gla.teamL.EBNFUtil.notNull;

/**
 * User: nishad
 * Date: 28/01/15
 * Time: 01:40
 */
public class EBNFFoldingBuilding extends FoldingBuilderEx {
    /**
     * Builds the folding regions for the specified node in the AST tree and its children.
     *
     * @param root     the element for which folding is requested.
     * @param document the document for which folding is built. Can be used to retrieve line
     *                 numbers for folding regions.
     * @param quick    whether the result should be provided as soon as possible. Is true, when
     *                 an editor is opened and we need to auto-fold something immediately, like Java imports.
     *                 If true, one should perform no reference resolving and avoid complex checks if possible.
     * @return the array of folding descriptors.
     */
    @NotNull
    @Override
    public FoldingDescriptor[] buildFoldRegions(PsiElement root, Document document, boolean quick) {
        // TODO

        if (root instanceof EBNFFile) {
            List<FoldingDescriptor> regions = new ArrayList<>();

            for (EBNFAssignment assignment : notNull(findChildrenOfType(root, EBNFAssignment.class))) {
                regions.add(new FoldingDescriptor(assignment.getRules(), assignment.getRules().getTextRange()));
            }

            for (EBNFNestedRules nestedRules : notNull(findChildrenOfType(root, EBNFNestedRules.class))) {
                regions.add(new FoldingDescriptor(nestedRules, nestedRules.getTextRange()));
            }

            // findChildrenOfType(root, )

            return regions.toArray(new FoldingDescriptor[regions.size()]);
        } else {
            return new FoldingDescriptor[0];
        }
    }

    /**
     * Returns the text which is displayed in the editor for the folding region related to the
     * specified node when the folding region is collapsed.
     *
     * @param node the node for which the placeholder text is requested.
     * @return the placeholder text.
     */
    @Nullable
    @Override
    public String getPlaceholderText(@NotNull ASTNode node) {
        PsiElement psi = node.getPsi();
        if (psi instanceof EBNFNestedRules) {
            return "(..)";
        }

        if (psi instanceof EBNFAssignment) {
            return  "...";
        }

        if (node.getElementType() == EBNFTypes.COMMENT_BLOCK) {
            return "/*..*/";
        }

        return null;
    }

    /**
     * Returns the default collapsed state for the folding region related to the specified node.
     *
     * @param node the node for which the collapsed state is requested.
     * @return true if the region is collapsed by default, false otherwise.
     */
    @Override
    public boolean isCollapsedByDefault(@NotNull ASTNode node) {
        return false;
    }
}

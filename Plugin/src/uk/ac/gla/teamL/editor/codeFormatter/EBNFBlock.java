package uk.ac.gla.teamL.editor.codeFormatter;

import com.intellij.formatting.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.TokenType;
import com.intellij.psi.formatter.common.AbstractBlock;
import com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.annotations.Nullable;
import uk.ac.gla.teamL.psi.*;

import java.util.ArrayList;
import java.util.List;

/**
 * User: nishad
 * Date: 09/02/15
 * Time: 11:54
 */
public class EBNFBlock extends AbstractBlock {

    private SpacingBuilder spacingBuilder;

    public EBNFBlock(ASTNode node, @Nullable Wrap wrap, @Nullable Alignment alignment, SpacingBuilder spacingBuilder) {
        super(node, wrap, alignment);
        this.spacingBuilder = spacingBuilder;
    }

    @Override
    protected List<Block> buildChildren() {
        List<Block> blocks = new ArrayList<Block>();
        ASTNode child = myNode.getFirstChildNode();
        ASTNode previousChild = null;

        while (child != null) {
            if (child.getElementType() != TokenType.WHITE_SPACE
            && (previousChild == null || previousChild.getElementType() != EBNFTypes.EOL
            ||  child.getElementType() != EBNFTypes.EOL)) {
//                && (child.getElementType() == EBNFTypes.NESTED_RULES
//                            ||  child.getElementType() == EBNFTypes.RULES_SEGMENT
//                            ||  child.getElementType() == EBNFTypes.RULES
//                            ||  child.getElementType() == EBNFTypes.ASSIGNMENT
//                            ||  child.getElementType() == EBNFTypes.ANNOTATION

                Alignment alignment;
                if (child instanceof EBNFAssignment) {
                    alignment = Alignment.createChildAlignment(Alignment.createAlignment());
                    blocks.add(new EBNFBlock(
                        ((EBNFAssignment) child).getEquals().getNode(),
                        Wrap.createWrap(WrapType.CHOP_DOWN_IF_LONG, false),
                        alignment,
                        spacingBuilder
                    ));

                } else if (child instanceof EBNFRules) {
                    alignment = Alignment.createChildAlignment(Alignment.createAlignment());

                } else {
                    alignment = Alignment.createAlignment();
                }

                Block block = new EBNFBlock(
                     child,
                     Wrap.createWrap(WrapType.NONE, false),
                     alignment,
                     spacingBuilder
                );

                blocks.add(block);
            }

            previousChild = child;
            child = child.getTreeNext();
        }
        return blocks;
    }

    /**
     * Returns a spacing object indicating what spaces and/or line breaks are added between two
     * specified children of this block.
     *
     * @param child1 the first child for which spacing is requested;
     *               <code>null</code> if given <code>'child2'</code> block is the first document block
     * @param child2 the second child for which spacing is requested.
     * @return the spacing instance, or null if no special spacing is required. If null is returned,
     * the formatter does not insert or delete spaces between the child blocks, but may insert
     * a line break if the line wraps at the position between the child blocks.
     * @see com.intellij.formatting.Spacing#createSpacing(int, int, int, boolean, int)
     * @see com.intellij.formatting.Spacing#getReadOnlySpacing()
     */
    @Nullable
    @Override
    public Spacing getSpacing(Block child1, Block child2) {
        return spacingBuilder.getSpacing(this, child1, child2);
    }

    @Override
    public Indent getIndent() {
        if (myNode.getPsi() instanceof EBNFAssignment) {
            return Indent.getAbsoluteNoneIndent();
        } else if (myNode.getPsi() instanceof EBNFRules) {
            if (myNode.getPsi().getParent() instanceof EBNFAssignment) {
                // Find the offset of the '='
                EBNFAssignment assignment = (EBNFAssignment) myNode.getPsi().getParent();
                PsiElement prevSibling = PsiTreeUtil.findChildOfType(assignment, EBNFEquals.class);

                return Indent.getIndent(
                    Indent.Type.NONE,
                    prevSibling.getTextRange().getStartOffset(),
                    true,
                    true
                );
            } else {
                return Indent.getIndent(
                    Indent.Type.NORMAL,
                    myNode.getTextRange().getStartOffset(),
                    false,
                    false
                );
            }
        } else {
            return Indent.getNoneIndent();
        }
    }

    /**
     * Returns true if the specified block may not contain child blocks. Used as an optimization
     * to avoid building the complete formatting model through calls to {@link #getSubBlocks()}.
     *
     * @return true if the block is a leaf block and may not contain child blocks, false otherwise.
     */
    @Override
    public boolean isLeaf() {
        return false;
    }
}

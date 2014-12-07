package uk.ac.gla.teamL.parser.psi.impl;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import uk.ac.gla.teamL.parser.psi.EBNFCompositeElement;

/**
 * User: nishad
 * Date: 22/11/14
 * Time: 15:29
 */
public class EBNFCompositeElementImpl extends ASTWrapperPsiElement implements EBNFCompositeElement {
    public EBNFCompositeElementImpl(ASTNode node) {
        super(node);
    }

}

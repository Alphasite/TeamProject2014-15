package uk.ac.gla.teamL.psi.impl;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import org.jetbrains.annotations.NotNull;
import uk.ac.gla.teamL.psi.EBNFNamedElement;

/**
 * User: nishad
 * Date: 25/01/15
 * Time: 21:00
 */
public abstract class EBNFNamedElementImpl extends ASTWrapperPsiElement implements EBNFNamedElement {
    public EBNFNamedElementImpl(@NotNull ASTNode node) {
        super(node);
    }
}

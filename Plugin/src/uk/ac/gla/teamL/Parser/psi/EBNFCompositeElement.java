package uk.ac.gla.teamL.parser.psi;

import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReference;
import org.jetbrains.annotations.Nullable;

/**
 * User: nishad
 * Date: 22/11/14
 * Time: 15:27
 */
public interface EBNFCompositeElement extends PsiElement {
    /*  Nothing here... Its a *haunted* interface


        ...BOO
    */

    @Nullable
    @Override
    PsiReference getReference();
}

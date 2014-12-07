package uk.ac.gla.teamL.parser.psi.impl;

import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiNamedElement;
import com.intellij.psi.PsiReferenceBase;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import uk.ac.gla.teamL.parser.EBNFParserUtil;
import uk.ac.gla.teamL.parser.psi.EBNFCompositeElement;
import uk.ac.gla.teamL.parser.psi.EBNFIdentifier;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * User: nishad
 * Date: 06/12/14
 * Time: 19:34
 */
public class EBNFReferenceImpl<T extends EBNFCompositeElement> extends PsiReferenceBase<T> {

    public EBNFReferenceImpl (T element, TextRange range) {
        super(element, range);
    }

    @Nullable
    @Override
    public PsiElement resolve() {
        PsiFile containingFile = myElement.getContainingFile();
        String referenceName = getRangeInElement().substring(myElement.getText());

        // Find the matching rule.
        if (myElement instanceof PsiNamedElement) {
            for (EBNFAssignmentImpl assignment : EBNFParserUtil.findRules(containingFile)) {
                if (assignment != null && assignment.getName() != null && assignment.getName().equals(referenceName)) {
                    return assignment;
                }
            }
        }

        return null;
    }

    @NotNull
    @Override
    public Object[] getVariants() {
        Set<String> variantsSet = new HashSet<>();
        List<EBNFIdentifier> variants = new ArrayList<>();

        // I could user get rules, but i thought it might be helpful to provide undefined nodes as completions as well.
        for (EBNFIdentifier identifier:  EBNFParserUtil.findIdentifiers(myElement.getContainingFile())) {
            if (!variantsSet.contains(identifier.getName())) {
                variants.add(identifier);
                variantsSet.add(identifier.getName());
            }
        }

        return variants.toArray();
    }
}

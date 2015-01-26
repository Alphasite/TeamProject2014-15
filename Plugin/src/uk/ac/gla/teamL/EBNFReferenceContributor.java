package uk.ac.gla.teamL;

import com.intellij.patterns.PlatformPatterns;
import com.intellij.psi.*;
import com.intellij.util.ProcessingContext;
import org.jetbrains.annotations.NotNull;
import uk.ac.gla.teamL.parser.EBNFParserUtil;
import uk.ac.gla.teamL.psi.EBNFAssignment;
import uk.ac.gla.teamL.psi.EBNFIdentifier;
import uk.ac.gla.teamL.psi.EBNFTypes;

import java.util.ArrayList;
import java.util.List;

/**
 * User: nishad
 * Date: 25/01/15
 * Time: 21:22
 */
public class EBNFReferenceContributor extends PsiReferenceContributor {
    @Override
    public void registerReferenceProviders(@NotNull PsiReferenceRegistrar registrar) {
        registrar.registerReferenceProvider(PlatformPatterns.psiElement(EBNFTypes.IDENTIFIER).withLanguage(EBNFLanguage.INSTANCE), new PsiReferenceProvider() {
            @NotNull
            @Override
            public PsiReference[] getReferencesByElement(@NotNull PsiElement element, @NotNull ProcessingContext context) {
                System.out.println("Checked element for references. " + element.toString());

                List<PsiReference> references = new ArrayList<>();
                for (EBNFIdentifier identifier : EBNFParserUtil.findIdentifiers(element)) {
                    if (identifier != null && !(identifier.getParent() instanceof EBNFAssignment)) {
                        references.add(new EBNFReference(element, element.getTextRange()));
                    }
                }

                return references.toArray(new PsiReference[references.size()]);
            }
        });

        System.out.println("Registered Ref Provider.");
    }
}

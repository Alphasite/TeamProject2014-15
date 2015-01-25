package uk.ac.gla.teamL.editor;

import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.lang.annotation.Annotator;
import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;
import uk.ac.gla.teamL.parser.EBNFParserUtil;
import uk.ac.gla.teamL.psi.EBNFAssignment;
import uk.ac.gla.teamL.psi.EBNFIdentifier;

import java.util.ArrayList;
import java.util.List;

/**
 * User: nishad
 * Date: 07/12/14
 * Time: 22:37
 */
public class EBNFUnusedRuleAnnotation implements Annotator {
    @Override
    public void annotate(@NotNull PsiElement psiElement, @NotNull AnnotationHolder annotationHolder) {
        //TODO fix.
        if (psiElement instanceof EBNFAssignment) {
            EBNFAssignment ruleDecl = (EBNFAssignment) psiElement;
            List<EBNFIdentifier> rules = EBNFParserUtil.findIdentifiers(ruleDecl.getContainingFile());

            String name = ruleDecl.getName();

            List<EBNFIdentifier> occurrences = new ArrayList<>();
            for (EBNFIdentifier id : rules) {
                String occurrenceName = id.getName();
                if (name.equals(occurrenceName.toLowerCase())) {
                    occurrences.add(id);
                }
            }

            if (occurrences.size() <= 1) {
                annotationHolder.createWarningAnnotation(ruleDecl, "Rule is never used.");
            }
        }
    }
}

package uk.ac.gla.teamL.editor;

import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.lang.annotation.Annotator;
import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;
import uk.ac.gla.teamL.parser.EBNFParserUtil;
import uk.ac.gla.teamL.psi.EBNFIdentifier;
import uk.ac.gla.teamL.psi.impl.EBNFAssignmentImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * User: nishad
 * Date: 05/12/14
 * Time: 19:19
 */
public class EBNFRuleErrorAnnotator implements Annotator {
    @Override
    public void annotate(@NotNull PsiElement psiElement, @NotNull AnnotationHolder annotationHolder) {

        if (psiElement instanceof EBNFIdentifier) {
            EBNFIdentifier id = (EBNFIdentifier) psiElement;
            String name = id.getName().toLowerCase();

            List<EBNFAssignmentImpl> rules = EBNFParserUtil.findRules(id.getContainingFile());

            List<EBNFAssignmentImpl> occurrences = new ArrayList<>();
            for (EBNFAssignmentImpl rule : rules) {
                String occurrenceName = rule.getId().getName();
                if (name.equals(occurrenceName.toLowerCase())) {
                    occurrences.add(rule);
                }
            }

            if (id.getParent() instanceof EBNFAssignmentImpl) {
                // Check for duplicate identifier declarations.

                EBNFAssignmentImpl parent = (EBNFAssignmentImpl) id.getParent();
                if (occurrences.size() > 1) {
                    annotationHolder.createErrorAnnotation(psiElement.getTextRange(), "Duplicate identifier");
                }

            } else {
                // Find undeclared rule identifier usage.

                if (occurrences.size() == 0) {
                    annotationHolder.createErrorAnnotation(psiElement.getTextRange(), "Undeclared identifier");
                }
            }
        }
    }
}

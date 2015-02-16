package uk.ac.gla.teamL.inspections.annotators;

import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.lang.annotation.Annotator;
import com.intellij.psi.PsiElement;
import com.intellij.psi.util.PsiTreeUtil;
import uk.ac.gla.teamL.editor.Annotations;
import uk.ac.gla.teamL.psi.EBNFAssignment;
import uk.ac.gla.teamL.psi.EBNFIdentifier;
import uk.ac.gla.teamL.psi.EBNFNegation;

import java.util.Collection;

import static uk.ac.gla.teamL.inspections.annotators.EBNFAnnotationAnnotator.hasAnnotation;

/**
 * User: nishad
 * Date: 16/02/15
 * Time: 21:40
 */
public class EBNFRegexAnnotator implements Annotator {
    /**
     * Annotates the specified PSI element.
     * It is guaranteed to be executed in non-reentrant fashion.
     * I.e there will be no call of this method for this instance before previous call get completed.
     * Multiple instances of the annotator might exist simultaneously, though.
     *
     * @param element to annotate.
     * @param holder  the container which receives annotations created by the plugin.
     */
    @Override
    public void annotate(PsiElement element, AnnotationHolder holder) {
        if (element instanceof EBNFAssignment) {
            EBNFAssignment assignment = (EBNFAssignment) element;
            boolean isRegex = hasAnnotation(assignment, Annotations.regex);

            if (!isRegex) {
                Collection<EBNFNegation> negations = PsiTreeUtil.findChildrenOfType(assignment.getRules(), EBNFNegation.class);
                if (negations.size() > 0) {
                    for (EBNFNegation negation : negations) {
                        holder.createErrorAnnotation(negation, "Only rule annotated as @regex may contain negations.");
                    }
                }
            } else {
                Collection<EBNFIdentifier> identifiers = PsiTreeUtil.findChildrenOfType(assignment.getRules(), EBNFIdentifier.class);
                if (identifiers.size() > 0) {
                    for (EBNFIdentifier identifier : identifiers) {
                        holder.createErrorAnnotation(identifier, "Rules annotated as @regex may not contain references to other rules.");
                    }
                }
            }

        }
    }
}

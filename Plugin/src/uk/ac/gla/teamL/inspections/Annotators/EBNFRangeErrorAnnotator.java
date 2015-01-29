package uk.ac.gla.teamL.inspections.Annotators;

import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.lang.annotation.Annotator;
import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;
import uk.ac.gla.teamL.psi.EBNFRange;
import uk.ac.gla.teamL.psi.impl.EBNFRangeImpl;

/**
 * User: nishad
 * Date: 05/12/14
 * Time: 20:10
 */
public class EBNFRangeErrorAnnotator implements Annotator {
    @Override
    public void annotate(@NotNull PsiElement psiElement, @NotNull AnnotationHolder annotationHolder) {
        if (psiElement instanceof EBNFRange) {
            EBNFRangeImpl range = (EBNFRangeImpl) psiElement;

            String lb, ub;

            //noinspection ConstantConditions
            String lbString = range.getGetLowerBound().getString();
            //noinspection ConstantConditions
            String ubString = range.getGetUpperBound().getString();

            if (lbString.length() != 1) {
                annotationHolder.createErrorAnnotation(range.getGetLowerBound(), "Lower bound is >1 character.");
                return;
            }

            if (ubString.length() != 1) {
                annotationHolder.createErrorAnnotation(range.getGetUpperBound(), "Upper bound is >1 character.");
                return;
            }

            // technically this should hold for all java compatible UTF15 strings. (i hope)
            if (lbString.charAt(0) > ubString.charAt(0)) {
                annotationHolder.createWarningAnnotation(psiElement, "Lower bound occurs after upper bound");
                return;
            }

        }
    }
}

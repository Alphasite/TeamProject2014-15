package uk.ac.gla.teamL.editor;

import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.lang.annotation.Annotator;
import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;
import uk.ac.gla.teamL.parser.psi.EBNFAnnotation;

import java.util.HashSet;
import java.util.Set;

/**
 * User: nishad
 * Date: 12/12/14
 * Time: 12:50
 */
public class EBNFAnnotationAnnotator implements Annotator {
    private static final Set<String> valid;

    static {
        valid = new HashSet<>();
        valid.add(Annotations.ignored.identifier);
        valid.add(Annotations.literal.identifier);
    }

    @Override
    public void annotate(@NotNull PsiElement psiElement, @NotNull AnnotationHolder annotationHolder) {
        if (psiElement instanceof EBNFAnnotation) {
            EBNFAnnotation annotation = (EBNFAnnotation) psiElement;

            if (!valid.contains(annotation.getName())) {
                annotationHolder.createErrorAnnotation(annotation, "Unrecognised annotation.");
            }
        }
    }
}

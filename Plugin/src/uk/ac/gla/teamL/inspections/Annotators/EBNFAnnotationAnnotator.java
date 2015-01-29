package uk.ac.gla.teamL.inspections.Annotators;

import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.lang.annotation.Annotator;
import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;
import uk.ac.gla.teamL.editor.Annotations;
import uk.ac.gla.teamL.psi.EBNFAnnotation;

import java.util.HashSet;
import java.util.Set;

/**
 * User: nishad
 * Date: 12/12/14
 * Time: 12:50
 */
public class EBNFAnnotationAnnotator implements Annotator {
    private static final Set<String> validAnnotations;

    static {
        validAnnotations = new HashSet<>();
        validAnnotations.add(Annotations.ignored.identifier);
        validAnnotations.add(Annotations.literal.identifier);
    }

    public static Boolean isValidAnnotation(String name) {
        return validAnnotations.contains(name.replace("@", "").toLowerCase());
    }

    @Override
    public void annotate(@NotNull PsiElement psiElement, @NotNull AnnotationHolder annotationHolder) {
        if (psiElement instanceof EBNFAnnotation) {
            EBNFAnnotation annotation = (EBNFAnnotation) psiElement;

            if (!isValidAnnotation(annotation.getName())) {
                annotationHolder.createErrorAnnotation(annotation, "Unrecognised annotation.");
            }
        }
    }
}

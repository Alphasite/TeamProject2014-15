package uk.ac.gla.teamL.translators;

import com.intellij.psi.util.PsiTreeUtil;
import uk.ac.gla.teamL.editor.Annotations;
import uk.ac.gla.teamL.inspections.Annotators.EBNFAnnotationAnnotator;
import uk.ac.gla.teamL.psi.EBNFAnnotation;
import uk.ac.gla.teamL.psi.EBNFAny;
import uk.ac.gla.teamL.psi.EBNFAssignment;
import uk.ac.gla.teamL.psi.EBNFIdentifier;

import java.util.List;

/**
 * User: nishad
 * Date: 29/01/15
 * Time: 21:44
 */
public class TranslatorUtils {
    public static String escapeString(String string) {
        return string.replace("'", "\\\'");
    }

    public static boolean isLiteral(EBNFAssignment assignment) {
        return hasAnnotation(assignment, Annotations.literal);
    }


    public static boolean isLexRule(EBNFAssignment assignment) {
        return hasAnnotation(assignment, Annotations.ignored)
                       || PsiTreeUtil.findChildrenOfType(assignment.getRules(), EBNFIdentifier.class).size() <= 0
                                  && PsiTreeUtil.findChildrenOfType(assignment.getRules(), EBNFAny.class).size() <= 0;

//        // I'd like to come up with a way to integrate it, but it might be
//        // better to keep it simple and easy to understand.
//        for (EBNFIdentifier identifier : PsiTreeUtil.findChildrenOfType(assignment.getRules(), EBNFIdentifier.class)) {
//            PsiReference psiReference = identifier.resolveReference();
//            if (psiReference instanceof EBNFAssignment && !isLexRule((EBNFAssignment) psiReference)) {
//                return false;
//            }
//        }
//        return true;
    }

    public static boolean hasAnnotation(EBNFAssignment assignment, Annotations label) {
        List<EBNFAnnotation> annotations = assignment.getAnnotationList();

        for (EBNFAnnotation annotation: annotations) {
            if (EBNFAnnotationAnnotator.isAnnotation(annotation.getName(), label)) {
                return true;
            }
        }

        return false;
    }
}

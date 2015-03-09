package uk.ac.gla.teamL.translators;

import com.intellij.psi.util.PsiTreeUtil;
import uk.ac.gla.teamL.EBNFFile;
import uk.ac.gla.teamL.editor.Annotations;
import uk.ac.gla.teamL.parser.psi.EBNFAnnotation;
import uk.ac.gla.teamL.parser.psi.EBNFAny;
import uk.ac.gla.teamL.parser.psi.EBNFAssignment;
import uk.ac.gla.teamL.parser.psi.EBNFIdentifier;

import java.util.List;

/**
 * User: nishad
 * Date: 12/12/14
 * Time: 13:27
 */
public abstract class Translator {
    public abstract String translate (EBNFFile file);

    public static boolean hasAnnotation(EBNFAssignment assignment, Annotations label) {
        List<EBNFAnnotation> annotations = assignment.getAnnotationList();

        for (EBNFAnnotation annotation: annotations) {
            if (annotation.getName().equals(label.identifier)) {
                return true;
            }
        }

        return false;
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

    public static String escapeString(String string) {
        return string.replace("\"", "\\\"");
    }
}

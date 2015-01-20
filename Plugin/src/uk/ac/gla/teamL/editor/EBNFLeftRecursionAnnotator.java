package uk.ac.gla.teamL.editor;

import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.lang.annotation.Annotator;
import com.intellij.psi.PsiElement;
import com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.annotations.NotNull;
import uk.ac.gla.teamL.parser.psi.EBNFAssignment;
import uk.ac.gla.teamL.parser.psi.EBNFIdentifier;
import uk.ac.gla.teamL.parser.psi.EBNFNestedRules;
import uk.ac.gla.teamL.parser.psi.EBNFRuleElement;

import java.util.List;

/**
 * User: nishad
 * Date: 07/12/14
 * Time: 15:18
 */
public class EBNFLeftRecursionAnnotator implements Annotator {

    @Override
    public void annotate(@NotNull PsiElement psiElement, @NotNull AnnotationHolder annotationHolder) {
        if (psiElement instanceof EBNFAssignment) {
            EBNFAssignment assignment = (EBNFAssignment) psiElement;

            String name = assignment.getName();
            List<EBNFRuleElement> subRules = assignment.getRules().getRuleElementList();

            if (subRules.size() > 0) {
                PsiElement deepest = PsiTreeUtil.getDeepestFirst(subRules.get(0));

                // Handle the corner case of nested rules,
                // the only place where an id can be and also not be the deepest element.
                deepest = deepest.getParent();

                while (deepest instanceof EBNFNestedRules) {
                    deepest = PsiTreeUtil.getDeepestFirst(((EBNFNestedRules) deepest).getRules());
                }

                EBNFIdentifier leftMostRule;
                if (!(deepest instanceof EBNFIdentifier)) {
                    leftMostRule = PsiTreeUtil.getParentOfType(deepest, EBNFIdentifier.class);
                } else {
                    leftMostRule = (EBNFIdentifier) deepest;
                }

                if (leftMostRule != null) {
                    if (leftMostRule.getName().equals(name)) {
                        annotationHolder.createWarningAnnotation(leftMostRule, "Rule is left recursive. This may cause issues with certain parser generators.");
                    }
                }
            }
        }
    }
}

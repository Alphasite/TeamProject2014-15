package uk.ac.gla.teamL;

import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiFileFactory;
import com.intellij.psi.util.PsiTreeUtil;

/**
 * User: nishad
 * Date: 18/11/14
 * Time: 11:14
 */
public class EBNFUtil {
    public static PsiElement createElement(Project inProject, String withText) {
        PsiFile file = PsiFileFactory
            .getInstance(inProject)
            .createFileFromText("_temp.ebnf", EBNFLanguage.INSTANCE, withText, false, false);
        return PsiTreeUtil.getDeepestFirst(file);
    }
}

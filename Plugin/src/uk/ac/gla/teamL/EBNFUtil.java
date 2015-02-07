package uk.ac.gla.teamL;

import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiFileFactory;
import com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;

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

    // TODO This is worth investigating, but for now its not much of an overhead to allocate an empty array.
    // public static final List emptyList = new ArrayList();

    @NotNull
    public static <T> Collection<T> notNull(Collection<T> collection) {
        return collection == null? new ArrayList<T>(0) : collection;
    }
}

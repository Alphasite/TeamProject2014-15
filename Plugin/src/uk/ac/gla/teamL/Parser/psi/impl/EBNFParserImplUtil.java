package uk.ac.gla.teamL.parser.psi.impl;

import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiFileFactory;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.util.IncorrectOperationException;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import uk.ac.gla.teamL.EBNFLanguage;
import uk.ac.gla.teamL.parser.psi.EBNFIdentifier;
import uk.ac.gla.teamL.parser.psi.EBNFString;

/**
 * User: nishad
 * Date: 24/11/14
 * Time: 23:35
 */
public class EBNFParserImplUtil {
    @Nullable
    @NonNls
    public static String getName(EBNFIdentifier id) {
        return id != null ? id.getText() : null;
    }

    public static PsiElement setName(EBNFIdentifier id, @NonNls @NotNull String name) throws IncorrectOperationException {

        Project project = id.getProject();
        PsiFile file = PsiFileFactory
                               .getInstance(project)
                               .createFileFromText("foo.ebnf", EBNFLanguage.INSTANCE, name, false, false);

        id.getId().replace(PsiTreeUtil.getDeepestFirst(file));

        return id;
    }

    public static String getName(EBNFAssignmentImpl element) {
        return element.getId().getName();
    }

    public static PsiElement setName(EBNFAssignmentImpl element, @NonNls @NotNull String name) throws IncorrectOperationException {
        element.getId().setName(name);
        return element;
    }

    public static PsiElement getNameIdentifier(EBNFAssignmentImpl element) {
        return element.getId();
    }

    public static String getString(EBNFString string) {
        PsiElement stringNode;

        if ((stringNode = string.getStringDoubleQuotes()) != null) {
            String toString = stringNode.getText();
            toString = toString.replace("\\" , "");
            return toString.substring(1, toString.length() - 1);

        } else if ((stringNode = string.getStringSingleQuotes()) != null) {
            String toString = stringNode.getText();
            toString = toString.replace("\\", "");
            return toString.substring(1, toString.length() - 1);

        } else  if((stringNode = string.getStringTripleQuotes()) != null) {
            String toString = stringNode.getText();
            toString = toString.replace("\\", "");
            return toString.substring(3, toString.length() - 3);

        } else {
            return "";
        }
    }
}

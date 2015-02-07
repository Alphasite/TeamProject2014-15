package uk.ac.gla.teamL.parser;

import com.intellij.lang.parser.GeneratedParserUtilBase;
import com.intellij.psi.PsiElement;
import com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.annotations.NotNull;
import uk.ac.gla.teamL.EBNFFile;
import uk.ac.gla.teamL.psi.*;

import java.util.ArrayList;
import java.util.List;

/**
 * User: nishad
 * Date: 08/11/14
 * Time: 21:58
 */
public class EBNFParserUtil extends GeneratedParserUtilBase {

    public static List<EBNFAssignment> findRules(PsiElement file, String name) {
        List<EBNFAssignment> identifiers = new ArrayList<>();

        for (EBNFAssignment assignment: PsiTreeUtil.findChildrenOfType(file, EBNFAssignment.class)) {
            if (name.toLowerCase().equals(assignment.getName().toLowerCase())) {
                identifiers.add(assignment);
            }
        }

        return identifiers;
    }

    public static List<EBNFAssignment> findRules(PsiElement file) {
        return PsiTreeUtil.getChildrenOfTypeAsList(file, EBNFAssignment.class);
    }

    public static List<EBNFIdentifier> findIdentifiers(PsiElement file, String name) {
        List<EBNFIdentifier> identifiers = new ArrayList<>();

        for (EBNFIdentifier id: PsiTreeUtil.findChildrenOfType(file, EBNFIdentifier.class)) {
            if (name.equals(id.getName())) {
                identifiers.add(id);
            }
        }

        return identifiers;
    }

    public static List<EBNFIdentifier> findIdentifiers(PsiElement file) {
        return new ArrayList<>(PsiTreeUtil.findChildrenOfType(file, EBNFIdentifier.class));
    }

    public static EBNFAssignment getFirstRule(PsiElement file) {
        if (file instanceof EBNFFile) {
            PsiElement child = file.getFirstChild();

            while (!(child instanceof EBNFAssignment) && child != null) {
                child = child.getNextSibling();
            }

            if (child != null) {
                return (EBNFAssignment) child;
            }
        }

        return null;
    }



}

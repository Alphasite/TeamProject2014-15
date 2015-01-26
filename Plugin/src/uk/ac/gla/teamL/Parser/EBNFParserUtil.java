package uk.ac.gla.teamL.parser;

import com.intellij.lang.parser.GeneratedParserUtilBase;
import com.intellij.psi.PsiElement;
import com.intellij.psi.util.PsiTreeUtil;
import uk.ac.gla.teamL.psi.EBNFAssignment;
import uk.ac.gla.teamL.psi.EBNFIdentifier;

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
            if (name.equals(assignment.getName())) {
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

    public static boolean isRecursive(EBNFAssignment rule) {
        return false; // TODO implement.
    }
}

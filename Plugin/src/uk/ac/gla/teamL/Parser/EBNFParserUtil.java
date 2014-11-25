package uk.ac.gla.teamL.parser;

import com.intellij.lang.parser.GeneratedParserUtilBase;
import com.intellij.psi.PsiFile;
import com.intellij.psi.util.PsiTreeUtil;
import uk.ac.gla.teamL.parser.psi.EBNFAssignment;
import uk.ac.gla.teamL.parser.psi.impl.EBNFAssignmentImpl;

import java.util.List;

/**
 * User: nishad
 * Date: 08/11/14
 * Time: 21:58
 */
public class EBNFParserUtil extends GeneratedParserUtilBase {

    public static List<EBNFAssignmentImpl> getRules(PsiFile file) {
        return PsiTreeUtil.getChildrenOfTypeAsList(file, EBNFAssignmentImpl.class);
    }

    public static boolean isRecursive(EBNFAssignment rule) {
        return false; // TODO implement.
    }
}

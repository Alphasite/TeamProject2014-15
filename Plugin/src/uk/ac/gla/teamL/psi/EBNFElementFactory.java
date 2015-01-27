package uk.ac.gla.teamL.psi;

import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiFileFactory;
import uk.ac.gla.teamL.EBNFFile;
import uk.ac.gla.teamL.EBNFFileType;
import uk.ac.gla.teamL.parser.EBNFParserUtil;

import java.util.List;

/**
 * User: nishad
 * Date: 25/01/15
 * Time: 21:04
 */

public class EBNFElementFactory {
    public static EBNFIdentifier createIdentifier(Project project, String name) {
        final EBNFFile file = createFile(project, "let "+name+" = " + name + " ;");
        List<EBNFAssignment> rules = EBNFParserUtil.findRules(file);

        if (rules.size() >= 1) {
            return rules.get(0).getId();
        } else {
            return null;
        }

    }

    public static EBNFFile createFile(Project project, String text) {
        String name = "dummy.ebnf";
        return (EBNFFile) PsiFileFactory.getInstance(project).createFileFromText(
            name,
            EBNFFileType.INSTANCE,
            text
        );
    }
}

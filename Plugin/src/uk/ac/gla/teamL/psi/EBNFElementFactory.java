package uk.ac.gla.teamL.psi;

import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiFileFactory;
import uk.ac.gla.teamL.EBNFFile;
import uk.ac.gla.teamL.EBNFFileType;

/**
 * User: nishad
 * Date: 25/01/15
 * Time: 21:04
 */

public class EBNFElementFactory {
    public static EBNFIdentifier createIdentifier(Project project, String name) {
        final EBNFFile file = createFile(project, name);
        EBNFIdentifier[] identifiers = file.findChildrenByClass(EBNFIdentifier.class);

        if (identifiers.length >= 1) {
            return identifiers[0];
        } else {
            return null;
        }

    }

    public static EBNFFile createFile(Project project, String text) {
        String name = "dummy.ebnf";
        return (EBNFFile) PsiFileFactory.getInstance(project).createFileFromText(
            "let "+name+" = " + name + " ;",
            EBNFFileType.INSTANCE, text
        );
    }
}

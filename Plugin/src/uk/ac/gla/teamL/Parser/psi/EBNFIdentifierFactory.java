package uk.ac.gla.teamL.parser.psi;

import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiFileFactory;
import uk.ac.gla.teamL.EBNFFile;
import uk.ac.gla.teamL.EBNFFileType;

/**
 * User: nishad
 * Date: 05/12/14
 * Time: 22:12
 */
public class EBNFIdentifierFactory {
    public static EBNFIdentifier createProperty(Project project, String name) {
        final EBNFFile file = createFile(project, name);
        return (EBNFIdentifier) file.getFirstChild();
    }

    public static EBNFFile createFile(Project project, String text) {
        String placeholder = "let Hello = \"World.\"";
        return (EBNFFile) PsiFileFactory.getInstance(project)
                                  .createFileFromText(placeholder, EBNFFileType.INSTANCE, text);
    }

}

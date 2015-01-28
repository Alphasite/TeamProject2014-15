package uk.ac.gla.teamL.editor;

import com.intellij.navigation.ChooseByNameContributor;
import com.intellij.navigation.NavigationItem;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiManager;
import com.intellij.psi.search.FileTypeIndex;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.util.indexing.FileBasedIndex;
import org.jetbrains.annotations.NotNull;
import uk.ac.gla.teamL.EBNFFile;
import uk.ac.gla.teamL.EBNFFileType;
import uk.ac.gla.teamL.parser.EBNFParserUtil;
import uk.ac.gla.teamL.psi.EBNFAssignment;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * User: nishad
 * Date: 28/01/15
 * Time: 01:48
 */
public class EBNFChooseByNameContributor implements ChooseByNameContributor {

    @NotNull
    @Override
    public String[] getNames(Project project, boolean includeNonProjectItems) {
        Collection<VirtualFile> virtualFiles = FileBasedIndex.getInstance().getContainingFiles(
            FileTypeIndex.NAME,
            EBNFFileType.INSTANCE,
            GlobalSearchScope.allScope(project)
        );

        List<EBNFAssignment> rules = new ArrayList<>();

        for (VirtualFile file : virtualFiles) {
            EBNFFile ebnfFile = (EBNFFile) PsiManager.getInstance(project).findFile(file);
            rules.addAll(EBNFParserUtil.findRules(ebnfFile));
        }

        List<String> names = new ArrayList<String>(rules.size());
        for (EBNFAssignment property : rules) {
            names.add(property.getName());
        }
        return names.toArray(new String[names.size()]);
    }

    @NotNull
    @Override
    public NavigationItem[] getItemsByName(String name, String pattern, Project project, boolean includeNonProjectItems) {
        Collection<VirtualFile> virtualFiles = FileBasedIndex.getInstance().getContainingFiles(
            FileTypeIndex.NAME,
            EBNFFileType.INSTANCE,
            GlobalSearchScope.allScope(project)
        );

        List<EBNFAssignment> rules = new ArrayList<>();

        for (VirtualFile file : virtualFiles) {
            EBNFFile ebnfFile = (EBNFFile) PsiManager.getInstance(project).findFile(file);
            rules.addAll(EBNFParserUtil.findRules(ebnfFile, name));
        }

        //noinspection SuspiciousToArrayCall
        return rules.toArray(new NavigationItem[rules.size()]);
    }
}

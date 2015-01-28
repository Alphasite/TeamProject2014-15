package uk.ac.gla.teamL.editor.StructureView;

import com.intellij.ide.structureView.StructureViewTreeElement;
import com.intellij.ide.util.treeView.smartTree.SortableTreeElement;
import com.intellij.ide.util.treeView.smartTree.TreeElement;
import com.intellij.navigation.ItemPresentation;
import com.intellij.navigation.NavigationItem;
import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;
import uk.ac.gla.teamL.EBNFFile;
import uk.ac.gla.teamL.parser.EBNFParserUtil;
import uk.ac.gla.teamL.psi.EBNFAssignment;

import java.util.ArrayList;
import java.util.List;

/**
 * User: nishad
 * Date: 28/01/15
 * Time: 02:04
 */
public class EBNFStructureViewElement implements StructureViewTreeElement, SortableTreeElement {
    private NavigationItem myElement;

    public EBNFStructureViewElement(PsiElement myElement) {
        this.myElement = (NavigationItem) myElement;
    }

    /**
     * Returns the data object (usually a PSI element) corresponding to the
     * structure view element.
     *
     * @return the data object instance.
     */
    @Override
    public Object getValue() {
        return myElement;
    }

    /**
     * Open editor and select/navigate to the object there if possible.
     * Just do nothing if navigation is not possible like in case of a package
     *
     * @param requestFocus <code>true</code> if focus requesting is necessary
     */
    @Override
    public void navigate(boolean requestFocus) {
        myElement.navigate(requestFocus);
    }

    /**
     * @return <code>false</code> if navigation is not possible for any reason.
     */
    @Override
    public boolean canNavigate() {
        return myElement.canNavigate();
    }

    /**
     * @return <code>false</code> if navigation to source is not possible for any reason.
     * Source means some kind of editor
     */
    @Override
    public boolean canNavigateToSource() {
        return myElement.canNavigateToSource();
    }

    @NotNull
    @Override
    public String getAlphaSortKey() {
        //noinspection ConstantConditions
        return myElement.getName();
    }

    /**
     * Returns the presentation of the tree element.
     *
     * @return the element presentation.
     */
    @NotNull
    @Override
    public ItemPresentation getPresentation() {
        //noinspection ConstantConditions
        return myElement.getPresentation();
    }

    /**
     * Returns the list of children of the tree element.
     *
     * @return the list of children.
     */
    @NotNull
    @Override
    public TreeElement[] getChildren() {
        if (myElement instanceof EBNFFile) {
            List<EBNFAssignment> rules = EBNFParserUtil.findRules((PsiElement) myElement);
            List<TreeElement> treeElements = new ArrayList<TreeElement>(rules.size());

            for (EBNFAssignment rule: rules) {
                treeElements.add(new EBNFStructureViewElement(rule));
            }

            return treeElements.toArray(new TreeElement[treeElements.size()]);

        } else {
            return EMPTY_ARRAY;
        }
    }
}

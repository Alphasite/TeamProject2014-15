package uk.ac.gla.teamL.editor.StructureView;

import com.intellij.ide.structureView.StructureViewModel;
import com.intellij.ide.structureView.StructureViewModelBase;
import com.intellij.ide.structureView.StructureViewTreeElement;
import com.intellij.openapi.editor.Editor;
import com.intellij.psi.PsiFile;
import uk.ac.gla.teamL.EBNFFile;

/**
 * User: nishad
 * Date: 28/01/15
 * Time: 02:04
 */
public class EBNFStructureViewModel extends StructureViewModelBase implements StructureViewModel.ElementInfoProvider {

    public EBNFStructureViewModel(PsiFile file, Editor editor) {
        super(file, editor, new EBNFStructureViewElement(file));
    }

    public EBNFStructureViewModel(PsiFile file) {
        super(file, new EBNFStructureViewElement(file));
    }

    @Override
    public boolean isAlwaysShowsPlus(StructureViewTreeElement element) {
        return false;
    }

    @Override
    public boolean isAlwaysLeaf(StructureViewTreeElement element) {
        return element instanceof EBNFFile; // There isn't any sort of nesting afaik, so only a file can have leaves.
    }
}

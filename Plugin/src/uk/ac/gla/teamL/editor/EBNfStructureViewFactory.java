package uk.ac.gla.teamL.editor;

import com.intellij.ide.structureView.StructureViewBuilder;
import com.intellij.lang.PsiStructureViewFactory;
import com.intellij.psi.PsiFile;
import org.jetbrains.annotations.Nullable;

/**
 * User: nishad
 * Date: 27/01/15
 * Time: 12:41
 */
public class EBNFStructureViewFactory implements PsiStructureViewFactory {
    @Nullable
    @Override
    public StructureViewBuilder getStructureViewBuilder(PsiFile psiFile) {
        return null;
    }
}

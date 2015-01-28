package uk.ac.gla.teamL.editor.StructureView;

import com.intellij.ide.structureView.StructureViewBuilder;
import com.intellij.ide.structureView.StructureViewModel;
import com.intellij.ide.structureView.TreeBasedStructureViewBuilder;
import com.intellij.lang.PsiStructureViewFactory;
import com.intellij.openapi.editor.Editor;
import com.intellij.psi.PsiFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * User: nishad
 * Date: 27/01/15
 * Time: 12:41
 */
public class EBNFStructureViewFactory implements PsiStructureViewFactory {
    @Nullable
    @Override
    public StructureViewBuilder getStructureViewBuilder(final PsiFile psiFile) {
        return new TreeBasedStructureViewBuilder() {
            /**
             * Returns the structure view model defining the data displayed in the structure view
             * for a specific file.
             *
             * @param editor
             * @return the structure view model instance.
             * @see TextEditorBasedStructureViewModel
             */
            @NotNull
            @Override
            public StructureViewModel createStructureViewModel(@Nullable Editor editor) {
                return new EBNFStructureViewModel(psiFile, editor);
            }
        };
    }
}

package uk.ac.gla.teamL;

import com.intellij.extapi.psi.PsiFileBase;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.psi.FileViewProvider;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/**
 * User: nishad
 * Date: 08/11/14
 * Time: 22:19
 */
public class EBNFFile extends PsiFileBase {

    public EBNFFile(@NotNull FileViewProvider viewProvider) {
        super(viewProvider, EBNFLanguage.INSTANCE);
    }

    @NotNull
    @Override
    public FileType getFileType() {
        return EBNFFileType.INSTANCE;
    }

    @Override
    public String toString() {
        return "Extended BNF File";
    }

    @Nullable
    @Override
    public Icon getIcon(int flags) {
        return super.getIcon(flags);
    }
}

// This is a generated file. Not intended for manual editing.
package uk.ac.gla.teamL.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReference;

public interface EBNFIdentifier extends EBNFCompositeElement {

  @NotNull
  PsiElement getId();

  @NotNull
  String getName();

  @NotNull
  PsiElement setName(String newName);

  @NotNull
  PsiElement getNameIdentifier();

  @NotNull
  PsiReference getReference();

}

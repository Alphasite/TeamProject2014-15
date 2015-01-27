// This is a generated file. Not intended for manual editing.
package uk.ac.gla.teamL.psi;

import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReference;
import org.jetbrains.annotations.NotNull;

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

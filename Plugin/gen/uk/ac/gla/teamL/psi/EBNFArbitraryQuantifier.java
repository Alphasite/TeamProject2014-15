// This is a generated file. Not intended for manual editing.
package uk.ac.gla.teamL.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface EBNFArbitraryQuantifier extends EBNFCompositeElement {

  @NotNull
  List<EBNFNum> getNumList();

  @NotNull
  EBNFNum getGetLowerBound();

  @Nullable
  EBNFNum getGetUpperBound();

}

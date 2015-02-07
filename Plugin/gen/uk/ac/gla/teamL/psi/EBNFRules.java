// This is a generated file. Not intended for manual editing.
package uk.ac.gla.teamL.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface EBNFRules extends EBNFCompositeElement {

  @NotNull
  List<EBNFOr> getOrList();

  @NotNull
  List<EBNFRuleElement> getRuleElementList();

  @NotNull
  List<List<EBNFRuleElement>> getRuleSegmentList();

}

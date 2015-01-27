// This is a generated file. Not intended for manual editing.
package uk.ac.gla.teamL.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface EBNFRuleElement extends EBNFCompositeElement {

  @Nullable
  EBNFAny getAny();

  @Nullable
  EBNFIdentifier getIdentifier();

  @Nullable
  EBNFNestedRules getNestedRules();

  @Nullable
  EBNFPredicate getPredicate();

  @Nullable
  EBNFQuantifier getQuantifier();

  @Nullable
  EBNFRange getRange();

  @Nullable
  EBNFString getString();

}

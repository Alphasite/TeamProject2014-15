// This is a generated file. Not intended for manual editing.
package uk.ac.gla.teamL.psi.impl;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import static uk.ac.gla.teamL.psi.EBNFTypes.*;
import uk.ac.gla.teamL.psi.*;

public class EBNFRuleElementImpl extends EBNFCompositeElementImpl implements EBNFRuleElement {

  public EBNFRuleElementImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof EBNFVisitor) ((EBNFVisitor)visitor).visitRuleElement(this);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public EBNFAny getAny() {
    return findChildByClass(EBNFAny.class);
  }

  @Override
  @Nullable
  public EBNFIdentifier getIdentifier() {
    return findChildByClass(EBNFIdentifier.class);
  }

  @Override
  @Nullable
  public EBNFNestedRules getNestedRules() {
    return findChildByClass(EBNFNestedRules.class);
  }

  @Override
  @Nullable
  public EBNFPredicate getPredicate() {
    return findChildByClass(EBNFPredicate.class);
  }

  @Override
  @Nullable
  public EBNFQuantifier getQuantifier() {
    return findChildByClass(EBNFQuantifier.class);
  }

  @Override
  @Nullable
  public EBNFRange getRange() {
    return findChildByClass(EBNFRange.class);
  }

  @Override
  @Nullable
  public EBNFString getString() {
    return findChildByClass(EBNFString.class);
  }

}

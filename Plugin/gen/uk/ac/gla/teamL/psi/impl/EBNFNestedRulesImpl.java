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

public class EBNFNestedRulesImpl extends EBNFCompositeElementImpl implements EBNFNestedRules {

  public EBNFNestedRulesImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof EBNFVisitor) ((EBNFVisitor)visitor).visitNestedRules(this);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public EBNFRules getRules() {
    return findNotNullChildByClass(EBNFRules.class);
  }

}

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

public class EBNFArbitraryQuantifierImpl extends EBNFCompositeElementImpl implements EBNFArbitraryQuantifier {

  public EBNFArbitraryQuantifierImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof EBNFVisitor) ((EBNFVisitor)visitor).visitArbitraryQuantifier(this);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public List<EBNFNum> getNumList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, EBNFNum.class);
  }

  @Override
  @NotNull
  public EBNFNum getGetLowerBound() {
    List<EBNFNum> p1 = getNumList();
    return p1.get(0);
  }

  @Override
  @Nullable
  public EBNFNum getGetUpperBound() {
    List<EBNFNum> p1 = getNumList();
    return p1.size() < 2 ? null : p1.get(1);
  }

}

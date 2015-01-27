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

public class EBNFRangeImpl extends EBNFCompositeElementImpl implements EBNFRange {

  public EBNFRangeImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof EBNFVisitor) ((EBNFVisitor)visitor).visitRange(this);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public List<EBNFString> getStringList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, EBNFString.class);
  }

  @Override
  @NotNull
  public EBNFString getGetLowerBound() {
    List<EBNFString> p1 = getStringList();
    return p1.get(0);
  }

  @Override
  @Nullable
  public EBNFString getGetUpperBound() {
    List<EBNFString> p1 = getStringList();
    return p1.size() < 2 ? null : p1.get(1);
  }

}

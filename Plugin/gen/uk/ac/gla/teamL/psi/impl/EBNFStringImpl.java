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

public class EBNFStringImpl extends EBNFCompositeElementImpl implements EBNFString {

  public EBNFStringImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof EBNFVisitor) ((EBNFVisitor)visitor).visitString(this);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public PsiElement getStringDoubleQuotes() {
    return findChildByType(STRING_DOUBLEQUOTES);
  }

  @Override
  @Nullable
  public PsiElement getStringSingleQuotes() {
    return findChildByType(STRING_SINGLEQUOTES);
  }

  @Override
  @Nullable
  public PsiElement getStringTripleQuotes() {
    return findChildByType(STRING_TRIPLEQUOTES);
  }

  @NotNull
  public String getString() {
    return EBNFParserImplUtil.getString(this);
  }

}

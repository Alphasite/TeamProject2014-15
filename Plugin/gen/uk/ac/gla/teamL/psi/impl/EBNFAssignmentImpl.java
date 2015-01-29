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
import com.intellij.navigation.ItemPresentation;

public class EBNFAssignmentImpl extends EBNFNamedElementImpl implements EBNFAssignment {

  public EBNFAssignmentImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof EBNFVisitor) ((EBNFVisitor)visitor).visitAssignment(this);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public List<EBNFAnnotation> getAnnotationList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, EBNFAnnotation.class);
  }

  @Override
  @NotNull
  public EBNFRules getRules() {
    return findNotNullChildByClass(EBNFRules.class);
  }

  @NotNull
  public String getName() {
    return EBNFParserImplUtil.getName(this);
  }

  @NotNull
  public PsiElement getNameIdentifier() {
    return EBNFParserImplUtil.getNameIdentifier(this);
  }

  @NotNull
  public PsiElement setName(String newName) {
    return EBNFParserImplUtil.setName(this, newName);
  }

  public ItemPresentation getPresentation() {
    return EBNFParserImplUtil.getPresentation(this);
  }

  @Override
  @NotNull
  public EBNFIdentifier getId() {
    return findNotNullChildByClass(EBNFIdentifier.class);
  }

}

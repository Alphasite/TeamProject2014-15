// This is a generated file. Not intended for manual editing.
package uk.ac.gla.teamL.psi.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.PsiReference;
import org.jetbrains.annotations.NotNull;
import uk.ac.gla.teamL.psi.EBNFIdentifier;
import uk.ac.gla.teamL.psi.EBNFVisitor;

import static uk.ac.gla.teamL.psi.EBNFTypes.ID;

public class EBNFIdentifierImpl extends EBNFCompositeElementImpl implements EBNFIdentifier {

  public EBNFIdentifierImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof EBNFVisitor) ((EBNFVisitor)visitor).visitIdentifier(this);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public PsiElement getId() {
    return findNotNullChildByType(ID);
  }

  @NotNull
  public String getName() {
    return EBNFParserImplUtil.getName(this);
  }

  @NotNull
  public PsiElement setName(String newName) {
    return EBNFParserImplUtil.setName(this, newName);
  }

  @NotNull
  public PsiElement getNameIdentifier() {
    return EBNFParserImplUtil.getNameIdentifier(this);
  }

  @NotNull
  public PsiReference getReference() {
    return EBNFParserImplUtil.getReference(this);
  }

}

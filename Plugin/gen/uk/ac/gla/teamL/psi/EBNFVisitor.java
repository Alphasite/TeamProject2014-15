// This is a generated file. Not intended for manual editing.
package uk.ac.gla.teamL.psi;

import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElementVisitor;

public class EBNFVisitor extends PsiElementVisitor {

  public void visitAnnotation(@NotNull EBNFAnnotation o) {
    visitCompositeElement(o);
  }

  public void visitAny(@NotNull EBNFAny o) {
    visitCompositeElement(o);
  }

  public void visitArbitraryQuantifier(@NotNull EBNFArbitraryQuantifier o) {
    visitCompositeElement(o);
  }

  public void visitAssignment(@NotNull EBNFAssignment o) {
    visitNamedElement(o);
  }

  public void visitAssignmentRecover(@NotNull EBNFAssignmentRecover o) {
    visitCompositeElement(o);
  }

  public void visitIdentifier(@NotNull EBNFIdentifier o) {
    visitCompositeElement(o);
  }

  public void visitNestedRules(@NotNull EBNFNestedRules o) {
    visitCompositeElement(o);
  }

  public void visitNum(@NotNull EBNFNum o) {
    visitCompositeElement(o);
  }

  public void visitOr(@NotNull EBNFOr o) {
    visitCompositeElement(o);
  }

  public void visitPredicate(@NotNull EBNFPredicate o) {
    visitCompositeElement(o);
  }

  public void visitQuantifier(@NotNull EBNFQuantifier o) {
    visitCompositeElement(o);
  }

  public void visitRange(@NotNull EBNFRange o) {
    visitCompositeElement(o);
  }

  public void visitRuleElement(@NotNull EBNFRuleElement o) {
    visitCompositeElement(o);
  }

  public void visitRules(@NotNull EBNFRules o) {
    visitCompositeElement(o);
  }

  public void visitString(@NotNull EBNFString o) {
    visitCompositeElement(o);
  }

  public void visitNamedElement(@NotNull EBNFNamedElement o) {
    visitCompositeElement(o);
  }

  public void visitCompositeElement(@NotNull EBNFCompositeElement o) {
    visitElement(o);
  }

}

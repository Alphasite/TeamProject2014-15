// This is a generated file. Not intended for manual editing.
package uk.ac.gla.teamL.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface EBNFAssignment extends EBNFNamedElement {

  @NotNull
  List<EBNFAnnotation> getAnnotationList();

  @NotNull
  EBNFRules getRules();

  @NotNull
  String getName();

  @NotNull
  PsiElement getNameIdentifier();

  @NotNull
  PsiElement setName(String newName);

  @NotNull
  EBNFIdentifier getId();

}

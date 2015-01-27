// This is a generated file. Not intended for manual editing.
package uk.ac.gla.teamL.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface EBNFAssignment extends EBNFCompositeElement {

  @NotNull
  List<EBNFAnnotation> getAnnotationList();

  @NotNull
  EBNFRules getRules();

  String toString();

  @NotNull
  String getName();

  @NotNull
  EBNFIdentifier getId();

}

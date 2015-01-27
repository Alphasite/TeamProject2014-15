// This is a generated file. Not intended for manual editing.
package uk.ac.gla.teamL.psi;

import com.intellij.psi.tree.IElementType;
import com.intellij.psi.PsiElement;
import com.intellij.lang.ASTNode;
import uk.ac.gla.teamL.psi.impl.*;

public interface EBNFTypes {

  IElementType ANNOTATION = new EBNFElementType("ANNOTATION");
  IElementType ANY = new EBNFElementType("ANY");
  IElementType ARBITRARY_QUANTIFIER = new EBNFElementType("ARBITRARY_QUANTIFIER");
  IElementType ASSIGNMENT = new EBNFElementType("ASSIGNMENT");
  IElementType ASSIGNMENT_RECOVER = new EBNFElementType("ASSIGNMENT_RECOVER");
  IElementType IDENTIFIER = new EBNFElementType("IDENTIFIER");
  IElementType NESTED_RULES = new EBNFElementType("NESTED_RULES");
  IElementType NUM = new EBNFElementType("NUM");
  IElementType OR = new EBNFElementType("OR");
  IElementType PREDICATE = new EBNFElementType("PREDICATE");
  IElementType QUANTIFIER = new EBNFElementType("QUANTIFIER");
  IElementType RANGE = new EBNFElementType("RANGE");
  IElementType RULES = new EBNFElementType("RULES");
  IElementType RULE_ELEMENT = new EBNFElementType("RULE_ELEMENT");
  IElementType STRING = new EBNFElementType("STRING");

  IElementType ANNOTATION_OPERATOR = new EBNFTokenType("@");
  IElementType ANY_OPERATOR = new EBNFTokenType(".");
  IElementType COMMENT_BLOCK = new EBNFTokenType("comment_block");
  IElementType COMMENT_SINGLELINE = new EBNFTokenType("comment_singleline");
  IElementType EQ = new EBNFTokenType("=");
  IElementType ID = new EBNFTokenType("ID");
  IElementType LB = new EBNFTokenType("(");
  IElementType LCB = new EBNFTokenType("{");
  IElementType LET = new EBNFTokenType("let");
  IElementType LIST_SEPERATOR = new EBNFTokenType(",");
  IElementType LSB = new EBNFTokenType("[");
  IElementType NEGATION_OPERATOR = new EBNFTokenType("!");
  IElementType NUMBERS = new EBNFTokenType("NUMBERS");
  IElementType ONE_OR_MORE = new EBNFTokenType("+");
  IElementType OR_OPERATOR = new EBNFTokenType("|");
  IElementType RANGE_OPERATOR = new EBNFTokenType("..");
  IElementType RB = new EBNFTokenType(")");
  IElementType RCB = new EBNFTokenType("}");
  IElementType RSB = new EBNFTokenType("]");
  IElementType STRING_DOUBLEQUOTES = new EBNFTokenType("string_doubleQuotes");
  IElementType STRING_SINGLEQUOTES = new EBNFTokenType("string_singleQuotes");
  IElementType STRING_TRIPLEQUOTES = new EBNFTokenType("string_tripleQuotes");
  IElementType TERMINAL = new EBNFTokenType("terminal");
  IElementType ZERO_OR_MORE = new EBNFTokenType("*");
  IElementType ZERO_OR_ONE = new EBNFTokenType("?");

  class Factory {
    public static PsiElement createElement(ASTNode node) {
      IElementType type = node.getElementType();
       if (type == ANNOTATION) {
        return new EBNFAnnotationImpl(node);
      }
      else if (type == ANY) {
        return new EBNFAnyImpl(node);
      }
      else if (type == ARBITRARY_QUANTIFIER) {
        return new EBNFArbitraryQuantifierImpl(node);
      }
      else if (type == ASSIGNMENT) {
        return new EBNFAssignmentImpl(node);
      }
      else if (type == ASSIGNMENT_RECOVER) {
        return new EBNFAssignmentRecoverImpl(node);
      }
      else if (type == IDENTIFIER) {
        return new EBNFIdentifierImpl(node);
      }
      else if (type == NESTED_RULES) {
        return new EBNFNestedRulesImpl(node);
      }
      else if (type == NUM) {
        return new EBNFNumImpl(node);
      }
      else if (type == OR) {
        return new EBNFOrImpl(node);
      }
      else if (type == PREDICATE) {
        return new EBNFPredicateImpl(node);
      }
      else if (type == QUANTIFIER) {
        return new EBNFQuantifierImpl(node);
      }
      else if (type == RANGE) {
        return new EBNFRangeImpl(node);
      }
      else if (type == RULES) {
        return new EBNFRulesImpl(node);
      }
      else if (type == RULE_ELEMENT) {
        return new EBNFRuleElementImpl(node);
      }
      else if (type == STRING) {
        return new EBNFStringImpl(node);
      }
      throw new AssertionError("Unknown element type: " + type);
    }
  }
}

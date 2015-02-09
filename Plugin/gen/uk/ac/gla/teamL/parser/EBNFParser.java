// This is a generated file. Not intended for manual editing.
package uk.ac.gla.teamL.parser;

import com.intellij.lang.PsiBuilder;
import com.intellij.lang.PsiBuilder.Marker;
import static uk.ac.gla.teamL.psi.EBNFTypes.*;
import static uk.ac.gla.teamL.parser.EBNFParserUtil.*;
import com.intellij.psi.tree.IElementType;
import com.intellij.lang.ASTNode;
import com.intellij.psi.tree.TokenSet;
import com.intellij.lang.PsiParser;

@SuppressWarnings({"SimplifiableIfStatement", "UnusedAssignment"})
public class EBNFParser implements PsiParser {

  public ASTNode parse(IElementType t, PsiBuilder b) {
    parseLight(t, b);
    return b.getTreeBuilt();
  }

  public void parseLight(IElementType t, PsiBuilder b) {
    boolean r;
    b = adapt_builder_(t, b, this, null);
    Marker m = enter_section_(b, 0, _COLLAPSE_, null);
    if (t == EOL) {
      r = EOL(b, 0);
    }
    else if (t == LINE_WS) {
      r = LINE_WS(b, 0);
    }
    else if (t == WHITE_SPACE) {
      r = WHITE_SPACE(b, 0);
    }
    else if (t == ANNOTATION) {
      r = annotation(b, 0);
    }
    else if (t == ANY) {
      r = any(b, 0);
    }
    else if (t == ARBITRARY_QUANTIFIER) {
      r = arbitraryQuantifier(b, 0);
    }
    else if (t == ASSIGNMENT) {
      r = assignment(b, 0);
    }
    else if (t == ASSIGNMENT_RECOVER) {
      r = assignment_recover(b, 0);
    }
    else if (t == EQUALS) {
      r = equals(b, 0);
    }
    else if (t == IDENTIFIER) {
      r = identifier(b, 0);
    }
    else if (t == NESTED_RULES) {
      r = nestedRules(b, 0);
    }
    else if (t == NUM) {
      r = num(b, 0);
    }
    else if (t == OR) {
      r = or(b, 0);
    }
    else if (t == PREDICATE) {
      r = predicate(b, 0);
    }
    else if (t == QUANTIFIER) {
      r = quantifier(b, 0);
    }
    else if (t == RANGE) {
      r = range(b, 0);
    }
    else if (t == RULE_ELEMENT) {
      r = ruleElement(b, 0);
    }
    else if (t == RULES) {
      r = rules(b, 0);
    }
    else if (t == RULES_SEGMENT) {
      r = rulesSegment(b, 0);
    }
    else if (t == STRING) {
      r = string(b, 0);
    }
    else {
      r = parse_root_(t, b, 0);
    }
    exit_section_(b, 0, m, t, r, true, TRUE_CONDITION);
  }

  protected boolean parse_root_(IElementType t, PsiBuilder b, int l) {
    return program(b, l + 1);
  }

  /* ********************************************************** */
  // "regexp:\\r|\\n|\\r\\n"
  public static boolean EOL(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "EOL")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<eol>");
    r = consumeToken(b, "regexp:\\r|\\n|\\r\\n");
    exit_section_(b, l, m, EOL, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // "regexp:[\\ \\t\\f]"
  public static boolean LINE_WS(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "LINE_WS")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<line ws>");
    r = consumeToken(b, "regexp:[\\ \\t\\f]");
    exit_section_(b, l, m, LINE_WS, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // (LINE_WS|EOL)+
  public static boolean WHITE_SPACE(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "WHITE_SPACE")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<white space>");
    r = WHITE_SPACE_0(b, l + 1);
    int c = current_position_(b);
    while (r) {
      if (!WHITE_SPACE_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "WHITE_SPACE", c)) break;
      c = current_position_(b);
    }
    exit_section_(b, l, m, WHITE_SPACE, r, false, null);
    return r;
  }

  // LINE_WS|EOL
  private static boolean WHITE_SPACE_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "WHITE_SPACE_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = LINE_WS(b, l + 1);
    if (!r) r = EOL(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // ANNOTATION_OPERATOR ID
  public static boolean annotation(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "annotation")) return false;
    if (!nextTokenIs(b, ANNOTATION_OPERATOR)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, ANNOTATION_OPERATOR, ID);
    exit_section_(b, m, ANNOTATION, r);
    return r;
  }

  /* ********************************************************** */
  // ANY_OPERATOR
  public static boolean any(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "any")) return false;
    if (!nextTokenIs(b, ANY_OPERATOR)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, ANY_OPERATOR);
    exit_section_(b, m, ANY, r);
    return r;
  }

  /* ********************************************************** */
  // LCB num (LIST_SEPERATOR num)? RCB
  public static boolean arbitraryQuantifier(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "arbitraryQuantifier")) return false;
    if (!nextTokenIs(b, LCB)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, LCB);
    r = r && num(b, l + 1);
    r = r && arbitraryQuantifier_2(b, l + 1);
    r = r && consumeToken(b, RCB);
    exit_section_(b, m, ARBITRARY_QUANTIFIER, r);
    return r;
  }

  // (LIST_SEPERATOR num)?
  private static boolean arbitraryQuantifier_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "arbitraryQuantifier_2")) return false;
    arbitraryQuantifier_2_0(b, l + 1);
    return true;
  }

  // LIST_SEPERATOR num
  private static boolean arbitraryQuantifier_2_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "arbitraryQuantifier_2_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, LIST_SEPERATOR);
    r = r && num(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // annotation* let identifier EQ rules terminal
  public static boolean assignment(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "assignment")) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, "<assignment>");
    r = assignment_0(b, l + 1);
    r = r && consumeToken(b, LET);
    r = r && identifier(b, l + 1);
    r = r && consumeToken(b, EQ);
    r = r && rules(b, l + 1);
    p = r; // pin = rules
    r = r && consumeToken(b, TERMINAL);
    exit_section_(b, l, m, ASSIGNMENT, r, p, assignment_recover_parser_);
    return r || p;
  }

  // annotation*
  private static boolean assignment_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "assignment_0")) return false;
    int c = current_position_(b);
    while (true) {
      if (!annotation(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "assignment_0", c)) break;
      c = current_position_(b);
    }
    return true;
  }

  /* ********************************************************** */
  // !(let | terminal | LSB | RSB | LB | RB | LCB | RCB | ANNOTATION_OPERATOR)
  public static boolean assignment_recover(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "assignment_recover")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NOT_, "<assignment recover>");
    r = !assignment_recover_0(b, l + 1);
    exit_section_(b, l, m, ASSIGNMENT_RECOVER, r, false, null);
    return r;
  }

  // let | terminal | LSB | RSB | LB | RB | LCB | RCB | ANNOTATION_OPERATOR
  private static boolean assignment_recover_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "assignment_recover_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, LET);
    if (!r) r = consumeToken(b, TERMINAL);
    if (!r) r = consumeToken(b, LSB);
    if (!r) r = consumeToken(b, RSB);
    if (!r) r = consumeToken(b, LB);
    if (!r) r = consumeToken(b, RB);
    if (!r) r = consumeToken(b, LCB);
    if (!r) r = consumeToken(b, RCB);
    if (!r) r = consumeToken(b, ANNOTATION_OPERATOR);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // EQ
  public static boolean equals(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "equals")) return false;
    if (!nextTokenIs(b, EQ)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, EQ);
    exit_section_(b, m, EQUALS, r);
    return r;
  }

  /* ********************************************************** */
  // ID
  public static boolean identifier(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "identifier")) return false;
    if (!nextTokenIs(b, ID)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, ID);
    exit_section_(b, m, IDENTIFIER, r);
    return r;
  }

  /* ********************************************************** */
  // LB rules RB
  public static boolean nestedRules(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "nestedRules")) return false;
    if (!nextTokenIs(b, LB)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, LB);
    r = r && rules(b, l + 1);
    r = r && consumeToken(b, RB);
    exit_section_(b, m, NESTED_RULES, r);
    return r;
  }

  /* ********************************************************** */
  // NUMBERS
  public static boolean num(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "num")) return false;
    if (!nextTokenIs(b, NUMBERS)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, NUMBERS);
    exit_section_(b, m, NUM, r);
    return r;
  }

  /* ********************************************************** */
  // OR_OPERATOR
  public static boolean or(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "or")) return false;
    if (!nextTokenIs(b, OR_OPERATOR)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, OR_OPERATOR);
    exit_section_(b, m, OR, r);
    return r;
  }

  /* ********************************************************** */
  // NEGATION_OPERATOR
  public static boolean predicate(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "predicate")) return false;
    if (!nextTokenIs(b, NEGATION_OPERATOR)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, NEGATION_OPERATOR);
    exit_section_(b, m, PREDICATE, r);
    return r;
  }

  /* ********************************************************** */
  // assignment *
  static boolean program(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "program")) return false;
    Marker m = enter_section_(b, l, _NONE_, null);
    int c = current_position_(b);
    while (true) {
      if (!assignment(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "program", c)) break;
      c = current_position_(b);
    }
    exit_section_(b, l, m, null, true, false, assignment_recover_parser_);
    return true;
  }

  /* ********************************************************** */
  // ZERO_OR_MORE
  // 			 | ZERO_OR_ONE
  // 			 | ONE_OR_MORE
  // 			 | arbitraryQuantifier
  public static boolean quantifier(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "quantifier")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<quantifier>");
    r = consumeToken(b, ZERO_OR_MORE);
    if (!r) r = consumeToken(b, ZERO_OR_ONE);
    if (!r) r = consumeToken(b, ONE_OR_MORE);
    if (!r) r = arbitraryQuantifier(b, l + 1);
    exit_section_(b, l, m, QUANTIFIER, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // LSB string RANGE_OPERATOR string RSB
  public static boolean range(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "range")) return false;
    if (!nextTokenIs(b, LSB)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, LSB);
    r = r && string(b, l + 1);
    r = r && consumeToken(b, RANGE_OPERATOR);
    r = r && string(b, l + 1);
    r = r && consumeToken(b, RSB);
    exit_section_(b, m, RANGE, r);
    return r;
  }

  /* ********************************************************** */
  // predicate?
  // 	          ( string
  // 	          | identifier
  // 	          | range
  // 	          | nestedRules
  // 	          | any
  // 	          ) quantifier?
  public static boolean ruleElement(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ruleElement")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<element>");
    r = ruleElement_0(b, l + 1);
    r = r && ruleElement_1(b, l + 1);
    r = r && ruleElement_2(b, l + 1);
    exit_section_(b, l, m, RULE_ELEMENT, r, false, null);
    return r;
  }

  // predicate?
  private static boolean ruleElement_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ruleElement_0")) return false;
    predicate(b, l + 1);
    return true;
  }

  // string
  // 	          | identifier
  // 	          | range
  // 	          | nestedRules
  // 	          | any
  private static boolean ruleElement_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ruleElement_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = string(b, l + 1);
    if (!r) r = identifier(b, l + 1);
    if (!r) r = range(b, l + 1);
    if (!r) r = nestedRules(b, l + 1);
    if (!r) r = any(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // quantifier?
  private static boolean ruleElement_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ruleElement_2")) return false;
    quantifier(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // rulesSegment (or rulesSegment)* {
  // //	pin = 2
  // //	recoverWhile = rulesRecover
  // }
  public static boolean rules(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "rules")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<rules>");
    r = rulesSegment(b, l + 1);
    r = r && rules_1(b, l + 1);
    r = r && rules_2(b, l + 1);
    exit_section_(b, l, m, RULES, r, false, null);
    return r;
  }

  // (or rulesSegment)*
  private static boolean rules_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "rules_1")) return false;
    int c = current_position_(b);
    while (true) {
      if (!rules_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "rules_1", c)) break;
      c = current_position_(b);
    }
    return true;
  }

  // or rulesSegment
  private static boolean rules_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "rules_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = or(b, l + 1);
    r = r && rulesSegment(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // {
  // //	pin = 2
  // //	recoverWhile = rulesRecover
  // }
  private static boolean rules_2(PsiBuilder b, int l) {
    return true;
  }

  /* ********************************************************** */
  // ruleElement+
  public static boolean rulesSegment(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "rulesSegment")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<rules segment>");
    r = ruleElement(b, l + 1);
    int c = current_position_(b);
    while (r) {
      if (!ruleElement(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "rulesSegment", c)) break;
      c = current_position_(b);
    }
    exit_section_(b, l, m, RULES_SEGMENT, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // string_tripleQuotes
  // 		 | string_doubleQuotes
  // 		 | string_singleQuotes
  public static boolean string(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "string")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<String>");
    r = consumeToken(b, STRING_TRIPLEQUOTES);
    if (!r) r = consumeToken(b, STRING_DOUBLEQUOTES);
    if (!r) r = consumeToken(b, STRING_SINGLEQUOTES);
    exit_section_(b, l, m, STRING, r, false, null);
    return r;
  }

  final static Parser assignment_recover_parser_ = new Parser() {
    public boolean parse(PsiBuilder b, int l) {
      return assignment_recover(b, l + 1);
    }
  };
}

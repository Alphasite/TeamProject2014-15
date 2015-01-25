package uk.ac.gla.teamL.lexer;

import com.intellij.lexer.FlexAdapter;

/**
 * User: nishad
 * Date: 08/11/14
 * Time: 22:28
 */
public class EBNFLexer extends FlexAdapter {
  public EBNFLexer() {
    super(new _EBNFLexer());
  }
}

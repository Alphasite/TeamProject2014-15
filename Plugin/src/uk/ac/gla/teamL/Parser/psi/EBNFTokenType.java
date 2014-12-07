package uk.ac.gla.teamL.parser.psi;

import com.intellij.psi.tree.IElementType;
import uk.ac.gla.teamL.EBNFLanguage;

/**
 * User: nishad
 * Date: 08/11/14
 * Time: 21:34
 */
public class EBNFTokenType extends IElementType {

    public EBNFTokenType(String debugName) {
        super(debugName, EBNFLanguage.INSTANCE);
    }

    @Override
    public String toString() {
        return "EBNFTokenType(" + super.toString() + ")";
    }
}

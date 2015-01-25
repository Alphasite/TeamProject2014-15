package uk.ac.gla.teamL.psi;

import com.intellij.psi.tree.IElementType;
import uk.ac.gla.teamL.EBNFLanguage;

/**
 * User: nishad
 * Date: 08/11/14
 * Time: 21:36
 */
public class EBNFElementType extends IElementType {
    public EBNFElementType(String debugName) {
        super(debugName, EBNFLanguage.INSTANCE);
    }
}

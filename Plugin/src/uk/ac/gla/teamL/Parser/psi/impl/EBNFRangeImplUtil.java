package uk.ac.gla.teamL.parser.psi.impl;

import com.intellij.lang.ASTNode;

/**
 * User: nishad
 * Date: 18/11/14
 * Time: 11:07
 */
public class EBNFRangeImplUtil extends EBNFCompositeElementImpl {
    public EBNFRangeImplUtil(ASTNode node) {
        super(node);
    }

    private static char getChar(String string) {
        return string.charAt(string.length() - 2);
    }

    public static char getLowerBound(EBNFRangeImpl node) {
        return (char) -1; // TODO implement.
    }

    public static char getUpperBound(EBNFRangeImpl node) {
        return (char) -1; // TODO implement.
    }
}

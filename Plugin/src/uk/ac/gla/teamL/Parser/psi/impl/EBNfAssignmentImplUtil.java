package uk.ac.gla.teamL.parser.psi.impl;

import com.intellij.lang.ASTNode;
import uk.ac.gla.teamL.parser.psi.EBNFIdentifier;

/**
 * User: nishad
 * Date: 23/11/14
 * Time: 20:45
 */
public class EBNfAssignmentImplUtil extends EBNFCompositeElementImpl {
    public EBNfAssignmentImplUtil(ASTNode node) {
        super(node);
    }

    public static String getRuleName(EBNFAssignmentImpl node) {
        EBNFIdentifier id = node.getIdentifier();

        if (id instanceof EBNFIdentifierImpl) {
            return ((EBNFIdentifierImpl) id).getName();
        } else {
            return "Probably something wrong here, the id wasn't an id??";
        }
    }
}

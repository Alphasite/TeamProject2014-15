package uk.ac.gla.teamL.parser.psi.impl;

import com.intellij.lang.ASTNode;
import org.jetbrains.annotations.NotNull;

/**
 * User: nishad
 * Date: 18/11/14
 * Time: 11:11
 */
public class EBNFIdentifierImplUtil extends EBNFCompositeElementImpl {
    public EBNFIdentifierImplUtil(ASTNode node) {
        super(node);
    }

    public static String getRuleName(@NotNull EBNFIdentifierImpl node) {
        return node.getId().getText();
    }
}

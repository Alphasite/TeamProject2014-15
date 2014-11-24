package uk.ac.gla.teamL.editor;

import com.intellij.codeInsight.daemon.RelatedItemLineMarkerInfo;
import com.intellij.codeInsight.daemon.RelatedItemLineMarkerProvider;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiLiteralExpression;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

/**
 * User: nishad
 * Date: 18/11/14
 * Time: 11:00
 */
public class EBNFLineMarkerProvider extends RelatedItemLineMarkerProvider {
    @Override
    protected void collectNavigationMarkers(@NotNull PsiElement node, Collection<? super RelatedItemLineMarkerInfo> result) {
        super.collectNavigationMarkers(node, result);

        if (node instanceof PsiLiteralExpression) {
            // TODO implement.
        }
    }
}

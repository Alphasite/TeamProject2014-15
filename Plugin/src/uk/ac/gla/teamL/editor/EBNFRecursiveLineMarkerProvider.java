package uk.ac.gla.teamL.editor;

import com.intellij.codeHighlighting.Pass;
import com.intellij.codeInsight.daemon.LineMarkerInfo;
import com.intellij.codeInsight.daemon.LineMarkerProvider;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.editor.markup.GutterIconRenderer;
import com.intellij.psi.PsiElement;
import com.intellij.util.FunctionUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import uk.ac.gla.teamL.EBNFIcon;
import uk.ac.gla.teamL.parser.psi.EBNFAssignment;
import uk.ac.gla.teamL.parser.psi.EBNFIdentifier;

import java.util.Collection;
import java.util.List;

import static com.intellij.psi.util.PsiTreeUtil.findChildrenOfType;

/**
 * User: nishad
 * Date: 18/11/14
 * Time: 11:00
 */
public class EBNFRecursiveLineMarkerProvider implements LineMarkerProvider {

    @Nullable
    @Override
    public LineMarkerInfo getLineMarkerInfo(@NotNull PsiElement psiElement) {
        return null;
    }

    @Override
    public void collectSlowLineMarkers(@NotNull List<PsiElement> list, @NotNull Collection<LineMarkerInfo> lines) {
        for (PsiElement node : list) {
            if (node instanceof EBNFAssignment) {
                EBNFAssignment assignment = (EBNFAssignment) node;
                String name = assignment.getName();

                for (EBNFIdentifier id : findChildrenOfType(assignment.getRules(), EBNFIdentifier.class)) {
                    if (id.getName() != null && id.getName().equals(name)) {
                        lines.add(new RecursiveMarker(assignment));
                        break;
                    }
                }
//                for (EBNFRuleElement element: assignment.getRules().getRuleElementList()) {
//                    Collection<EBNFIdentifier> identifiers = new ArrayList<>();
//
//                    if (element instanceof EBNFIdentifier) {
//                        identifiers.add((EBNFIdentifier) element);
//                    } else {
//                        identifiers = PsiTreeUtil.findChildrenOfType(element, EBNFIdentifier.class);
//                    }
//
//                    for (EBNFIdentifier id: identifiers) {
//                        if (id.getName() != null && id.getName().equals(name)) {
//                            lines.add(new RecursiveMarker(assignment));
//                            break;
//                        }
//                    }
//                }

            }
        }
    }

    private static class RecursiveMarker extends LineMarkerInfo<EBNFAssignment> {

        public RecursiveMarker(@NotNull EBNFAssignment element) {
            super(
                         element,
                         element.getTextRange(),
                         EBNFIcon.RECURSIVE,
                         Pass.UPDATE_OVERRIDEN_MARKERS,
                         FunctionUtil.<EBNFAssignment, String>constant("Recursive"),
                         null,
                         GutterIconRenderer.Alignment.RIGHT
            );
        }

        @Nullable
        @Override
        public GutterIconRenderer createGutterRenderer() {
            return new LineMarkerGutterIconRenderer<EBNFAssignment>(this) {
                @Override
                public AnAction getClickAction() {
                    //noinspection ConstantConditions
                    return null;
                }
            };
        }
    }
}

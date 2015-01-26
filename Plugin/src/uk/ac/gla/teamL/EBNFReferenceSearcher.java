// TODO do!-
//package uk.ac.gla.teamL;
//
//import com.intellij.openapi.application.QueryExecutorBase;
//import com.intellij.psi.PsiElement;
//import com.intellij.psi.PsiFile;
//import com.intellij.psi.PsiReference;
//import com.intellij.psi.search.LocalSearchScope;
//import com.intellij.psi.search.SearchScope;
//import com.intellij.psi.search.searches.ReferencesSearch;
//import com.intellij.util.Processor;
//import org.jetbrains.annotations.NotNull;
//import uk.ac.gla.teamL.parser.EBNFParserUtil;
//import uk.ac.gla.teamL.psi.EBNFAssignment;
//import uk.ac.gla.teamL.psi.EBNFIdentifier;
//
///**
// * User: nishad
// * Date: 26/01/15
// * Time: 11:52
// */
//public class EBNFAttrPatternRefSearcher extends QueryExecutorBase<PsiReference, ReferencesSearch.SearchParameters> {
//    public EBNFAttrPatternRefSearcher() {
//        super(true);
//    }
//
//    @Override
//    public void processQuery(@NotNull ReferencesSearch.SearchParameters queryParameters, @NotNull final Processor<PsiReference> consumer) {
//        final PsiElement target = queryParameters.getElementToSearch();
//        if (!(target instanceof EBNFIdentifier)) return;
//
//        SearchScope scope = queryParameters.getEffectiveSearchScope();
//        if (!(scope instanceof LocalSearchScope)) return;
//
//        PsiFile file = target.getContainingFile();
//        if (!(file instanceof EBNFFile)) return;
//
//        for (EBNFAssignment rules : EBNFParserUtil.findIdentifiers(file)) {
//            for (BnfAttr attr : attrs.getAttrList()) {
//                BnfAttrPattern pattern = attr.getAttrPattern();
//                if (pattern == null) continue;
//                BnfStringLiteralExpression patternExpression = pattern.getLiteralExpression();
//
//                PsiReference ref = BnfStringImpl.matchesElement(patternExpression, target) ? patternExpression.getReference() : null;
//                if (ref != null && ref.isReferenceTo(target)) {
//                    if (!consumer.process(ref)) return;
//                }
//            }
//        }
//    }
//}

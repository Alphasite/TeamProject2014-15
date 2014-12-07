package uk.ac.gla.teamL.editor;

import com.intellij.codeInsight.completion.*;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiReference;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.util.ProcessingContext;
import org.jetbrains.annotations.NotNull;
import uk.ac.gla.teamL.EBNFFile;
import uk.ac.gla.teamL.parser.EBNFLexer;
import uk.ac.gla.teamL.parser.EBNFParserUtil;
import uk.ac.gla.teamL.parser.psi.EBNFAssignment;
import uk.ac.gla.teamL.parser.psi.EBNFCompositeElement;
import uk.ac.gla.teamL.parser.psi.EBNFNestedRules;
import uk.ac.gla.teamL.parser.psi.impl.EBNFAssignmentImpl;

import java.util.List;

import static com.intellij.patterns.PlatformPatterns.psiElement;

/**
 * User: nishad
 * Date: 21/11/14
 * Time: 19:57
 */
public class EBNFCompletionContributor extends CompletionContributor {
    public EBNFCompletionContributor() {
        extend(CompletionType.BASIC, psiElement().inFile(PlatformPatterns.instanceOf(EBNFFile.class)),
            new CompletionProvider<CompletionParameters>() {
            @Override
            protected void addCompletions(@NotNull CompletionParameters completionParameters, ProcessingContext processingContext, @NotNull CompletionResultSet completionResultSet) {
                PsiElement cursor = completionParameters.getPosition();
                EBNFCompositeElement parent = PsiTreeUtil.getParentOfType(cursor, EBNFAssignment.class, EBNFNestedRules.class);

                if (parent != null) {
                    final EBNFLexer lexer = new EBNFLexer();

                    PsiFile file = completionParameters.getPosition().getContainingFile();

                    PsiReference partialID = file.findReferenceAt(completionParameters.getOffset());

                    List<EBNFAssignmentImpl> rules = EBNFParserUtil.findRules(file);

                    for (EBNFAssignmentImpl rule: rules) {
                        if (rule != null) {

                            String name = rule.getId().getName();
                            if (name != null) {
                                completionResultSet.addElement(LookupElementBuilder.create(name));
                            }
                        }
                    }
                }


            }
        });
    }
}

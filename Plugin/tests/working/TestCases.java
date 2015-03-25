package working;

import com.intellij.codeInsight.completion.CompletionType;
import com.intellij.codeInsight.generation.actions.CommentByLineCommentAction;
import com.intellij.psi.PsiElement;
import com.intellij.testFramework.fixtures.LightCodeInsightFixtureTestCase;
import com.intellij.usageView.UsageInfo;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * Created by rosie on 10/03/15.
 */
public class TestCases extends LightCodeInsightFixtureTestCase {


    @Override
    protected String getTestDataPath() {
        return "Plugin/TestAssets/working";
    }

    public void testAnnotator() {
        myFixture.configureByFiles("DefaultTestData.ebnf", "AnnotatorTestData.ebnf");
        myFixture.checkHighlighting(true, true, true);
    }

    public void testCompletion() {
        myFixture.configureByFiles("CompletionTestData.ebnf");
        myFixture.complete(CompletionType.BASIC, 1);
        List<String> strings = myFixture.getLookupElementStrings();
        assert strings != null;

        assertTrue(strings.containsAll(Arrays.asList("a", "b")));
        assertEquals(2, strings.size());
    }

    // Ill need to figure out why this keeps crashing, since it doenst pass nro fail properly.
//    public void testFormatter() {
//        myFixture.configureByFiles("FormatterTestData.ebnf");
//
//        ApplicationManager.getApplication().runWriteAction(new Runnable() {
//            @Override
//            public void run() {
//                CodeStyleManager.getInstance(getProject()).reformat(myFixture.getFile());
//            }
//        });
//
//        myFixture.checkResultByFile("FormatterFormattedTestData.ebnf");
//    }

    public void testRename() {
        myFixture.configureByFiles("RenameTestData.ebnf");
        myFixture.renameElementAtCaret("char");
        myFixture.checkResultByFile("RenameTestData.ebnf", "RenameTestDataAfter.ebnf", true);
    }


    public void testFolding() {
        myFixture.testFolding(getTestDataPath() + "/FoldTest.ebnf");
    }

    public void testFindUsages() {
        Collection<UsageInfo> usages = myFixture.testFindUsages("FindUsageTestData.ebnf");
        assertEquals(3, usages.size());
    }


    public void testCommenter() {
        myFixture.configureByFile("CommenterTestData.ebnf");
        CommentByLineCommentAction commentAction = new CommentByLineCommentAction();
        commentAction.actionPerformedImpl(getProject(), myFixture.getEditor());
        myFixture.checkResultByFile("CommenterTestDataCommented.ebnf");
        commentAction.actionPerformedImpl(getProject(), myFixture.getEditor());
        myFixture.checkResultByFile("CommenterTestDataUncommented.ebnf");
    }

    public void testReference() {
        myFixture.configureByFiles("ReferenceTestData.ebnf");
        PsiElement element = myFixture.getFile().findElementAt(myFixture.getCaretOffset()).getParent();
        assertEquals("let b = b;", element.getReference().resolve().getText());
    }
}


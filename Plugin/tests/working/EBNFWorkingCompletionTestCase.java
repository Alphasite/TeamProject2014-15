package working;

import com.intellij.codeInsight.completion.CompletionType;
import com.intellij.testFramework.fixtures.LightCodeInsightFixtureTestCase;

import java.util.Arrays;
import java.util.List;

/**
 * Created by rosie on 10/03/15.
 */
public class EBNFWorkingCompletionTestCase extends LightCodeInsightFixtureTestCase {


    @Override
    protected String getTestDataPath() {
        return "working";
    }

    public void testAnnotator() {
        myFixture.configureByFiles("DefaultTestData.ebnf");
        myFixture.checkHighlighting(false, false, true);
    }

    public void testCompletion() {
        myFixture.configureByFiles("DefaultTestData.ebnf");
        myFixture.complete(CompletionType.BASIC, 1);
        List<String> strings = myFixture.getLookupElementStrings();
        assertTrue(strings.containsAll(Arrays.asList("program", "assignment", "rules", "ruleElement")));
        assertEquals(19, strings.size());
    }

}


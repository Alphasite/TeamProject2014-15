package working;

import com.intellij.testFramework.ParsingTestCase;
import uk.ac.gla.teamL.parser.EBNFParserDefinition;

/**
 * User: nishad
 * Date: 03/03/15
 * Time: 00:16
 */
public class EBNFWorkingParserTestCase extends ParsingTestCase {
    public EBNFWorkingParserTestCase() {
        super("", "ebnf", new EBNFParserDefinition());
    }

    public void testParsingTestData() {
        doTest(true);
    }

    @Override
    protected String getTestDataPath() {
        return "working";
    }

    @Override
    protected boolean skipSpaces() {
        return false;
    }

    @Override
    protected boolean includeRanges() {
        return true;
    }
}

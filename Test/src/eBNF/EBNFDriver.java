package Old;

import org.antlr.runtime.ANTLRInputStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.tree.Tree;

import java.io.FileInputStream;

public class EBNFDriver {
    public static void main(String[] args) throws Exception {
        ANTLRInputStream input = new ANTLRInputStream(new FileInputStream(args[0]));
        ebnfLexer lexer = new ebnfLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        ebnfParser parser = new ebnfParser(tokens);
        String tree = ((Tree) parser.program().getTree()).toStringTree();
        System.out.println(tree);
    }
}
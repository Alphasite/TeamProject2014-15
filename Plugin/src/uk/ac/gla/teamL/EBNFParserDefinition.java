package uk.ac.gla.teamL;

import com.intellij.lang.ASTNode;
import com.intellij.lang.Language;
import com.intellij.lang.ParserDefinition;
import com.intellij.lang.PsiParser;
import com.intellij.lexer.Lexer;
import com.intellij.openapi.project.Project;
import com.intellij.psi.FileViewProvider;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.TokenType;
import com.intellij.psi.tree.IFileElementType;
import com.intellij.psi.tree.TokenSet;
import org.jetbrains.annotations.NotNull;
import uk.ac.gla.teamL.Parser.EBNFLexer;
import uk.ac.gla.teamL.Parser.EBNFParser;
import uk.ac.gla.teamL.Parser.psi.EBNFTypes;

/**
 * User: nishad
 * Date: 08/11/14
 * Time: 22:07
 */
public class EBNFParserDefinition implements ParserDefinition {
    public static final TokenSet WHITE_SPACES = TokenSet.create(TokenType.WHITE_SPACE);
    public static final TokenSet COMMENTS = TokenSet.create(EBNFTypes.COMMENT_BLOCK, EBNFTypes.COMMENT_SINGLELINE);
    public static final TokenSet STRINGS = TokenSet.create(
        EBNFTypes.STRING,
        EBNFTypes.STRING_SINGLEQUOTES,
        EBNFTypes.STRING_DOUBLEQUOTES,
        EBNFTypes.STRING_TRIPLEQUOTES
    );

    public static final IFileElementType FILE = new IFileElementType(
        Language.findInstance(EBNFLanguage.class)
    );

    @NotNull
    @Override
    public Lexer createLexer(Project project) {
        return new EBNFLexer();
    }

    @Override
    public PsiParser createParser(Project project) {
        return new EBNFParser();
    }

    @Override
    public IFileElementType getFileNodeType() {
        return FILE;
    }

    @NotNull
    @Override
    public TokenSet getWhitespaceTokens() {
        return WHITE_SPACES;
    }

    @NotNull
    @Override
    public TokenSet getCommentTokens() {
        return COMMENTS;
    }

    @NotNull
    @Override
    public TokenSet getStringLiteralElements() {
        return STRINGS;
    }

    @NotNull
    @Override
    public PsiElement createElement(ASTNode astNode) {
        return EBNFTypes.Factory.createElement(astNode);
    }

    @Override
    public PsiFile createFile(FileViewProvider fileViewProvider) {
        return new EBNFFile(fileViewProvider);
    }

    @Override
    public SpaceRequirements spaceExistanceTypeBetweenTokens(ASTNode astNode, ASTNode astNode1) {
        return SpaceRequirements.MAY;
    }
}

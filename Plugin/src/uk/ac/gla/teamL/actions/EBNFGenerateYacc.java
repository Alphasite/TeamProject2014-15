package uk.ac.gla.teamL.actions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.psi.PsiFile;
import org.jetbrains.annotations.NotNull;
import uk.ac.gla.teamL.EBNFFile;
import uk.ac.gla.teamL.translators.Translator;
import uk.ac.gla.teamL.translators.YaccTranslator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * User: nishad
 * Date: 12/12/14
 * Time: 15:35
 */
public class EBNFGenerateYacc extends AnAction {
    @Override
    public void actionPerformed(@NotNull AnActionEvent anActionEvent) {
        PsiFile file = anActionEvent.getData(LangDataKeys.PSI_FILE);
        YaccTranslator translator = new YaccTranslator();

        if (file instanceof EBNFFile) {
            translator.translate((EBNFFile) file);

            String name = file.getName().split("\\.")[0];
            String rootPath = file.getContainingDirectory().getVirtualFile().getPath()
                    .concat("/").concat(name).concat("_yacc/");

            Path lexFilePath = Paths.get(rootPath, name + ".l");
            Path yaccFilePath = Paths.get(rootPath, name + ".y");
            Path makeFilePath = Paths.get(rootPath, name + ".compile");

            try {
                Files.createDirectories(Paths.get(rootPath));

                if (!Files.exists(lexFilePath))
                    lexFilePath = Files.createFile(lexFilePath);

                if (!Files.exists(yaccFilePath))
                    yaccFilePath = Files.createFile(yaccFilePath);

                if (!Files.exists(makeFilePath))
                    makeFilePath = Files.createFile(makeFilePath);

                Files.write(lexFilePath, translator.getLex().getBytes());
                Files.write(yaccFilePath, translator.getYacc().getBytes());
                Files.write(makeFilePath, "lex ".concat(name).concat(".l\n")
                        .concat("yacc -d ").concat(name).concat(".y\n")
                        .concat("cc lex.yy.c y.tab.c -o ").concat(name).concat("\n").getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.err.println("Cannot translate this");
        }
    }
}

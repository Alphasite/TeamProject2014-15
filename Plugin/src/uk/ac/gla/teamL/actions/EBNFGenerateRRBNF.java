package uk.ac.gla.teamL.actions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.psi.PsiFile;
import org.jetbrains.annotations.NotNull;
import uk.ac.gla.teamL.EBNFFile;
import uk.ac.gla.teamL.translators.RRBNFTranslator;
import uk.ac.gla.teamL.translators.Translator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * User: nishad
 * Date: 12/12/14
 * Time: 15:35
 *
 * Altered by Rosie for (RR)BNF
 *
 */
public class EBNFGenerateRRBNF extends AnAction {
    @Override
    public void actionPerformed(@NotNull AnActionEvent anActionEvent) {
        //Translator translator = new Antlr3Translator();
        Translator translator = new RRBNFTranslator();
        PsiFile file = anActionEvent.getData(LangDataKeys.PSI_FILE);
        if (file instanceof EBNFFile) {
            String translate = translator.translate((EBNFFile) file);

            String name = file.getName().split("\\.")[0];
            Path path = Paths.get(file.getContainingDirectory().getVirtualFile().getPath(), name + ".bnf");
            try {
                if (!Files.exists(path)) {
                    path = Files.createFile(path);
                }

                Files.write(path, translate.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

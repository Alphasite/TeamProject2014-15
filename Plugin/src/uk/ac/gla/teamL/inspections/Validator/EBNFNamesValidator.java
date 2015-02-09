package uk.ac.gla.teamL.inspections.Validator;

import com.intellij.lang.refactoring.NamesValidator;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;

import static uk.ac.gla.teamL.inspections.Annotators.EBNFAnnotationAnnotator.isValidAnnotation;

/**
 * User: nishad
 * Date: 28/01/15
 * Time: 10:58
 */
public class EBNFNamesValidator implements NamesValidator {

    /**
     * Checks if the specified string is a keyword in the custom language.
     *
     * @param name    the string to check.
     * @param project the project in the context of which the check is done.
     * @return true if the string is a keyword, false otherwise.
     */
    @Override
    public boolean isKeyword(@NotNull String name, Project project) {
        if (name.toLowerCase().equals("let")) {
            return true;
        }

        if (isValidAnnotation(name)) {
            return true;
        }

        return false;
    }

    /**
     * Checks if the specified string is a valid identifier in the custom language.
     *
     * @param name    the string to check.
     * @param project the project in the context of which the check is done.
     * @return true if the string is a valid identifier, false otherwise.
     */
    @Override
    public boolean isIdentifier(@NotNull String name, Project project) {
        // I'll need to come back to this eventually, but its good enough for now.
        if (name.length() < 2) {
            return false;
        }

        return true;
    }
}

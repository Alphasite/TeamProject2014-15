package uk.ac.gla.teamL.editor;

/**
 * User: nishad
 * Date: 18/12/14
 * Time: 11:02
 */
public enum Annotations {
    ignored("ignored"),
    literal("literal"),
    regex("regex");

    public final String identifier;

    Annotations(String identifier) {
        this.identifier = identifier;
    }
}

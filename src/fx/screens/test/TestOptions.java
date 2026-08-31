package src.fx.screens.test;

/**
 * The five test options, replacing the {@code isCrossVisible1..5} booleans that
 * {@link src.ui.screens.test.TestSelection} passed to TestView positionally.
 *
 * @param allTranslations    ask for every German translation, not just one
 * @param ignoreOrder        accept translations in any order
 * @param verbStems          ask for the principal parts of verbs
 * @param randomDeclension   ask for a random declension form
 * @param randomConjugation  ask for a random conjugation form
 */
public record TestOptions(
        boolean allTranslations,
        boolean ignoreOrder,
        boolean verbStems,
        boolean randomDeclension,
        boolean randomConjugation) {
}

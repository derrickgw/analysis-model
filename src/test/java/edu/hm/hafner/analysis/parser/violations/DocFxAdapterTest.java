package edu.hm.hafner.analysis.parser.violations;

import edu.hm.hafner.analysis.AbstractParserTest;
import edu.hm.hafner.analysis.Report;
import edu.hm.hafner.analysis.Severity;
import edu.hm.hafner.analysis.assertj.SoftAssertions;

/**
 * Tests the class {@link DocFxAdapter}.
 *
 * @author Ullrich Hafner
 */
class DocFxAdapterTest extends AbstractParserTest {
    DocFxAdapterTest() {
        super("docfx.json");
    }

    @Override
    protected void assertThatIssuesArePresent(final Report report, final SoftAssertions softly) {
        softly.assertThat(report).hasSize(3);
        softly.assertThat(report.get(0))
                .hasMessage("Invalid file link:(~/missing.md#mobiilisovellus).")
                .hasFileName("sanasto.md")
                .hasType("InvalidFileLink")
                .hasLineStart(63)
                .hasSeverity(Severity.WARNING_NORMAL);
        softly.assertThat(report.get(1))
                .hasMessage("Invalid file link:(~/mobiilirajapinta/puuttuu.md).")
                .hasFileName("mobiilirajapinta/json-dateandtime.md")
                .hasType("InvalidFileLink")
                .hasLineStart(18)
                .hasSeverity(Severity.WARNING_NORMAL);
    }

    @Override
    protected DocFxAdapter createParser() {
        return new DocFxAdapter();
    }
}
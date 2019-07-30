package job;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.anyOf;

public class SequenceTest {
    private Sequence sequence;

    @Before
    public void setup() {
        sequence = new Sequence();
    }

    @Test
    public void givenNoJobsTheResultIsAnEmptySequence() {
        sequence = new Sequence();
        String result = sequence.order("");
        assertThat(result, is(""));
    }

    @Test
    public void givenASingleJobTheResultConsistsOfASingleJob() {
        sequence = new Sequence();
        String result = sequence.order("a =>");
        assertThat(result, is("a"));
    }

    @Test
    public void givenIndependentJobsTheResultContainsJobsInNoParticularOrder() {
        sequence = new Sequence();
        String result = sequence.order("a =>\nb =>\nc =>\n");
        assertThat(result, is("abc"));
    }

    @Test
    public void givenDependencyBetweenJobsTheResultContainsDependentJobsAfterIndependentJobs() {
        sequence = new Sequence();
        String result = sequence.order("a =>\nb =>c\nc =>\n");
        assertThat(result, is("acb"));
        result = sequence.order("a =>\nb =>c\nc =>f\nd=>a\ne=>b\nf=>");
        assertThat(result, anyOf(is("adfcbe"), is("fcbead")));
    }
}

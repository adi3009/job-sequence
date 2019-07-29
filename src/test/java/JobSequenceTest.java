import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.anyOf;

public class JobSequenceTest {
    private JobSequence jobSequence;

    @Before
    public void setup() {
        jobSequence = new JobSequence();
    }

    @Test
    public void givenNoJobsTheResultIsAnEmptySequence() {
        jobSequence = new JobSequence();
        String result = jobSequence.order("");
        assertThat(result, is(""));
    }

    @Test
    public void givenASingleJobTheResultConsistsOfASingleJob() {
        jobSequence = new JobSequence();
        String result = jobSequence.order("a =>");
        assertThat(result, is("a"));
    }

    @Test
    public void givenIndependentJobsTheResultContainsJobsInNoParticularOrder() {
        jobSequence = new JobSequence();
        String result = jobSequence.order("a =>\nb =>\nc =>\n");
        assertThat(result, is("abc"));
    }

    @Test
    public void givenDependencyBetweenJobsTheResultContainsDependentJobsAfterIndependentJobs() {
        jobSequence = new JobSequence();
        String result = jobSequence.order("a =>\nb =>c\nc =>\n");
        assertThat(result, is("acb"));
        result = jobSequence.order("a =>\nb =>c\nc =>f\nd=>a\ne=>b\nf=>");
        assertThat(result, anyOf(is("adfcbe"), is("fcbead")));
    }
}

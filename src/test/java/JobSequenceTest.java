import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.is;

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
}

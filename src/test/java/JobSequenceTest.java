import org.junit.Test;
import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.is;

public class JobSequenceTest {
    @Test
    public void givenNoJobsTheResultIsAnEmptySequence() {
        JobSequence jobSequence = new JobSequence();
        String result = jobSequence.order("");
        assertThat(result, is(""));
    }

    @Test
    public void givenASingleJobTheResultConsistsOfASingleJob() {
        JobSequence jobSequence = new JobSequence();
        String result = jobSequence.order("a =>");
        assertThat(result, is("a"));
    }
}

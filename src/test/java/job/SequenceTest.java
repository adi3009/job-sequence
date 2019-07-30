package job;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.CoreMatchers.anyOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class SequenceTest {
    private Sequence sequence;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setup() {
        sequence = new Sequence();
    }

    @Test
    public void givenNoJobsTheResultIsAnEmptySequence() {
        String result = sequence.order("");
        assertThat(result, is(""));
    }

    @Test
    public void givenASingleJobTheResultConsistsOfASingleJob() {
        String result = sequence.order("a =>");
        assertThat(result, is("a"));
    }

    @Test
    public void givenIndependentJobsTheResultContainsJobsInNoParticularOrder() {
        String result = sequence.order("a =>\nb =>\nc =>\n");
        assertThat(result, is("abc"));
    }

    @Test
    public void givenDependencyBetweenJobsTheResultContainsDependentJobsAfterIndependentJobs() {
        String result = sequence.order("a =>\nb =>c\nc =>\n");
        assertThat(result, is("acb"));
        result = sequence.order("a =>\nb =>c\nc =>f\nd=>a\ne=>b\nf=>");
        assertThat(result, anyOf(is("adfcbe"), is("fcbead")));
    }

    @Test
    public void givenAJobDependsOnItselfTheResultShouldBeAnError() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage(Job.CAN_NOT_DEPEND_ON_ITSELF);
        sequence.order("a =>\nb =>\nc =>c");
    }

    @Test
    public void givenJobsHaveCircularDependencyTheResultShouldBeAnError() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage(JobGraph.CAN_NOT_HAVE_CIRCULAR_DEPENDENCIES);
        sequence.order("a =>b\nb =>c\nc =>a");
    }
}

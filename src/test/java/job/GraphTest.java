package job;

import com.google.common.collect.ImmutableList;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class GraphTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void givenAJobListTheGraphPutsDependentJobsLater() {
        List<Job> jobList = ImmutableList.of(
                Job.fromLine("a =>b"),
                Job.fromLine("b => c"),
                Job.fromLine("c =>")
        );

        List<String> expectedOrder = ImmutableList.of("c", "b", "a");
        List<String> actualOrder = new ArrayList<>();
        JobGraph jobGraph = JobGraph.forJobs(jobList.stream());
        jobGraph.traverse(actualOrder::add);
        assertThat(actualOrder, is(expectedOrder));
    }

    @Test
    public void jobGraphCanNotHaveCycles() {
        List<Job> jobList = ImmutableList.of(
                Job.fromLine("a =>b"),
                Job.fromLine("b => c"),
                Job.fromLine("c =>a")
        );

        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage(JobGraph.CAN_NOT_HAVE_CIRCULAR_DEPENDENCIES);
        JobGraph.forJobs(jobList.stream());
    }
}

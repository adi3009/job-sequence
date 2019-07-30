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
                new Job("a =>b"),
                new Job("b => c"),
                new Job("c =>")
        );

        List<String> expectedOrder = ImmutableList.of("c", "b", "a");
        List<String> actualOrder = new ArrayList<>();
        JobGraph jobGraph = new JobGraph(jobList.stream());
        jobGraph.traverse(actualOrder::add);
        assertThat(actualOrder, is(expectedOrder));
    }

    @Test
    public void jobGraphCanNotHaveCycles() {
        List<Job> jobList = ImmutableList.of(
                new Job("a =>b"),
                new Job("b => c"),
                new Job("c =>a")
        );

        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage(JobGraph.CAN_NOT_HAVE_CIRCULAR_DEPENDENCIES);
        new JobGraph(jobList.stream());
    }
}

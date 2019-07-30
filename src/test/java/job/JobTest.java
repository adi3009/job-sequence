package job;

import job.exception.SelfDependencyException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class JobTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void createAJobFromJobLineThatDoesNotDependOnOtherJob() {
        String line = "a => ";
        Job aJob = new Job(line);
        assertThat(aJob.getName(), is("a"));
        assertThat(aJob.isDependent(), is(false));
    }

    @Test
    public void createAJobFromJobLineThatDependsOnOtherJob() {
        String line = "a => b";
        Job aJob = new Job(line);
        assertThat(aJob.getDependsOn(), is("b"));
    }

    @Test
    public void aJobCanNotDependOnItself() {
        thrown.expect(SelfDependencyException.class);
        thrown.expectMessage(Job.CAN_NOT_DEPEND_ON_ITSELF);
        new Job("c=>c");
    }
}

package job;

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
        Job aJob = Job.fromLine("a => ");
        assertThat(aJob.getName(), is("a"));
        assertThat(aJob.isDependent(), is(false));
    }

    @Test
    public void createAJobFromJobLineThatDependsOnOtherJob() {
        Job aJob = Job.fromLine("a => b");
        assertThat(aJob.getDependsOn(), is("b"));
    }

    @Test
    public void aJobCanNotDependOnItself() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage(Job.CAN_NOT_DEPEND_ON_ITSELF);
        Job.fromLine("c=>c");
    }
}

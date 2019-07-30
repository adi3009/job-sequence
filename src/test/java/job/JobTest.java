package job;

import org.junit.Test;

import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.is;

public class JobTest {
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
}

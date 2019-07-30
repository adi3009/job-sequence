package job;

import java.util.Arrays;
import java.util.stream.Stream;

public class Sequence {
    public String order(String jobs) {
        if (jobs.isEmpty())
            return "";

        JobGraph jobGraph = new JobGraph(parseJobs(jobs));
        StringBuilder jobOrder = new StringBuilder();
        jobGraph.traverse(jobOrder::append);

        return jobOrder.toString();
    }

    private Stream<Job> parseJobs(String jobs) {
        return Arrays.stream(jobs.split("\n")).map(Job::new);
    }
}

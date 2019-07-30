package job;

import java.util.Arrays;
import java.util.stream.Stream;

public class Sequence {
    public String order(String jobs) {
        if (jobs.isEmpty())
            return "";

        StringBuilder ordering = new StringBuilder();
        JobGraph.forJobs(parse(jobs))
                .traverse(ordering::append);

        return ordering.toString();
    }

    private Stream<Job> parse(String jobs) {
        return Arrays.stream(jobs.split("\n")).map(Job::fromLine);
    }
}

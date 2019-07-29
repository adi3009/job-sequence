import com.google.common.graph.Graph;
import com.google.common.graph.GraphBuilder;
import com.google.common.graph.MutableGraph;
import com.google.common.graph.Traverser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class JobSequence {
    public String order(String jobs) {
        if (jobs.isEmpty())
            return "";

        Graph<String> jobGraph = generateJobGraph(parseJobs(jobs));
        StringBuilder jobOrder = new StringBuilder();
        Stream<String> independentJobs = jobGraph.nodes().stream().filter(
                n -> jobGraph.successors(n).isEmpty() && jobGraph.predecessors(n).isEmpty()
        );

        independentJobs.forEach(jobOrder::append);

        Stream<String> hasDependents = jobGraph.nodes().stream().filter(
                n -> !jobGraph.successors(n).isEmpty() && jobGraph.predecessors(n).isEmpty()
        );

        Traverser<String> traverser = Traverser.forTree(jobGraph::successors);
        traverser.depthFirstPreOrder(hasDependents.collect(Collectors.toList())).forEach(jobOrder::append);

        return jobOrder.toString();
    }

    private List<String[]> parseJobs(String jobs) {
        String[] jobLines = jobs.split("\n");
        List<String[]> jobRelations = new ArrayList<>();
        Arrays.stream(jobLines).forEach(jobLine -> {
            String[] individualJobs = jobLine.split("=>");
            String dependentJob = individualJobs[0].trim();
            String independentJob = individualJobs.length == 2 ? individualJobs[1].trim() : "";
            jobRelations.add(new String[] {dependentJob, independentJob});
        });

        return jobRelations;
    }

    private Graph<String> generateJobGraph(List<String[]> jobsAndDependencies) {
        MutableGraph<String> graph = GraphBuilder.directed()
                .allowsSelfLoops(false)
                .build();

        jobsAndDependencies.stream().forEach(jobs -> {
            if (jobs[1].isEmpty())
                graph.addNode(jobs[0]);
            else
                graph.putEdge(jobs[1], jobs[0]);
        });

        return graph;
    }
}

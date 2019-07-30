package job;

import com.google.common.graph.GraphBuilder;
import com.google.common.graph.MutableGraph;
import com.google.common.graph.Traverser;

import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class JobGraph {
    private MutableGraph<String> graph;

    public JobGraph(Stream<Job> jobs) {
        graph = GraphBuilder.directed().build();

        jobs.forEach(job -> {
            if (job.isDependent())
                graph.putEdge(job.getDependsOn(), job.getName());
            else
                graph.addNode(job.getName());
        });
    }

    public void traverse(Consumer<String> consumer) {
        Stream<String> independentJobs = graph.nodes().stream().filter(
                n -> graph.successors(n).isEmpty() && graph.predecessors(n).isEmpty()
        );

        independentJobs.forEach(consumer);

        Stream<String> hasDependents = graph.nodes().stream().filter(
                n -> !graph.successors(n).isEmpty() && graph.predecessors(n).isEmpty()
        );

        Traverser<String> traverser = Traverser.forTree(graph::successors);
        traverser.depthFirstPreOrder(hasDependents.collect(Collectors.toList())).forEach(consumer);
    }
}

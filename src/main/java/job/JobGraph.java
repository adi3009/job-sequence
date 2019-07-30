package job;

import com.google.common.graph.GraphBuilder;
import com.google.common.graph.Graphs;
import com.google.common.graph.ImmutableGraph;
import com.google.common.graph.Traverser;

import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class JobGraph {
    private ImmutableGraph<String> graph;

    public final static String CAN_NOT_HAVE_CIRCULAR_DEPENDENCIES = "jobs can’t have circular dependencies.";

    private JobGraph(ImmutableGraph<String> graph) {
        this.graph = graph;
    }

    public static JobGraph forJobs(Stream<Job> jobs) {
        ImmutableGraph.Builder<String> builder = GraphBuilder.directed().immutable();
        jobs.forEach(job -> {
            if (job.isDependent())
                builder.putEdge(job.getDependsOn(), job.getName());
            else
                builder.addNode(job.getName());
        });

        ImmutableGraph<String> graph = builder.build();

        if (Graphs.hasCycle(graph)) {
            throw new IllegalArgumentException(CAN_NOT_HAVE_CIRCULAR_DEPENDENCIES);
        }

        return new JobGraph(graph);
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

# Arrange jobs in dependency order

Given a list of jobs the program arranges jobs in their dependency order.

e.g.

for job list

```
a =>
b => c
c => d
d =>
```

one correct ordering would be

```adcb```

## Approach

Using directed graph data structure, for each job there is a node in a graph and
an edge between nodes for dependency. An edge from some job node ```a``` to some 
job node ```b``` in the graph indicates that job ```a``` must come before job ```b``` 
in ordered job sequence.

For above job list, the graph would be 

```a```

```d``` -> ```c``` -> ```b```

### Steps

- Parse job string into ```Job``` class instances, will error if a job depends on itself.
- Create a graph from a stream of ```Job``` instances, 
    - if a ```Job``` has no dependency then create a node in the graph
    - else create an edge
    - Error if the graph has cycles.
- Traverse the graph
    - first list all independent nodes, nodes with 0 in-degree and out-degree
    - traverse nodes with 1 or more out-degree in depth first pre order
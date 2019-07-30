package job;

import job.exception.SelfDependencyException;

class Job {
    private String name;

    private String dependsOn;

    Job(String jobLine) {
        String[] names = jobLine.split("=>");
        name = names[0].trim();
        dependsOn = names.length == 2 ? names[1].trim() : "";
        if (dependsOn.equals(name)) {
            throw new SelfDependencyException();
        }
    }

    boolean isDependent() {
        return !dependsOn.isEmpty();
    }

    public String getName() {
        return name;
    }

    public String getDependsOn() {
        return dependsOn;
    }
}

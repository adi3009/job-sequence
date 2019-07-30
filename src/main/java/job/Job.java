package job;

import job.exception.SelfDependencyException;

class Job {
    private String name;

    private String dependsOn;

    final static String CAN_NOT_DEPEND_ON_ITSELF = "jobs can’t depend on themselves.";

    Job(String jobLine) {
        String[] names = jobLine.split("=>");
        name = names[0].trim();
        dependsOn = names.length == 2 ? names[1].trim() : "";
        if (dependsOn.equals(name)) {
            throw new SelfDependencyException(CAN_NOT_DEPEND_ON_ITSELF);
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

package job;

class Job {
    private String name;

    private String dependsOn;

    final static String CAN_NOT_DEPEND_ON_ITSELF = "jobs can’t depend on themselves.";

    private Job(String name, String dependsOn) {
        this.name = name;
        this.dependsOn = dependsOn;
    }

    public static Job fromLine(String line) {
        String[] names = line.split("=>");
        String name = names[0].trim();
        String dependsOn = names.length == 2 ? names[1].trim() : "";
        if (dependsOn.equals(name)) {
            throw new IllegalArgumentException(CAN_NOT_DEPEND_ON_ITSELF);
        }

        return new Job(name, dependsOn);
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

public class JobSequence {
    public String order(String jobs) {
        if (jobs.isEmpty())
            return "";

        return jobs.substring(0, 1);
    }
}

public class JobSequence {
    public String order(String jobs) {
        if (jobs.isEmpty())
            return "";

        return jobs.replace(" =>", "").replace("\n", "");
    }
}

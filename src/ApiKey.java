import java.io.IOException;
import java.util.*;

public class ApiKey implements Frequency{
    private String[] apiArray;
    private Map<String, Integer> apiFrequencyHashMap;
    @Override
    public StringBuilder findMaxFrequency(ArrayList<Log> logArrayList) throws IOException {
        StringBuilder maxFrequencyApiKey = new StringBuilder();
        maxFrequencyApiKey.append("최다 호출 API KEY\n");

        apiArray = Split.splitApiKey(logArrayList); // split
        apiFrequencyHashMap = Frequency.findFrequency(apiArray); // find frequency
        List<String> apiKeyFrequencyKeySet = Frequency.sortHashKeySet(apiFrequencyHashMap); //sort hash by value
        maxFrequencyApiKey.append(apiKeyFrequencyKeySet.get(0)).append("\n");
        return maxFrequencyApiKey;
    }
}

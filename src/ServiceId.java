import java.util.*;

public class ServiceId implements Frequency{
    Map<String, Integer> serviceIdFrequencyHashMap;
    String[] serviceIdArray;

    public StringBuilder findMaxFrequency(ArrayList<Log> logArrayList){
        StringBuilder maxFrequencyServiceId = new StringBuilder();
        maxFrequencyServiceId.append("상위 3개의 API Service ID와 각각의 요청수\n");

        serviceIdArray = Split.splitServiceId(logArrayList); // split
        serviceIdFrequencyHashMap = Frequency.findFrequency(serviceIdArray); //find frequency
        List<String> serviceIdFrequencyKeySet = Frequency.sortHashKeySet(serviceIdFrequencyHashMap); //sort hash by value

        for (int indexForKey = 0; indexForKey < 3; indexForKey++) {
            maxFrequencyServiceId.append(serviceIdFrequencyKeySet.get(indexForKey))
                    .append(": ")
                    .append(serviceIdFrequencyHashMap.get(serviceIdFrequencyKeySet.get(indexForKey)))
                    .append("\n");
        }
        return maxFrequencyServiceId;
    }
}

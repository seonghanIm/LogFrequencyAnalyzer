import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Browser implements Frequency {
    private Map<String, Integer> browserFrequencyHashMap;
    private String[] browserStringArray;

    public StringBuilder findMaxFrequency(ArrayList<Log> logArrayList) throws IOException {
        StringBuilder maxFrequencyBrowser = new StringBuilder();
        maxFrequencyBrowser.append("웹 브라우저별 사용 비율\n");

        browserStringArray = Split.makeBrowserArray(logArrayList); // split
        browserFrequencyHashMap = Frequency.findFrequency(browserStringArray); //find frequency
        List<String> browserFrequencyKeySet = Frequency.sortHashKeySet(browserFrequencyHashMap);//sort hash by value
        try{
            for (String key : browserFrequencyKeySet) {
                int ratio = browserFrequencyHashMap.get(key) * 100 / logArrayList.size();
                maxFrequencyBrowser.append(key).append(": ").append(ratio).append("%\n");
            }
        }catch(ArithmeticException e){
            StringBuilder errorStringBuilder = new StringBuilder();
            errorStringBuilder.append(e);
            FileIO.writeFile(errorStringBuilder);
        }

        return maxFrequencyBrowser;
    }
}

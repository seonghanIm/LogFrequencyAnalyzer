import jdk.nashorn.internal.runtime.arrays.ArrayIndex;

import java.io.IOException;
import java.util.*;

public interface Frequency {
    public StringBuilder findMaxFrequency(ArrayList<Log> logArrayList) throws IOException;

    static Map findFrequency(String[] targetArray) { // 빈도수 체크
        Map<String, Integer> hashMap = new HashMap<>();
        try {
            Arrays.stream(targetArray).forEach(target -> {
                if (hashMap.containsKey(target)) hashMap.put(target, hashMap.get(target) + 1);
                 else hashMap.put(target, 1);
            });
        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
        }
        return hashMap;
    }

    static List<String> sortHashKeySet(Map<String, Integer> hashMap) { // KeySet에 hashMap Key 저장 후 hashMap value 기준으로 정렬
        List<String> keySet = new ArrayList<>(hashMap.keySet());
        keySet.sort((o1, o2) -> hashMap.get(o2).compareTo(hashMap.get(o1)));
        return keySet;
    }
}

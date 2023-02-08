import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

public class Split {
    public static ArrayList<Log> splitString(BufferedReader br) throws IOException { // input.log를 한줄 씩 읽어 Log 객체에 추가
        ArrayList<Log> logArrayList = new ArrayList<>(); //
        String inputLogString;
        String inputLogStringArray[];
        while ((inputLogString = br.readLine()) != null) { // 전달된 버퍼를 한줄 씩 읽음
            inputLogString = inputLogString.replaceAll("\\[", ""); // '[' 삭제
            inputLogStringArray = inputLogString.split("]"); //']' 기준으로 문자열 자름
            Log log = Log.builder() // builder를 통해 자른 문자열을 log 객체에 추가
                    .statusCode(inputLogStringArray[0])
                    .url(inputLogStringArray[1])
                    .webBrowser(inputLogStringArray[2])
                    .callTime(inputLogStringArray[3])
                    .build();
            logArrayList.add(log); // 채워진 log를 logArrayList에 저장
        }
        return logArrayList;
    }


    public static String[] splitApiKey(ArrayList<Log> logArrayList){ // url에서 apiKey 추출
        StringBuilder apiKeyStringBuilder = new StringBuilder();
        logArrayList.stream().forEach(log -> {
            if (log.checkStatusCode()) {
                String url = log.getUrl();
                int index = url.indexOf("=");
                int index2 = url.indexOf("&");
                if(index==-1&&index2==-1){
                    StringBuilder errorBuilder = new StringBuilder();
                    errorBuilder.append("상태코드가 200이지만 올바르지 않은 형식의 URL이 있습니다.\n");
                    try {
                        FileIO.writeFile(errorBuilder);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    System.exit(-1);
                }
                String apiKey = url.substring(index + 1, index2);
                apiKeyStringBuilder.append(apiKey).append(" ");
            }
        });
        return apiKeyStringBuilder.toString().split(" ");
    }

    public static String[] splitServiceId(ArrayList<Log> logArrayList) { // Url에서 service ID 추출
        StringBuilder serviceIdStringBuilder = new StringBuilder();
        logArrayList.stream().forEach(log -> {
            if (log.checkStatusCode()) {
                String url = log.getUrl();
                int index = url.indexOf("?");
                if(index==-1){
                    StringBuilder errorBuilder = new StringBuilder();
                    errorBuilder.append("상태코드가 200이지만 올바르지 않은 형식의 URL이 있습니다.\n");
                    try {
                        FileIO.writeFile(errorBuilder);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    System.exit(-1);
                }
                String serviceId = url.substring(28, index);
                serviceIdStringBuilder.append(serviceId).append(" ");
            }
        });

        return serviceIdStringBuilder.toString().split(" ");
    }

    public static String[] makeBrowserArray(ArrayList<Log> logArrayList) {  //Log 객체의 browser을 Strig Array 타입으로 전환 -> 코드 재사용성을 높이기 위해
        StringBuilder browserStringBuilder = new StringBuilder();
        logArrayList.stream().forEach(log -> {
            if (log.checkStatusCode()) {
                browserStringBuilder.append(log.getWebBrowser())
                        .append(" ");
            }
        });
        return browserStringBuilder.toString().split(" ");
    }
}

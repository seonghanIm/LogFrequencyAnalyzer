import java.io.*;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws IOException { // 프로그램의 시작점
        ArrayList<Log> logArrayList = FileIO.readFile(); // 파일 읽기
        if(logArrayList==null){ // Exception 체크
            return;
        }
        StringBuilder output = new StringBuilder();


        output.append(new ApiKey().findMaxFrequency(logArrayList)) // apikey, serviceId, browser의 각 메소드를 실행 후 output StringBuilder에 추가
                .append("\n")
                .append(new ServiceId().findMaxFrequency(logArrayList))
                .append("\n")
                .append(new Browser().findMaxFrequency(logArrayList));
        FileIO.writeFile(output); // 완성된 output 파일을 writeFile 을 통해 output.log 완성
    }
}



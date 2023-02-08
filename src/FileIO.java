import java.io.*;
import java.util.ArrayList;

public class FileIO {

    public static ArrayList<Log> readFile() throws IOException {
        ArrayList<Log> logArrayList = null; // 초기화 시점에 null 을 넣어 줌으로써 예외 발생시 null을 return 하도록 함
        try{
            File file = new File("src/Input/input.log");
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            logArrayList = Split.splitString(bufferedReader); // Split 클래스의 splitString 메소드에 input 버퍼 전달
            bufferedReader.close();
            return logArrayList;
        }catch (FileNotFoundException e){
            StringBuilder errorStringBuilder = new StringBuilder();
            errorStringBuilder.append("파일이 존재 하지 않습니다.\n").append(e);
            FileIO.writeFile(errorStringBuilder);
            return logArrayList;
        }catch(IOException e){
            e.printStackTrace();
            return logArrayList;
        }
    }

    public static void writeFile(StringBuilder logResult) throws IOException{ // 파일 출력
        PrintWriter pw = new PrintWriter("src/output/output.log");
        pw.println(logResult);
        pw.close();
    }
}

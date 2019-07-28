import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baidu.translate.demo.TransApi;

import java.io.*;

public class Main2 {
    public static void main(String[] args) throws IOException {
        File fileOld = new File("F:\\java\\java\\src\\3.txt");
        File fileNew = new File("F:\\java\\java\\src\\5.txt");
        readFileByLines(fileOld, fileNew);
    }

    public static void readFileByLines(File fileOld, File fileNew) {
        BufferedReader reader = null;
        String reg = "^\\d+$";
        try {
            System.out.println("按行读取字幕内容：");
            reader = new BufferedReader(new FileReader(fileOld));
            String tempString = null;
            String catString = "";
            int flag = 0;
            int line = 1;
            // 一次读入一行，直到读入null为文件结束
            while ((tempString = reader.readLine()) != null) {
                if (!(tempString.contains("DL") || tempString.contains("UE_ID") || tempString.contains("PRACH")|| tempString.contains("idle"))&&tempString.contains("M")) {
                    System.out.println("before translate: line " + line + ": " + tempString);
                    String newtempString=tempString.replace("M","");
                    appendFile(fileNew, newtempString + "\n");
                    line++;
                }

            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
    }

    public static void readFile(File file) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
        String line = null;
        while ((line = br.readLine()) != null) {
            System.out.println(line);
        }
    }

    public static void writeFile(File file, String content) throws IOException {
        FileOutputStream fos = new FileOutputStream(file);
        OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8");
        osw.write(content);
        osw.flush();
    }

    public static void appendFile(File file, String content) throws IOException {
        OutputStreamWriter out = new OutputStreamWriter(
                new FileOutputStream(file, true), // true to append
                "UTF-8"
        );
        out.write(content);
        out.close();
    }
}

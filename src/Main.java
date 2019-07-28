import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baidu.translate.demo.TransApi;


import java.io.*;

public class Main {

    // 在平台申请的APP_ID 详见 http://api.fanyi.baidu.com/api/trans/product/desktop?req=developer
    private static final String APP_ID = "20151130000006995";
    private static final String SECURITY_KEY = "OVHRSQUL3M2s3UeEcLyD";

    public static void main(String[] args)throws IOException {
        TransApi api = new TransApi(APP_ID, SECURITY_KEY);
       // readFileByLines("F:\\java\\java\\src\\1.srt");
       // String query = "it's got many faces, I look forward to seeing this one.";
       // String string=api.getTransResult(query, "auto", "zh");
       // System.out.println(string);
       // JSONObject jsonObject=JSON.parseObject(string);
       // JSONArray jsonArray=JSON.parseArray(jsonObject.get("trans_result").toString());
       // String result=JSON.parseObject(jsonArray.get(0).toString()).get("dst").toString();
       // System.out.println(result);
        File fileOld = new File("F:\\java\\java\\src\\en.srt");
        File fileNew = new File("F:\\java\\java\\src\\zh.srt");
        readFileByLines(fileOld,fileNew);
    }
    public static void readFileByLines(File fileOld,File fileNew) {
        TransApi api = new TransApi(APP_ID, SECURITY_KEY);
        BufferedReader reader = null;
        String reg = "^\\d+$";
        try {
            System.out.println("按行读取字幕内容：");
            reader = new BufferedReader(new FileReader(fileOld));
            String tempString = null;
            String catString="";
            int flag=0;
            int line = 1;
            // 一次读入一行，直到读入null为文件结束
            while ((tempString = reader.readLine()) != null) {
                if(!(tempString.contains("-->")||tempString.matches(reg)||tempString.length()==0)){
                    System.out.println("before translate: line " + line + ": " + tempString);
                    String string=api.getTransResult(tempString, "auto", "zh");
                    JSONObject jsonObject=JSON.parseObject(string);
                    JSONArray jsonArray=JSON.parseArray(jsonObject.get("trans_result").toString());
                    tempString=JSON.parseObject(jsonArray.get(0).toString()).get("dst").toString();
                    System.out.println("after translate : line " + line + ": " + tempString);
                }
                appendFile(fileNew,tempString+"\n");
                line++;
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

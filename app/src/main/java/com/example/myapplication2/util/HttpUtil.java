package com.example.myapplication2.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;
import java.util.Set;

public class HttpUtil {

    public static String sendPostMultiFormData(String apiUrl,Map<String,String> data, String loginKey, String filePath) throws Exception {

        BufferedReader br = null;
        String result = "" ;
        String boundary = "^-----^";
        String charset = "UTF-8";
        String LINE_FEED = "\r\n";
        OutputStream outputStream;
        PrintWriter writer;

        try {
            URL url = new URL(apiUrl);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);

            conn.setRequestMethod("POST"); // 보내는 타입
            conn.setRequestProperty("Content-Type", "multipart/form-data;charset=utf-8;boundary="+boundary);
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestProperty("loginKey", loginKey);

            outputStream = conn.getOutputStream();
            writer = new PrintWriter(new OutputStreamWriter(outputStream, charset), true);

            /** Body에 데이터를 넣어줘야 할경우 없으면 Pass **/
            Set<String> dataKey = data.keySet();
            for(String key:dataKey) {
                writer.append("--" + boundary).append(LINE_FEED);
                writer.append("Content-Disposition: form-data; name=\""+key+"\"").append(LINE_FEED);
                writer.append("Content-Type: text/plain; charset=" + charset).append(LINE_FEED);
                writer.append(LINE_FEED);
                writer.append(data.get(key)).append(LINE_FEED);
            }
            writer.flush();


            /** 파일 데이터를 넣는 부분**/
            File file = new File(filePath);
            if(file.exists()) {
                writer.append("--" + boundary).append(LINE_FEED);
                writer.append("Content-Disposition: form-data; name=\"file\"; filename=\"" + file.getName() + "\"").append(LINE_FEED);
                writer.append("Content-Type: " + URLConnection.guessContentTypeFromName(file.getName())).append(LINE_FEED);
                writer.append("Content-Transfer-Encoding: binary").append(LINE_FEED);
                writer.append(LINE_FEED);
                writer.flush();

                FileInputStream inputStream = new FileInputStream(file);
                byte[] buffer = new byte[(int)file.length()];
                int bytesRead = -1;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
                outputStream.flush();
                inputStream.close();
                writer.append(LINE_FEED);
                writer.flush();
            }

            writer.append("--" + boundary + "--").append(LINE_FEED);
            writer.close();

            int statusCode = conn.getResponseCode() ;

            // Return Data 수신
            br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String line;
            while((line = br.readLine()) != null) {
                result = result + line.trim();
            }
        }catch(Exception e) {
            throw e ;
        }finally {
            if(br != null) {
                br.close();
            }
        }

        return result ;
    }

    public static String sendPostData(String apiUrl,String data) throws Exception {

        BufferedReader br = null;
        String result = "" ;

        try {
            URL url = new URL(apiUrl);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setDoInput(true);


            conn.setRequestMethod("POST"); // 보내는 타입
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("Accept", "application/json");

            byte[] bytePostData	= data.getBytes(); // encoding
            int postDataLength	= bytePostData.length;
            conn.setRequestProperty("Content-Length", Integer.toString(postDataLength));
            conn.setUseCaches(false);
            conn.getOutputStream().write(bytePostData);

            OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());

            // Data setting
            osw.write(data);

            // Data 전송
//			osw.flush();

            int statusCode = conn.getResponseCode() ;

            // Return Data 수신
            br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String line;
            while((line = br.readLine()) != null) {
                result = result + line.trim();
            }
        }catch(Exception e) {
            throw e ;
        }finally {
            if(br != null) {
                br.close();
            }
        }

        return result ;
    }

    public static String sendGetData(String apiUrl,String data) throws Exception {

        BufferedReader br = null;
        String result = "" ;

        try {
            URL url = new URL(apiUrl);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setDoInput(true);


            conn.setRequestMethod("GET"); // 보내는 타입
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("Accept", "application/json");

            byte[] bytePostData	= data.getBytes(); // encoding
            int postDataLength	= bytePostData.length;
            conn.setRequestProperty("Content-Length", Integer.toString(postDataLength));
            conn.setUseCaches(false);
            conn.getOutputStream().write(bytePostData);

            OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());

            // Data setting
            osw.write(data);

            // Data 전송
//			osw.flush();

            int statusCode = conn.getResponseCode() ;

            // Return Data 수신
            br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String line;
            while((line = br.readLine()) != null) {
                result = result + line.trim();
            }
        }catch(Exception e) {
            throw e ;
        }finally {
            if(br != null) {
                br.close();
            }
        }

        return result ;
    }
}

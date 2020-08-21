package com.japl.Utils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;

public class Japl {
    /*
    随机数40-47   (int)(Math.random()*(47-40)+40)
    System.out.println("\033[41;90;1m" + "你好世界" + "\033[0m");
    背景颜色;字体颜色;字体样式
    背景颜色40-49(顺序同下)

    "黑色", "90"
    "红色", "91"
    "绿色", "92"
    "黄色", "93"
    "蓝色", "94"
    "紫红色", "95"
    "青蓝色", "96"
    "白色", "97"
*/
    protected static final String ffmpegPath="A:/ffmpeg/bin/ffmpeg.exe ";
    public static boolean Download(String Url,String path){
        try {
            URL url=new URL(Url);
            HttpURLConnection connection=(HttpURLConnection)url.openConnection();
            connection.setRequestMethod("GET");
            connection.addRequestProperty("Connection", "keep-alive");
            connection.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/83.0.4103.116 Safari/537.36");
            connection.addRequestProperty("Accept", "*/*");
            connection.addRequestProperty("Origin","https://www.bilibili.com");
            connection.addRequestProperty("Sec-Fetch-Site", "cross-site");
            connection.addRequestProperty("Sec-Fetch-Mode", "cors");
            connection.addRequestProperty("Sec-Fetch-Dest", "empty");
            connection.addRequestProperty("Referer", "https://www.bilibili.com");
            connection.addRequestProperty("Accept-Encoding", "identity");
            connection.addRequestProperty("Accept-Language", "zh-CN,zh;q=0.9,ja;q=0.8");
            connection.connect();

            InputStream is = connection.getInputStream();
            FileOutputStream out =new FileOutputStream(path);

            long len = connection.getContentLengthLong();

            int lenght;
            byte[] buffer =new byte[1024];
            int x=0;
            while((lenght=is.read(buffer))!=-1) {
                x+=lenght;
                out.write(buffer,0,lenght);
                out.flush();
                System.out.println("总大小："+len+" 已下载："+x);
            }
            is.close();
            out.close();
            return true;
        }catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
    public static String POST(String url,String string,String Cookies) {
        try {
            URL url2=new URL(url);
            HttpURLConnection connection=(HttpURLConnection)url2.openConnection();
            connection.setRequestMethod("POST");
            connection.addRequestProperty("Connection", "keep-alive");
            connection.addRequestProperty("Pragma", "no-cache");
            connection.addRequestProperty("content-type", "application/x-www-form-urlencoded");
            connection.addRequestProperty("Accept-Language", "zh-CN,en-US;q=0.8");
            connection.addRequestProperty("Sec-Fetch-Dest","empty");
            connection.addRequestProperty("Sec-Fetch-Mode","cors");
            connection.addRequestProperty("Sec-Fetch-Site","same-site");
            connection.addRequestProperty("Cookie", Cookies);

            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.connect();

            byte[] data=string.getBytes();
            connection.getOutputStream().write(data);
            int code=connection.getResponseCode();
            if(code==200){
                InputStream is = connection.getInputStream();
                ByteArrayOutputStream message =new ByteArrayOutputStream();
                int lenght;
                byte[] buffer =new byte[1024];
                while((lenght=is.read(buffer))!=-1) {
                    message.write(buffer,0,lenght);
                }
                is.close();
                message.close();
                return new String(message.toByteArray(), StandardCharsets.UTF_8);
            }else{
                System.out.println(code);
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
        return "null";
    }
    public static String Regex(String regex,String str) {
        String string="";
        Matcher mat= Pattern.compile(regex).matcher(str);
        while(mat.find()) {
            string=mat.group(1);
        }
        return string;
    }
    public static String GET(String Url, String cookies){
        try {
            URL url=new URL(Url);
            HttpURLConnection connection=(HttpURLConnection)url.openConnection();
            connection.setRequestMethod("GET");
            connection.addRequestProperty("Cookie", cookies);

            connection.addRequestProperty("Connection", "keep-alive");
            connection.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/83.0.4103.116 Safari/537.36");
            connection.addRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
            connection.addRequestProperty("Accept-Language", "zh-CN,zh;q=0.8,zh-TW;q=0.7,zh-HK;q=0.5,en-US;q=0.3,en;q=0.2");
            connection.addRequestProperty("Upgrade-Insecure-Requests", "1");
            connection.addRequestProperty("Cache-Control", "max-age=0");

            connection.connect();

            InputStream is = connection.getInputStream();

            String encoding = connection.getContentEncoding();
            if(encoding!=null){
                //判断是否gzip
                if(encoding.equals("gzip")){
                    is=new GZIPInputStream(is);
                }
            }
            ByteArrayOutputStream message =new ByteArrayOutputStream();
            int lenght;
            byte[] buffer =new byte[1024];
            while((lenght=is.read(buffer))!=-1) {
                message.write(buffer,0,lenght);
            }
            is.close();
            message.close();
            return new String(message.toByteArray(), StandardCharsets.UTF_8);

        }catch (IOException e) {
            e.printStackTrace();
        }
        return "null";
    }
    public static void BILIBILI(){
        /*String encoded = Base64.getEncoder().encodeToString("".getBytes());
        System.out.println(encoded);*/

        String str="ICAgICAgICAgICAgICAgICAgICAgLy8KICAgICAgICAgXFwgICAgICAgICAvLwogICAgICAgICAgXFwgICAgICAgLy8KICAgICMjRERERERERERERERERERERERERERERCMjCiAgICAjIyBERERERERERERERERERERERERERCAjIyAgIF9fX19fX19fICAgX19fICAgX19fICAgICAgICBfX18gICBfX19fX19fXyAgIF9fXyAgIF9fXyAgICAgICAgX19fCiAgICAjIyBoaCAgICAgICAgICAgICAgICBoaCAjIyAgIHxcICAgX18gIFwgfFwgIFwgfFwgIFwgICAgICB8XCAgXCB8XCAgIF9fICBcIHxcICBcIHxcICBcICAgICAgfFwgIFwKICAgICMjIGhoICAgIC8vICAgIFxcICAgIGhoICMjICAgXCBcICBcfFwgL19cIFwgIFxcIFwgIFwgICAgIFwgXCAgXFwgXCAgXHxcIC9fXCBcICBcXCBcICBcICAgICBcIFwgIFwKICAgICMjIGhoICAgLy8gICAgICBcXCAgIGhoICMjICAgIFwgXCAgIF9fICBcXCBcICBcXCBcICBcICAgICBcIFwgIFxcIFwgICBfXyAgXFwgXCAgXFwgXCAgXCAgICAgXCBcICBcCiAgICAjIyBoaCAgICAgICAgICAgICAgICBoaCAjIyAgICAgXCBcICBcfFwgIFxcIFwgIFxcIFwgIFxfX19fIFwgXCAgXFwgXCAgXHxcICBcXCBcICBcXCBcICBcX19fXyBcIFwgIFwKICAgICMjIGhoICAgICAgd3d3dyAgICAgIGhoICMjICAgICAgXCBcX19fX19fX1xcIFxfX1xcIFxfX19fX19fXFwgXF9fXFwgXF9fX19fX19cXCBcX19cXCBcX19fX19fX1xcIFxfX1wKICAgICMjIGhoICAgICAgICAgICAgICAgIGhoICMjICAgICAgIFx8X19fX19fX3wgXHxfX3wgXHxfX19fX19ffCBcfF9ffCBcfF9fX19fX198IFx8X198IFx8X19fX19fX3wgXHxfX3wKICAgICMjIE1NTU1NTU1NTU1NTU1NTU1NTU1NICMjCiAgICAjI01NTU1NTU1NTU1NTU1NTU1NTU1NTU0jIwogICAgICAgICBcLyAgICAgICAgICAgIFwv";
        byte[] decoded = Base64.getDecoder().decode(str);
        //System.out.println("\033[41;97;1m" + new String(decoded) + "\n\033[0m");
        System.out.println(new String(decoded));
    }
    public static int Random(int s,int d){
        return (int)(Math.random()*(d-s)+s);
    }
    public static void Print(Object... obj){
        //转换为字符串
        String str=obj[0].toString();

        if(obj.length>1){
            int b=(int)obj[1];
            if(b==1){
                //非主流杀马特爆闪RGB
                for (int i=0;i<str.length();i++) {
                    System.out.print("\033["+Random(90,97)+";1m" + str.charAt(i) + "\033[0m");
                }
            }else if(b==2){
                int len=2;
                if(obj.length==3){
                    len=(int)obj[2];
                }
                //彩虹颜色
                int[] colors=new int[]{91,93,92,96,94,95,97};
                int x=0;
                int s=0;
                for (int i=0;i<str.length();i++) {
                    System.out.print("\033["+colors[x]+";1m" + str.charAt(i) + "\033[0m");
                    s++;
                    if(s>=len){
                        x++;
                        s=0;
                    }
                    if (x>6){
                        x=0;
                    }
                }
            }else if(b==3){
                //自选颜色
                int[] colors=new int[]{91,93,92,96,94,95,97,90};
                int i=0;
                if(obj.length==3){
                    int x = (int) obj[2];
                    if(x<colors.length){
                        i=x;
                    }
                }
                System.out.print("\033["+colors[i]+";1m" + str + "\033[0m");
            }else if(b==4){
                int[] colors1=new int[]{40,41,42,43,44,45,46,47};
                int[] colors2=new int[]{90,91,92,93,94,95,96,97};

                int bcolor=1;
                int tcolor=7;

                if(obj.length>=3){
                    int x = (int) obj[2];
                    if(x<colors1.length){
                        bcolor=x;
                    }
                }
                if(obj.length==4){
                    int x = (int) obj[3];
                    if(x<colors2.length){
                        tcolor=x;
                    }
                }
                System.out.print("\033["+colors1[bcolor]+";"+colors2[tcolor]+";1m" + str + "\033[0m");
            }else{
                //默认 随机一种颜色
                System.out.print("\033["+Random(90,97)+";1m" + str + "\033[0m");
            }
        }else{
            //默认 随机一种颜色
            System.out.print("\033["+Random(90,97)+";1m" + str + "\033[0m");
        }
    }
    public static void Println(Object... obj){
        //转换为字符串
        String str=obj[0].toString();

        if(obj.length>1){
            int b=(int)obj[1];
            if(b==1){
                //非主流杀马特爆闪RGB
                for (int i=0;i<str.length();i++) {
                    System.out.print("\033["+Random(90,97)+";1m" + str.charAt(i) + "\033[0m");
                }
                System.out.println();
            }else if(b==2){
                int len=2;
                if(obj.length==3){
                    len=(int)obj[2];
                }
                //彩虹颜色
                int[] colors=new int[]{91,93,92,96,94,95,97};
                int x=0;
                int s=0;
                for (int i=0;i<str.length();i++) {
                    System.out.print("\033["+colors[x]+";1m" + str.charAt(i) + "\033[0m");
                    s++;
                    if(s>=len){
                        x++;
                        s=0;
                    }
                    if (x>6){
                        x=0;
                    }
                }
                System.out.println();
            }else if(b==3){
                //自选颜色
                int[] colors=new int[]{91,93,92,96,94,95,97,90};
                int i=0;
                if(obj.length==3){
                    int x = (int) obj[2];
                    if(x<colors.length){
                        i=x;
                    }
                }
                System.out.println("\033["+colors[i]+";1m" + str + "\033[0m");
            }else if(b==4){
                int[] colors1=new int[]{40,41,42,43,44,45,46,47};
                int[] colors2=new int[]{90,91,92,93,94,95,96,97};

                int bcolor=1;
                int tcolor=7;

                if(obj.length>=3){
                    int x = (int) obj[2];
                    if(x<colors1.length){
                        bcolor=x;
                    }
                }
                if(obj.length==4){
                    int x = (int) obj[3];
                    if(x<colors2.length){
                        tcolor=x;
                    }
                }
                System.out.println("\033["+colors1[bcolor]+";"+colors2[tcolor]+";1m" + str + "\n\033[0m");
            }else{
                //默认 随机一种颜色
                System.out.println("\033["+Random(90,97)+";1m" + str + "\033[0m");
            }
        }else{
            //默认 随机一种颜色
            System.out.println("\033["+Random(90,97)+";1m" + str + "\033[0m");
        }
    }
    //视频合并
    public static String Video_Audio(String videoInputPath, String audioInputPath, String videoOutPath){
        // ffmpeg命令
        String command = "-i " + videoInputPath + " -i " + audioInputPath
                + " -c:v copy -c:a aac -strict experimental " +
                " -map 0:v:0 -map 1:a:0 "
                + " -y " + videoOutPath;
        return command;
    }
    public static void Convetor(String command) throws Exception {
        Process process;
        InputStream errorStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader br = null;
        try {
            process = Runtime.getRuntime().exec(ffmpegPath+command);
            errorStream = process.getErrorStream();
            inputStreamReader = new InputStreamReader(errorStream, StandardCharsets.UTF_8);
            br = new BufferedReader(inputStreamReader);
            // 用来收集错误信息的
            String str;
            while ((str = br.readLine()) != null) {
                System.out.println(str);
            }
            process.waitFor();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                br.close();
            }
            if (inputStreamReader != null) {
                inputStreamReader.close();
            }
            if (errorStream != null) {
                errorStream.close();
            }
        }
    }
}

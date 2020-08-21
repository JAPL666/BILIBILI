package com.japl;

import com.japl.Utils.Japl;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.File;
import java.util.*;

public class Main {

    static ArrayList<String> VideoNames=new ArrayList<>();
    static ArrayList<String> VideoUrl=new ArrayList<>();
    static ArrayList<String> AudioUrl=new ArrayList<>();

    public static void main(String[] args) {
        Japl.BILIBILI();
        System.out.println("\n请输入视频BV号！多个BV号用;隔开！");

        while (true){
            Scanner sc=new Scanner(System.in);
            String bv=sc.next();

            if(bv.contains(";")){
                String[] bvs=bv.split(";");
                for (String b:bvs){
                    if(b.contains("BV")){
                        getCid(b);
                        Download();
                        clear();
                    }
                }
            }else{
                if(bv.contains("BV")){
                    getCid(bv);
                    Download();
                    clear();
                }
            }
            System.out.println("全部视频下载完成！");
        }
    }
    public static void clear(){
        VideoNames.clear();
        VideoUrl.clear();
        AudioUrl.clear();
    }
    public static void Download(){
        for (int i=0;i<VideoNames.size();i++){
            String name=VideoNames.get(i);
            try {
                System.out.println("开始下载："+name);

                String path="B站视频/";
                File dir = new File(path);
                if(!dir.exists()){
                    //不存在就创建
                    dir.mkdirs();
                }

                String path1=path+name+"1.m4s";
                String path2=path+name+"2.m4s";
                String mp4=path+name+".mp4";


                Japl.Download(VideoUrl.get(i),path1);
                Japl.Download(AudioUrl.get(i),path2);

                System.out.println(name+"下载完成！");
                System.out.println("开始合并视频："+name);
                String dm=Japl.Video_Audio(path1,path2,path+name+".mp4");
                Japl.Convetor(dm);
                System.out.println(name+"合并完成！");

                File file=new File(mp4);
                if(file.exists()){
                    if (file.length()>3){
                        new File(path1).delete();
                        new File(path2).delete();
                        System.out.println("缓存删除完成！");
                        System.out.println("完成！文件路径："+mp4);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    //通过BV号获取cid
    public static void getCid(String BV){
        String cid_url="https://api.bilibili.com/x/player/pagelist?bvid="+BV+"&jsonp=jsonp";
        String res = Japl.GET(cid_url, "");
        JSONObject obj = new JSONObject(res);
        int x=1;
        for (Object json :new JSONArray(obj.get("data").toString())){
            JSONObject jsonObject = new JSONObject(json.toString());
            String name=BV+"_"+x;
            getUrl(jsonObject.get("cid").toString(),BV,name);
            x++;
        }
    }
    //获取视频真实地址
    public static void getUrl(String cid,String BV,String VideoName){
        String url="https://api.bilibili.com/x/player/playurl?cid="+cid+"&qn=80&fnver=0&fnval=16&type=&otype=json&bvid="+BV;
        String res = Japl.GET(url, "");

        try {
            JSONObject data = new JSONObject(new JSONObject(res).get("data").toString());
            JSONObject dash = new JSONObject(data.get("dash").toString());

            String  video= new JSONObject(new JSONArray(dash.get("video").toString()).get(0).toString()).get("baseUrl").toString();
            String  audio= new JSONObject(new JSONArray(dash.get("audio").toString()).get(0).toString()).get("baseUrl").toString();

            VideoNames.add(VideoName);
            VideoUrl.add(video);
            AudioUrl.add(audio);

            System.out.println("成功获取 "+VideoName+" 的地址！");
            System.out.println("视频地址："+video+"\n");
            System.out.println("音频地址："+audio+"\n");

        }catch (Exception e){
            e.printStackTrace();
            System.out.println("此处报错了！"+res);
        }

    }
    //获取视频标题
    /*
    public static String getVideoName(String BV){
        String html= Japl.GET("https://www.bilibili.com/video/"+BV,"");
        Document doc = Jsoup.parse(html);
        Elements x = doc.getElementsByTag("H1");
        if(x.toString().contains("title")){
            return x.text();
        }
        return "null";
    }
     */
}

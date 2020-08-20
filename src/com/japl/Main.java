package com.japl;

import com.japl.Utils.Japl;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.*;

public class Main {

    static ArrayList<String> VideoNames=new ArrayList<>();
    static ArrayList<String> VideoUrl=new ArrayList<>();
    static ArrayList<String> AudioUrl=new ArrayList<>();

    public static void main(String[] args) {
        Japl.BILIBILI();

        Japl.Println("我已经完全爱上沃玛了！！！",4);

        Japl.Print("你");
        Japl.Print("好");
        Japl.Print("世");
        Japl.Print("界");
        Japl.Println("你好世界！！");
        /*
        getCid("BV1U7411x7EX");
        getCid("BV1B64y1c7ta");

        for (int i=0;i<VideoNames.size();i++){
            System.out.println(VideoNames.get(i));
            System.out.println(VideoUrl.get(i));
            System.out.println(AudioUrl.get(i));
            System.out.println("\n");
        }
        */

    }
    //通过BV号获取cid
    public static void getCid(String BV){
        String cid_url="https://api.bilibili.com/x/player/pagelist?bvid="+BV+"&jsonp=jsonp";
        String res = Japl.GET(cid_url, "");
        JSONObject obj = new JSONObject(res);
        for (Object json :new JSONArray(obj.get("data").toString())){
            JSONObject jsonObject = new JSONObject(json.toString());
            String part = jsonObject.get("part").toString();
            String VideoName=(getVideoName(BV)+":"+part).replace(" ", "");
            getUrl(jsonObject.get("cid").toString(),BV,VideoName);
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
            System.out.println("视频地址："+video);
            System.out.println("音频地址："+audio);
            System.out.println();
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("此处报错了"+res);
        }

    }
    //获取视频标题
    public static String getVideoName(String BV){
        String html= Japl.GET("https://www.bilibili.com/video/"+BV,"");
        Document doc = Jsoup.parse(html);
        Elements x = doc.getElementsByTag("H1");
        if(x.toString().contains("title")){
            return x.text();
        }
        return "null";
    }
}

package org.say.xhttp;

/**
 * Created by say on 1/20/16.
 */
public class XHttpTest {

    public static void main(String[] args) {
//        String r = XHttp.url("http://115.29.192.163:8080/attendance").data("day", "21").data("file", new File("/home/say/Downloads/10月打卡-玉林.xls")).post().dataToString();
//        System.out.println(r);

        String r = XHttp.url("http://www.mytju.com/classCode/tools/encode_gb2312.asp").get().dataToString();
        System.out.println(r);

//        String r = XHttp.url("https://www.baidu.com/s?ie=utf-8&f=8&rsv_bp=1&rsv_idx=2&tn=baiduhome_pg&wd=gbk%E7%BC%96%E7%A0%81%E7%BD%91%E7%AB%99&rsv_spt=1&oq=b&rsv_pq=9e5e091d00056097&rsv_t=c341W6zGWzSl0WBg0M3lA8Hj4bsUIUJN0SzzkIhH7xjETpmsgxO6RO%2BLj7DRu2Ub%2BdV9&rsv_enter=1&rsv_sug3=28&rsv_sug1=3&bs=b").get().dataToString();
//        System.out.println(r);

    }

//    @Test
//    public void testGet() throws IOException {
//        String r = XHttp.url("http://www.baidu.com").get().dataToString();
//        Assert.assertNotNull(r);
//    }
//
//    @Test
//    public void testGetData() throws IOException {
//        String r = XHttp.url("http://www.baidu.com").data("a", "b").get().dataToString();
//        Assert.assertNotNull(r);
//    }


}

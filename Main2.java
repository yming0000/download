import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class Main2 {

    private static final String NUMBER_FORMAT = "NUMBERFORMAT";

    public static void main(String[] args) throws Exception {
        downloadFile();
    }

    private static void downloadFile() throws Exception {
        String filePath = "FILEPATH";
        File dir = new File(filePath);
        if (!dir.isDirectory()) {
            dir.mkdirs();
        }
        String url1 = "URL1";
        String url2 = "URL2";
        String url = url1 + NUMBER_FORMAT + url2;
        for (int i = 0; i < 20; i++) {
            url = url.replace(NUMBER_FORMAT, number(i, 3));
            try {
                download(url, filePath);
            } catch (Exception e) {}
        }
    }

    private static String number(int i, int j) {
        String s = String.valueOf(i);
        if (s.length() < i) {
            s = "0" + s;
        }
        return s;
    }

    private static void download(String urlString, String filePath) throws Exception {
        URL url = new URL(urlString);
        URLConnection con = url.openConnection();
        InputStream is = con.getInputStream();
        StringBuffer htmlSb = new StringBuffer();
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(is, "utf-8"));
            while (br.ready()) {
                htmlSb.append(br.readLine());
            }
            br.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // HTML文件字符串
        String htmlStr = htmlSb.toString();
        for (String s : imgType()) {
            String[] ss = htmlStr.split(s);
            for (int i = 0; i < ss.length - 1; i++) {
                String u = ss[i].substring(ss[i].lastIndexOf("=") + 1) + s;
                if (u.startsWith("\"") || u.startsWith("\'")) {
                    u = u.substring(1);
                }
                if (u.startsWith("http")) {
                } else if (u.startsWith("//")) {
                    u = url.getProtocol() + ":" + u;
                } else if (u.startsWith("/")) {
                    u = url.getProtocol() + "://" + url.getHost() + u;
                } else {
                    u = urlString + u;
                }
                try {
                    downloadFile(u, filePath);
                } catch (Exception e) {}
            }
        }
    }

    private static void downloadFile(String urlString, String filePath) throws Exception {
        URL url = new URL(urlString);
        URLConnection con = url.openConnection();
        InputStream is = con.getInputStream();
        byte[] bs = new byte[1024];
        int len;
        String[] ss = url.getPath().split("/");
        String filename = filePath + "/" + ss[ss.length - 1];
        File file = new File(filename);
        FileOutputStream os = new FileOutputStream(file, true);
        while ((len = is.read(bs)) != -1) {
            os.write(bs, 0, len);
        }
        os.close();
        is.close();
    }

    private static List<String> imgType() {
        List<String> list = new ArrayList<String>();
        list.add("bmp");
        list.add("jpg");
        list.add("jpeg");
        list.add("png");
        list.add("gif");
        return list;
    }

}

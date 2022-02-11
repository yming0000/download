import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class Main1 {

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
            download(url, filePath);
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

}
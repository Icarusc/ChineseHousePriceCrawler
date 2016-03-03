package icarus.webmagic.test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexTest {

    public static void main(String[] args) {

        String[] bylist = "2016Äê".split("Äê");
        System.out.println(bylist);
        File file = new File("./files/houses.txt");
        if (!file.exists()) {
            try {
                file.createNewFile();
                FileWriter fw = new FileWriter(file.getAbsoluteFile());
                BufferedWriter bw = new BufferedWriter(fw);
                bw.write("asd");
                bw.close();

                System.out.println("Done");
            } catch (IOException e) {
                System.out.println("Error while create new file...");
            }
        }

        String str1 = "http://sh.lianjia.com/ershoufang/SHA10203913.html";
        String strRex = "http://sh\\.lianjia\\.com/ershoufang/[a-zA-Z]+\\d+.html$";
        Pattern pattern = Pattern.compile(strRex);
        Matcher matcher = pattern.matcher(str1);
        boolean rs = matcher.matches();
        System.out.println(rs);
        if (rs)
            System.out.println(matcher.group());

        strRex = "a";
        pattern = Pattern.compile(strRex);
        matcher = pattern.matcher(str1);
        rs = matcher.matches();
        System.out.println(rs);
        if (rs)
            System.out.println(matcher.group());

        str1 = "asc";
        matcher = pattern.matcher(str1);
        rs = matcher.matches();
        System.out.println(rs);

        str1 = "sc";
        matcher = pattern.matcher(str1);
        rs = matcher.matches();
        System.out.println(rs);
    }
}

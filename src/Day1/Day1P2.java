package Day1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day1P2 {
    public static void main(String[] args){
        String day1txt = null;
        try{
            day1txt = fileToString("src/Day1/hi.txt");
        }catch (IOException e){
            System.out.println(e);
        }
        HashMap<String, String> mapfirst = new HashMap<String, String>();
        HashMap<String, String> mapsecond = new HashMap<String, String>();
        String[] ary = {"one", "two", "three", "four", "five", "six", "seven", "eight", "nine"};
        String[] revary = {"eno", "owt", "eerht", "ruof", "evif", "xis", "neves", "thgie", "enin"};
        for(int i = 0; i < 9; i++){
            mapfirst.put(ary[i], "" + (i+1));
            mapsecond.put(revary[i], "" + (i+1));
        }
        String regexfirst = "(1|2|3|4|5|6|7|8|9|one|two|three|four|five|six|seven|eight|nine)";
        Pattern ptrnfirst = Pattern.compile(regexfirst);
        String regexsecond = "(1|2|3|4|5|6|7|8|9|eno|owt|eerht|ruof|evif|xis|neves|thgie|enin)";
        Pattern ptrnsecond = Pattern.compile(regexsecond);
        int idx = 0;
        StringBuilder curline = new StringBuilder("");
        int sum = 0;
        while(idx < day1txt.length()){
            while(day1txt.charAt(idx) != '\n'){
                curline.append(day1txt.charAt(idx));
                idx++;
            }
            int firstnum = getNum(curline.toString(), ptrnfirst, mapfirst);
            int secondnum = getNum(curline.reverse().toString(), ptrnsecond, mapsecond);
            int curNum = (firstnum * 10) + secondnum;
            System.out.println(curline.reverse().toString() + "\t" + firstnum + "\t"+ secondnum + "\t"+ curNum);

            sum +=curNum;
            curline.setLength(0);
            idx += 1;
        }
        System.out.println(sum);
    }
    public static int getNum(String line, Pattern ptrn, HashMap<String, String> map){
        Matcher matcher = ptrn.matcher(line);
        String req = null;
        if(matcher.find()){
            req = matcher.group();
        }
        assert req != null;
        if(req.length() == 1){
            return Integer.parseInt(req);
        }
        return Integer.parseInt(map.get(req));

    }

    public static String fileToString(String filePath) throws IOException {
        Path path = Paths.get(filePath);
        byte[] bytes = Files.readAllBytes(path);
        return new String(bytes);
    }
}

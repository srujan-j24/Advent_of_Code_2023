package Day1;

import java.io.IOException;
import java.nio.file.*;
import java.util.Deque;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day1P1 {
    public static void main(String[] args){
        String day1txt = null;
        try{
            day1txt = fileToString("src/Day1/input.txt");
        }catch (IOException e){
            System.out.println(e);
        }
        String regex = "(1|2|3|4|5|6|7|8|9)";
        Pattern ptrn = Pattern.compile(regex);
        int idx = 0;
        StringBuilder curline = new StringBuilder("");
        Deque<String> container = new LinkedList<>();
        int sum = 0;
        while(idx < day1txt.length()){
            while(day1txt.charAt(idx) != '\n'){
                curline.append(day1txt.charAt(idx));
                idx++;
            }
            Matcher matcher = ptrn.matcher(curline.toString());
            while(matcher.find()){
                container.add(matcher.group());
            }
            System.out.print(curline.toString());
            System.out.println(" " + container.getFirst() + container.getLast());
            String curNum = "" + container.getFirst() + container.getLast();
            System.out.println(curNum);
            sum += Integer.parseInt("" + container.getFirst() + container.getLast());
            container.clear();
            curline.setLength(0);
            idx += 1;
        }
        System.out.println(sum);
    }
    public static String fileToString(String filePath) throws IOException {
        Path path = Paths.get(filePath);
        byte[] bytes = Files.readAllBytes(path);
        return new String(bytes);
    }
}

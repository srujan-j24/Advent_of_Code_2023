package Day5;

import org.w3c.dom.ranges.Range;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day5P1 {
    static ArrayList<Long> numberList = new ArrayList<>();
    static ArrayList<Boolean> stateList = new ArrayList<>();
    static String valRegex = "([0-9]*) ([0-9]*) ([0-9]*)";
    static Pattern valPtrn = Pattern.compile(valRegex);

    static class RangeObj{
        Long dstStart, srcStart, srcEnd;

        RangeObj(Long dStart, Long sStart, Long rngLen){
            this.dstStart = dStart;
            this.srcStart = sStart;
            this.srcEnd = sStart + rngLen -1;
        }
        void checkAndAlter(){
            for(int i = 0; i < numberList.size(); i++){
                Long cur = numberList.get(i);
                if(cur >= srcStart && cur <= srcEnd && stateList.get(i)){

                    Long newCur = (cur - srcStart) + dstStart;
                    System.out.println(srcStart + "  "  + srcEnd);
                    System.out.println((i+1) + " " + cur + " -> " + newCur);
                    numberList.set(i, newCur);
                    stateList.set(i, false);
                }
            }
        }

    }
    static Long findMin(ArrayList<Long> list){
        Long min = Long.MAX_VALUE;
        for(int i = 0; i < list.size(); i++){
            min = Math.min(min, list.get(i));
        }
        return min;
    }

    public static void main(String[] args){
        String input = null;
        try{
            Path path = Paths.get("src/Day5/input5.txt");
            byte[] bytes = Files.readAllBytes(path);
            input = new String(bytes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        StringBuilder curLine = new StringBuilder();
        int i = 0;
        while(input.charAt(i) != '\n'){
            curLine.append(input.charAt(i++));
        }
        String regex0 = "seeds:([0-9 ]*)";
        Pattern ptrn = Pattern.compile(regex0);
        Matcher mt = ptrn.matcher(curLine.toString());
        String nums = null;
        while (mt.find()){
            nums = mt.group(1);
        }

        assert nums != null;
        String[] numary = nums.trim().split(" ");
        for(String s: numary){
            numberList.add(Long.parseLong(s));
            stateList.add(true);
        }


        i+=3;
        input = input.substring(i);
        String regex = "([a-z -]*):\n([0-9 \n]*\n)";
        ptrn = Pattern.compile(regex);
        Matcher mtchr = ptrn.matcher(input);

        while(mtchr.find()){
            System.out.println(numberList);
            handleConversion(mtchr.group(2));
            System.out.println("================");
            stateList.replaceAll(ignored -> true);

        }

        System.out.println(findMin(numberList));


    }
    public static void handleConversion(String values){
        Matcher mtch = valPtrn.matcher(values);
        while(mtch.find()){
            Long destStart = Long.parseLong(mtch.group(1));
            Long srcStart = Long.parseLong(mtch.group(2));
            Long rangeLen = Long.parseLong(mtch.group(3));
            RangeObj cur = new RangeObj(destStart, srcStart, rangeLen);
            cur.checkAndAlter();
        }
    }
}

package Day5;

import java.io.IOException;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day5P2 {


    static class NumberInfo {
        long startNum, range, endNum;
        NumberInfo(long s, long r){
            this.startNum = s;
            this.range = r;
            this.endNum = s + r - 1;
        }
    }
    static class ConverterInfo{
        long destStart, destEnd, srcStart, srcEnd, rng, diff;
        ConverterInfo(long d, long s, long r){
            this.destStart = d;
            this.srcStart = s;
            this.rng = r;
            this.destEnd = destStart + rng - 1;
            this.srcEnd = srcStart + rng - 1;
            this.diff = srcStart - destStart;
        }
    }

    static ArrayList<NumberInfo> numRangeList = new ArrayList<>();
    static void compareAndUpdate(ArrayList<ConverterInfo> list){
        ArrayList<NumberInfo> temp = new ArrayList<>();
        for(int i = 0; i < numRangeList.size(); i++){
            boolean isConverted = false;
            for(ConverterInfo convtInfo: list){
                NumberInfo curNumInfo = numRangeList.get(i);
                if(curNumInfo.startNum >= convtInfo.srcStart && curNumInfo.endNum <= convtInfo.srcEnd){
                    long newStart = curNumInfo.startNum - convtInfo.diff;
                    temp.add(new NumberInfo(newStart, curNumInfo.range));
                    isConverted = true;
                    break;
                }
                else if(curNumInfo.startNum >= convtInfo.srcStart && curNumInfo.startNum <= convtInfo.srcEnd){
                    long newStart = curNumInfo.startNum - convtInfo.diff;
                    long newRange = convtInfo.srcEnd - curNumInfo.startNum + 1;
                    if(newRange <= 0){
                        System.out.println("hi");
                    }
                    NumberInfo newnumInfo = new NumberInfo(newStart, newRange);
                    temp.add(newnumInfo);
                    curNumInfo.startNum = convtInfo.srcEnd +1;
                    curNumInfo.range = curNumInfo.endNum - convtInfo.srcEnd;
                }
                else if(curNumInfo.endNum <= convtInfo.srcEnd && curNumInfo.endNum >= convtInfo.srcStart){
                    long newStart = convtInfo.srcStart - convtInfo.diff;
                    long newRange = curNumInfo.endNum - convtInfo.srcStart + 1;
                    if(newRange <= 0){
                        System.out.println("bi");
                    }
                    NumberInfo newnumInfo = new NumberInfo(newStart, newRange);
                    temp.add(newnumInfo);
                    curNumInfo.endNum = convtInfo.srcStart - 1;
                    curNumInfo.range = convtInfo.srcStart - curNumInfo.startNum + 1;
                }
            }
            if(!isConverted){
                temp.add(numRangeList.get(i));
            }
        }
        numRangeList = temp;

    }
    static void computeSection(String str){
        Pattern ptrn = Pattern.compile("(\\d+) (\\d+) (\\d+)");
        Matcher mt = ptrn.matcher(str);
        ArrayList<ConverterInfo> convertList = new ArrayList<>();
        while(mt.find()){
            long ds = Long.parseLong(mt.group(1));
            long ss = Long.parseLong(mt.group(2));
            long r = Long.parseLong(mt.group(3));
            convertList.add(new ConverterInfo(ds, ss, r));
        }
        compareAndUpdate(convertList);
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
        Pattern ptrn = Pattern.compile("(\\d+) (\\d+)");
        Matcher mt = ptrn.matcher(curLine.toString());
        while(mt.find()){
            long start = Long.parseLong(mt.group(1));
            long range = Long.parseLong(mt.group(2));
            numRangeList.add(new NumberInfo(start, range));
        }
        ArrayList<String> sections = new ArrayList<>();
        ptrn = Pattern.compile("([\\d+ \n]*\n\n)");
        mt = ptrn.matcher(input.substring(i+3));
        while(mt.find()){
            sections.add(mt.group().trim());
        }

        while(!sections.isEmpty()){
            computeSection(sections.remove(0));
        }
        for(NumberInfo n: numRangeList){
            System.out.println(n.startNum + " " + n.endNum + " " + n.range);
        }
        long min = Long.MAX_VALUE;
        for(NumberInfo n: numRangeList){
            min = Long.min(min, n.startNum);
        }
        System.out.println(min);



    }

}

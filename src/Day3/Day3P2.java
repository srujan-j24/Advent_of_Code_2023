package Day3;
import java.util.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class Day3P2{
    static class Part{
        int colStart, colEnd, row;
        String val;
        ArrayList<Integer> ratioNums = new ArrayList<>();
        Part(int s, int e, int r, String val){
            this.colStart = s;
            this.colEnd = e;
            this.row = r;
            this.val = val;
        }
    }

    static HashMap<String, Part> map = new HashMap<>();
    public static void main(String[] args){
        String input = null;
        try{
            Path path = Paths.get("src/Day3/input3.txt");
            byte[] bytes = Files.readAllBytes(path);
            input = new String(bytes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        int rowNum = 0;
        int colNum = 0;
        StringBuilder partnumber = new StringBuilder();
        int i = 0;
        int res = 0;
        while(i < input.length()){
            char cur = input.charAt(i);
            if(cur == '.'){
                colNum++;
                i++;
                continue;
            }else if(cur == '\n'){
                i++;
                rowNum++;
                colNum = 0;
            }
            else if(Character.isDigit(cur)){

                int start = colNum;
                int end;
                while(Character.isDigit(cur)){
                    partnumber.append(cur);
                    i++;
                    colNum++;
                    cur = input.charAt(i);
                }
                end = colNum-1;
                map.put("" + start + end + rowNum, new Part(start, end, rowNum, partnumber.toString()));
                partnumber.setLength(0);
            }else{
                map.put("" + colNum + colNum + rowNum, new Part(colNum, colNum, rowNum, "" + cur));
                i++;
                colNum++;
            }
        }

        for(Map.Entry<String, Part> entry: map.entrySet()){
            Part curPart = entry.getValue();
            if(curPart.val.matches("\\d+")){
                //check if a part
                boolean ispart = checkIfPart(curPart.colStart, curPart.colEnd, curPart.row, Integer.parseInt(curPart.val));
                System.out.println(curPart.val + "\t" + ispart);
                if(ispart)
                    res += Integer.parseInt(curPart.val);
            }
        }
        System.out.println(res);
        int actualSum = 0;
        for(Map.Entry<String, Part> entry: map.entrySet()){
            Part curPart = entry.getValue();
            System.out.println("*");
            if(curPart.val.equals("*") && curPart.ratioNums.size() == 2){
                System.out.println(curPart.ratioNums.get(0) + " " + curPart.ratioNums.get(1));
                actualSum += curPart.ratioNums.get(0) * curPart.ratioNums.get(1);
            }
        }
        System.out.println(actualSum);

    }
    public static boolean checkIfPart(int start, int end, int row,int val){
        String key = "" + start + end + row;
        boolean top = false, bottom = false, right, left, tr, tl, br, bl;
        for(int i = start; i < end+1; i++){
            top = checkSymbol("" + i + i + (row-1), val) || top;
            bottom = checkSymbol("" + i + i + (row+1), val) || bottom;
        }
        right = checkSymbol("" + (end+1) + (end+1) + row, val);
        left = checkSymbol("" + (start-1) + (start-1) + row, val);
        tr = checkSymbol("" + (end+1) + (end+1) + (row-1), val);
        tl = checkSymbol("" + (start-1) + (start-1) + (row-1), val);
        br = checkSymbol("" + (end+1) + (end+1) + (row+1), val);
        bl = checkSymbol("" + (start-1) + (start-1) + (row+1), val);

        return top || bottom || right || left || tr || tl || br || bl;

    }
    public static boolean checkSymbol(String posKey,int gearNum){
        if(map.containsKey(posKey)){
            if(map.get(posKey).val.equals("*")){
                map.get(posKey).ratioNums.add(gearNum);
            }
            return true;
        }
        return false;
    }
}


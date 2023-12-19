package Day8;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day8P2 {
    static class NodeInfo{
        String  key, left , right;
        boolean isStart = false;
        boolean isEnd = false;
        NodeInfo(String k, String l, String r){
            this.left = l;
            this.right = r;
            if(k.charAt(2) == 'A'){
                isStart = true;
            }else if(k.charAt(2) == 'Z'){
                this.isEnd = true;
            }
            this.key = k;
        }
    }
    static HashMap<String, NodeInfo> map = new HashMap<>();
    static ArrayList<String> curNodes = new ArrayList<>();
    static HashMap<Integer, Integer> firstEncounter = new HashMap<>();
    static boolean reachedEnd(int steps){
        for(int i = 0; i < curNodes.size(); i++){
            if(map.get(curNodes.get(i)).isEnd){
                if(!firstEncounter.containsKey(i)){
                    firstEncounter.put(i, steps);
                }
            }
            if(firstEncounter.size() == curNodes.size()){
                return true;
            }
        }
        for(String n : curNodes){
            if(!map.get(n).isEnd)
                return false;
        }
        return true;
    }

    public static void main(String[] args){
        String input =  null;
//        input = "LR\n" +
//                "\n" +
//                "11A = (11B, XXX)\n" +
//                "11B = (XXX, 11Z)\n" +
//                "11Z = (11B, XXX)\n" +
//                "22A = (22B, XXX)\n" +
//                "22B = (22C, 22C)\n" +
//                "22C = (22Z, 22Z)\n" +
//                "22Z = (22B, 22B)\n" +
//                "XXX = (XXX, XXX)\n";
        try{
            Path p = Paths.get("src/Day8/input8.txt");
            byte[] bytes = Files.readAllBytes(p);
            input = new String(bytes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        StringBuilder direction = new StringBuilder();
        int i = 0;
        while(input.charAt(i) != '\n'){
            direction.append(input.charAt(i++));
        }
        String directions = direction.toString();
        String nodes = input.substring(i+2);
        Pattern ptrn = Pattern.compile("([A-Z0-9]*) = \\(([A-Z0-9]*), ([A-Z0-9]*)\\)\n");
        Matcher mt = ptrn.matcher(nodes);
        while(mt.find()){
            map.put(mt.group(1), new NodeInfo(mt.group(1), mt.group(2), mt.group(3)));
        }

        for(String key: map.keySet()){
            if(map.get(key).isStart){
                curNodes.add(key);
            }
        }
        int steps = 0;
        int directionLen = directions.length();
        while(!reachedEnd(steps)){
            char curDirection = directions.charAt(steps % directionLen);
            steps += 1;
            if(curDirection == 'L'){
                curNodes.replaceAll(key -> map.get(key).left);
            }else{
                curNodes.replaceAll(key -> map.get(key).right);
            }
        }
        System.out.println(firstEncounter);
        int j = 0;
        long[] firstEnc = new long[firstEncounter.size()];
        for(Integer key: firstEncounter.keySet()){
            firstEnc[j++] = firstEncounter.get(key);
        }
        System.out.println(LCMofArray(firstEnc));

    }
    static long LCMofArray(long[] ary){
        long lcm = ary[0];
        for(int i = 1; i < ary.length; i++){
            lcm = (lcm * ary[i]) / gcd(lcm , ary[i]);
        }
        return lcm;
    }
    static long gcd(long a, long b){
        if(b == 0){
            return a;
        }
        return gcd(b, a % b);
    }
}

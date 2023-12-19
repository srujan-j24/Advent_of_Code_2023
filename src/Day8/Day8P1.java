package Day8;

import com.sun.security.auth.module.NTLoginModule;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day8P1 {
    static class NodeInfo{
        String  left , right;
        NodeInfo(String l, String r){
            this.left = l;
            this.right = r;
        }
    }
    static HashMap<String, NodeInfo> map = new HashMap<>();
    static String curNode = null;
    public static void main(String[] args){
        String input =  null;
//        input = "LLR\n" +
//                "\n" +
//                "AAA = (BBB, BBB)\n" +
//                "BBB = (AAA, ZZZ)\n" +
//                "ZZZ = (ZZZ, ZZZ)\n";
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
        Pattern ptrn = Pattern.compile("([A-Z]*) = \\(([A-Z]*), ([A-Z]*)\\)\n");
        Matcher mt = ptrn.matcher(nodes);
        while(mt.find()){
            System.out.println(mt.group(1));
            map.put(mt.group(1), new NodeInfo(mt.group(2), mt.group(3)));
        }
        curNode = "AAA";
        int steps = 0;
        int directionLen = directions.length();
        while(!curNode.equals("ZZZ")){
            char curDirection = directions.charAt(steps % directionLen);
            steps += 1;
            if(curDirection == 'L'){
                curNode = map.get(curNode).left;
            }else{
                curNode = map.get(curNode).right;
            }
        }
        System.out.println(steps);
    }
}

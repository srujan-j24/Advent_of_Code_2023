package Day2;

import java.io.IOException;
import java.nio.file.*;
import java.util.*;
import java.util.regex.*;

public class Day2P2 {
    static HashMap<String , Integer> cubeInfomap = new HashMap<String, Integer>();
    static HashMap<String, Integer> minCubemap = new HashMap<String, Integer>();
    static String[] colorAry = {"red", "blue", "green"};
    public static void main(String[] args){
        cubeInfomap.put("red" , 12);
        cubeInfomap.put("green", 13);
        cubeInfomap.put("blue", 14);
        minCubemap.put("red", 0);
        minCubemap.put("green", 0);
        minCubemap.put("blue", 0);
        //hi
        String input = null;
        try{
            Path path = Paths.get("src/Day2/day2.txt");
            byte[] bytes = Files.readAllBytes(path);
            input = new String(bytes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

//        String regex = "Game ([0-9])*:(([a-z0-9 ,]*);?)*";
//        Pattern ptrn = Pattern.compile(regex);
        String regex1 = "Game ([0-9]*): ([a-z0-9 ,;]*)$";
        Pattern ptrn1 = Pattern.compile(regex1);

        int idx = 0;
//        Deque<String> list = new LinkedList<>();
        StringBuilder curline = new StringBuilder();

        int sum = 0;
        while(idx < input.length()){
            while(input.charAt(idx) != '\n'){
                curline.append(input.charAt(idx));
                idx++;
            }
            Matcher matcher = ptrn1.matcher(curline.toString());
//            Matcher matcher = ptrn.matcher(curline.toString());
            String gameNo = null;
            String gameInfo = null;
            while(matcher.find()){
                gameNo = matcher.group(1);
                gameInfo = matcher.group(2);
            }
            isvalid(gameInfo);

            System.out.println(minCubemap);
            sum += minCubemap.get("red") * minCubemap.get("blue") * minCubemap.get("green");
            minCubemap.put("red", 0);
            minCubemap.put("green", 0);
            minCubemap.put("blue", 0);

            System.out.println("\n" + curline);
//            System.out.println(list);
            curline.setLength(0);
//            list.clear();
            idx++;
        }
        System.out.println(sum);

    }
    public static void isvalid(String info){
        String[] gamePicks = info.split(";");
        for(String picks: gamePicks){
            String[] pickInfo= picks.split(",");
            validatePick(pickInfo);
        }
    }
    public static void validatePick(String[] pickInfo){
        for(int i = 0; i < pickInfo.length; i++){
            pickInfo[i] = pickInfo[i].trim();
        }
        for(String pick: pickInfo){
            String[] cubeInfo = pick.split(" ");
            int curCubeNum = Integer.parseInt(cubeInfo[0]);

            if(minCubemap.get(cubeInfo[1])< curCubeNum)
                minCubemap.put(cubeInfo[1], curCubeNum);
        }
    }


}
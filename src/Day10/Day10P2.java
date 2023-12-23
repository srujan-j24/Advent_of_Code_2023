package Day10;

import javax.naming.CannotProceedException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day10P2 {
    static class Tiles{
        int tileType = 0;//0 for outer 1 for pipe 2 for inner
        int key ;
        char shape;
        Tiles(int k, char s){
            this.key = k;
            this.shape = s;
        }
    }
    static HashMap<Integer, Tiles> map = new HashMap<>();
    static HashMap<String, Integer> direction = new HashMap<>();
    static{
        direction.put("up", 1);
        direction.put("down", -1);
        direction.put("right" , +1000);
        direction.put("left", -1000);
    }


    static int goNext(int cur){
        map.get(cur).tileType = 1;

        switch (map.get(cur).shape) {
            case '|' :
                if(map.get(cur - 1000).tileType == 1){
                    return cur + 1000;
                }else{
                    return cur - 1000;
                }
            case '-':
                if(map.get(cur -1).tileType == 1){
                    return cur + 1;
                }else{
                    return cur - 1;
                }
            case 'L':
                if(map.get(cur - 1000).tileType == 1){

                    return cur + 1;
                }else{
                    return cur - 1000;
                }
            case 'J':
                if(map.get(cur - 1000).tileType == 1){
                    return cur - 1;
                }else{
                    return cur - 1000;
                }
            case '7':
                if(map.get(cur + 1000).tileType == 1){
                    return cur -1;
                }else{
                    return cur + 1000;
                }
            case 'F':
                if(map.get(cur + 1000).tileType == 1){
                    return cur + 1;
                }else{
                    return cur + 1000;
                }
        }
        return 0;
    }
    static boolean isInner = true;

    public static void main(String[] args){
        String input = null;
        input = "FF7FSF7F7F7F7F7F---7\n" +
                "L|LJ||||||||||||F--J\n" +
                "FL-7LJLJ||||||LJL-77\n" +
                "F--JF--7||LJLJ7F7FJ-\n" +
                "L---JF-JLJ.||-FJLJJ7\n" +
                "|F|F-JF---7F7-L7L|7|\n" +
                "|FFJF7L7F-JF7|JL---7\n" +
                "7-L-JL7||F7|L7F-7F7|\n" +
                "L.L7LFJ|||||FJL7||LJ\n" +
                "L7JLJL-JLJLJL--JLJ.L\n";
        try{
            Path p = Paths.get("src/Day10/input10.txt");
            byte[] bytes = Files.readAllBytes(p);
            input = new String(bytes);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ArrayList<String> list = new ArrayList<>();
        Pattern ptrn = Pattern.compile("([-|LJ7F.S]*)\n");
        Matcher mt = ptrn.matcher(input);
        while(mt.find()){
            list.add(mt.group(1));
        }
        int startingKey = 0;
        for(int r = 0; r < list.size(); r++){
            String cur = list.get(r);
            for(int c = 0; c < cur.length(); c++){
                int hashKey = (r * 1000) + c;
                if(cur.charAt(c) == 'S'){
                    startingKey = hashKey;
                }
                map.put(hashKey, new Tiles(hashKey, cur.charAt(c)));
            }
        }
        ArrayList<Integer> starts = new ArrayList<>();
        getStartingPostions(starts, startingKey);

        map.get(startingKey).tileType = 1;
        int start1 = starts.get(0);
        int start2 = starts.get(1);
        LinkedList<Integer> path = new LinkedList<>();
        path.add(startingKey);
        path.add(start2);
        while(start1 != start2){
            start2 = goNext(start2);
            path.add(start2);
        }
        map.get(start2).tileType = 1;
        char previous = '.';
        int factor = 0;
        for(int r = 0; r < list.size(); r++){
            isInner = false;
            factor = 0;
            for(int c = 0; c < list.get(r).length(); c++){
                int hashKey = (r * 1000) + c;
                Tiles cur = map.get(hashKey);
                if(cur.tileType == 0){
                    if(isInner){
                        cur.tileType = 2;
                        cur.shape = '.';
                    }
                }else{
                    if(cur.shape == '-'){
                        continue;
                    }
                    if(cur.shape == '|'){
                        isInner = !isInner;
                    }
                    if(cur.shape == 'L'){
                        /* L = -1  J = +1  F +1  7 -1*/
                        factor = -1;
                    }
                    if(cur.shape == 'J'){
                        factor += +1;
                        if(Math.abs(factor) == 2){
                            isInner = !isInner;
                        }
                    }
                    if(cur.shape == 'F'){
                        factor = +1;
                    }
                    if(cur.shape == '7'){
                        factor += -1;
                        if(Math.abs(factor) == 2){
                            isInner = !isInner;
                        }
                    }
                    if(cur.shape == 'S'){
                        isInner = !isInner;
                    }
                }
                previous = cur.shape;


            }
        }
        showMaze(list);
        int total = 0;
        for(Integer i : map.keySet()){
            if(map.get(i).tileType == 2){
                total += 1;
            }
        }
        System.out.println(total);



    }
    static void showMaze(ArrayList<String> list){
        for(int r = 0; r < list.size(); r++){
            String cur = list.get(r);
            for(int c = 0; c < cur.length(); c++){
                int hashKey = (r * 1000) + c;
                if(map.get(hashKey).shape == 'S'){
                    System.out.print("🟥");
                }
//                else if(map.get(hashKey).shape == '.'){
//                    System.out.print("🟪");
//                }
//                else if(map.get(hashKey).shape == 'L' && map.get(hashKey).tileType == 1){
//                    System.out.print("🟨");
//                }
                else if(map.get(hashKey).tileType == 2){
                    System.out.print("🟩");
                }
                else if(map.get(hashKey).tileType == 1){
                    System.out.print("⬛");
                }else{
                    System.out.print("🟦");
                }
            }
            System.out.println();
        }
        System.out.println("\n\n\n");
    }
    static void getStartingPostions(ArrayList<Integer> starts, int startingKey){
        if(map.get(startingKey - 1000) != null){
            char top = map.get(startingKey - 1000).shape;
            if(top == '|' || top == '7' || top == 'F'){
                starts.add(startingKey - 1000);
            }
        }
        if(map.get(startingKey + 1000) != null){
            char bottom = map.get(startingKey + 1000).shape;
            if(bottom == '|' || bottom == 'L' || bottom == 'J'){
                starts.add(startingKey + 1000);
            }
        }
        if(map.get(startingKey + 1) != null){
            char right = map.get(startingKey + 1).shape;
            if(right == '-' || right == 'J' || right == '7'){
                starts.add(startingKey + 1);
            }
        }
        if(map.get(startingKey - 1) != null){
            char left = map.get(startingKey - 1).shape;
            if(left == '-' || left == 'L' || left == 'F'){
                starts.add(startingKey - 1);
            }
        }
    }
}
//for(int r = 0; r < list.size(); r++){
//        String cur = list.get(r);
//        for(int c = 0; c < cur.length(); c++){
//        int hashKey = (r * 1000) + c;
//        if(map.get(hashKey).shape == 'S'){
//        System.out.print("🟥");
//        }
//                else if(map.get(hashKey).shape == 'L' && map.get(hashKey).tileType == 1){
//                    System.out.print("🟨");
//                }
//        else if(map.get(hashKey).tileType == 2){
//        System.out.print("🟩");
//        }
//        else if(map.get(hashKey).tileType == 1){
//        System.out.print("⬛");
//        }else{
//        System.out.print("🟦");
//        }
//        }
//        System.out.println();
//        }
//        System.out.println("\n\n\n");
//        k++;
//        if(k ==  3){
//        System.exit(-1);
//        }
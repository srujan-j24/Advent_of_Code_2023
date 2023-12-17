package Day6;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day6P2 {
    static class RaceInfo{
        long time, distance, winPossibilities;
        RaceInfo(long t, long d){
            this.time = t;
            this.distance = d;
            winPossibilities = 0;
        }
        void calcWinPossibilities(){
            for(int i = 1; i < time; i++){
                long speed = i;
                long remainingTime = time - speed;
                long distanceCovered = speed * remainingTime;
                if(distanceCovered > distance)
                    winPossibilities += 1;
            }
        }
    }
    public static void main(String[] args){
//        String input = "Time:      7  15   30\n" +
//                "Distance:  9  40  200\n";
        String input = null;
        try{
            Path p = Paths.get("src/Day6/input6.txt");
            byte[] bytes = Files.readAllBytes(p);
            input = new String(bytes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String regex = "([\\d+ ]*\n)";
        Pattern ptrn = Pattern.compile(regex);
        Matcher mt = ptrn.matcher(input);
        boolean found = mt.find();
        String[] timings = mt.group().trim().split("\\s+");
        String time = String.join("", timings);
        found = mt.find();
        String[] distances = mt.group().trim().split("\\s+");
        String distance = String.join("", distances);
        RaceInfo r = new RaceInfo(Long.parseLong(time), Long.parseLong(distance));
        r.calcWinPossibilities();
        System.out.println(r.winPossibilities);
    }
}

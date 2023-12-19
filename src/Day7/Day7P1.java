package Day7;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day7P1 {
    static HashMap<Character, Integer> cardStrength = new HashMap<>();
    static{
        cardStrength.put('2', 0);
        cardStrength.put('3', 1);
        cardStrength.put('4', 2);
        cardStrength.put('5', 3);
        cardStrength.put('6', 4);
        cardStrength.put('7', 5);
        cardStrength.put('8', 6);
        cardStrength.put('9', 7);
        cardStrength.put('T', 8);
        cardStrength.put('J', 9);
        cardStrength.put('Q', 10);
        cardStrength.put('K', 11);
        cardStrength.put('A', 12);
    }
    static HashMap<Character, Integer> checker = new HashMap<>();
    static int calcHandStrength(String hand){
        for(int i = 0 ; i < hand.length(); i++){
            checker.merge(hand.charAt(i), 1, Integer::sum);
        }
        if(checker.size() == 1){
            return 7;
        }
        else if(checker.size() == 2){
            for(Character key: checker.keySet()){
                if(checker.get(key) == 4){
                    return 6;
                }
            }
            return 5;
        }
        else if(checker.size() == 3){
            for(Character key: checker.keySet()){
                if(checker.get(key) == 3){
                    return 4;
                }
            }
            return 3;
        }
        else if(checker.size() == 4){
            return 2;
        }
        else{
            return 1;
        }

    }
    static int  calcIsStronger(String cur, String other){
        for(int i = 0; i < cur.length(); i++){
            if(cur.charAt(i) != other.charAt(i)){
                return cardStrength.get(cur.charAt(i)) - cardStrength.get(other.charAt(i));
            }
        }
        return 0;
    }
    static class Hand implements Comparable<Hand> {
        String value;
        int strength, bidAmt;
        Hand(String val, String bid){
            this.value = val;
            this.strength = calcHandStrength(value);
            checker.clear();
            this.bidAmt = Integer.parseInt(bid);
        }

        @Override
        public int compareTo(Hand o) {
            if(this.strength != o.strength){
                return this.strength - o.strength;
            }
            return calcIsStronger(this.value, o.value);
        }
    }
    public static void main(String[] args){
        PriorityQueue<Hand> pq = new PriorityQueue<>();
        String input = null;
//        String input = "32T3K 765\n" +
//                "T55J5 684\n" +
//                "KK677 28\n" +
//                "KTJJT 220\n" +
//                "QQQJA 483\n";
        try{
            Path p = Paths.get("src/Day7/input7.txt");
            byte[] bytes = Files.readAllBytes(p);
            input = new String(bytes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String regex = "([A-Z2-9]*) ([0-9]*)\n";
        Pattern ptrn = Pattern.compile(regex);
        Matcher mt = ptrn.matcher(input);
        while(mt.find()){

            pq.add(new Hand(mt.group(1), mt.group(2)));
        }
        long sum = 0;
        long rank = 1;
        while(!pq.isEmpty()){
            int curbid = pq.remove().bidAmt;
            sum += curbid * rank;
            rank++;
        }
        System.out.println(sum);
    }
}

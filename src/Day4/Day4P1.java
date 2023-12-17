package Day4;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day4P1 {
    static String regex = "Card  ? ?([0-9]*):([ 0-9]*)\\|([ 0-9]*)";
    static Pattern ptrn = Pattern.compile(regex);
    static class ScrathCard{

        int cardNo;
        ArrayList<Integer> myNums = new ArrayList<>();
        HashSet<Integer> winningSet = new HashSet<>();
        ArrayList<Integer> winningNums = new ArrayList<>();
        int points = 0;
        int matchNo = 0;
        ScrathCard(String s){
            String cardNumstr, winningNumstr, myNumstr;

            Matcher mtcher = ptrn.matcher(s);
            mtcher.find();
            cardNo = Integer.parseInt(mtcher.group(1));
            myNumstr = mtcher.group(2);
            winningNumstr = mtcher.group(3);

            String[] winsArray = winningNumstr.trim().split("  ?");
            String[] myArray = myNumstr.trim().split("  ?");

            for(String str: myArray){
                myNums.add(Integer.parseInt(str));
            }
            for(String str: winsArray){
                winningSet.add(Integer.parseInt(str));
            }
            for (Integer myNum : myNums) {
                if (winningSet.contains(myNum)) {
                    matchNo += 1;
                    if (points != 0) {
                        points = points * 2;
                    } else {
                        points = 1;
                    }
                }
            }
        }
    }
    public static void main(String[] args){
        String input = null;
        try{
            Path path = Paths.get("src/Day4/input.txt");
            byte[] bytes = Files.readAllBytes(path);
            input = new String(bytes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        StringBuilder curLine = new StringBuilder();
        int i = 0;
        LinkedList<ScrathCard> cardList = new LinkedList<>();
        while(i < input.length()){
            char cur = input.charAt(i);
            while(cur != '\n'){
                curLine.append(cur);
                i++;
                cur = input.charAt(i);
            }
            cardList.add(new ScrathCard(curLine.toString()));
            curLine.setLength(0);
            i++;
        }

        int sum = 0;
        for(ScrathCard card: cardList){
            sum += card.points;
        }

        System.out.println(sum);

    }
}

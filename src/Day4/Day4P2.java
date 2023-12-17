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

public class Day4P2 {

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
        LinkedList<Day4P1.ScrathCard> cardList = new LinkedList<>();
        while(i < input.length()){
            char cur = input.charAt(i);
            while(cur != '\n'){
                curLine.append(cur);
                i++;
                cur = input.charAt(i);
            }
            cardList.add(new Day4P1.ScrathCard(curLine.toString()));
            curLine.setLength(0);
            i++;
        }

        int sum = 0;
        for(Day4P1.ScrathCard card: cardList){
            sum += card.points;
        }
        ArrayList<Integer> totalcards = new ArrayList<>();
        i = 0;
        while(i < cardList.size()){
            totalcards.add(cardList.get(i).cardNo);
            i++;
        }
        i = 0;
        while(i < totalcards.size()){
            int curCardNo = totalcards.get(i);
            int curCardValue = cardList.get(curCardNo - 1).matchNo;
            for(int j = curCardNo+1; j <= curCardNo + curCardValue; j++){
                totalcards.add(j);
            }
            i++;
        }
        System.out.println(totalcards.size());
        System.out.println(sum);

    }
}

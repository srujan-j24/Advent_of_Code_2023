package Day9;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedList;

public class Day9P1 {
    static boolean fullZero(ArrayList<Integer> list){
        for(int i: list){
            if(i != 0)
                return false;
        }
        return true;
    }
    static class HistoryInfo{
        LinkedList<ArrayList<Integer>> data = new LinkedList<>();
        int size = 0, newLastNum;

        HistoryInfo(ArrayList<Integer> primary){
            data.add(primary);
            this.size = data.size();
            while(!fullZero(data.get(this.size-1))){
                ArrayList<Integer> temp = new ArrayList<>();
                ArrayList<Integer> prev = data.get(data.size()-1);
                for(int i = 1; i < prev.size(); i++){
                    temp.add(prev.get(i) - prev.get(i-1));
                }
                data.add(temp);
                this.size++;
            }
            int oldLastNum;
            newLastNum = 0;
            for(int i = data.size()-2; i >= 0; i--){
                ArrayList<Integer> temp = data.get(i);
                oldLastNum = temp.get(temp.size()-1);
                newLastNum = newLastNum + oldLastNum;

            }

        }
    }
    public static void main(String[] args){
        String input = null;
//        input = "0 3 6 9 12 15\n" +
//                "1 3 6 10 15 21\n" +
//                "10 13 16 21 30 45\n";
        try{
            Path p = Paths.get("src/Day9/input9.txt");
            byte[] bytes = Files.readAllBytes(p);
            input = new String(bytes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String[] historyList = input.split("\n");
        ArrayList<Integer> arylist = new ArrayList<>();
        for(String h: historyList){
            String[] nums = h.split(" ");
            ArrayList<Integer> list = new ArrayList<>();
            for(String num: nums){
                list.add(Integer.parseInt(num));
            }

            HistoryInfo temp = new HistoryInfo(list);
            System.out.println(temp.newLastNum);
            arylist.add(temp.newLastNum);
        }
        long sum = 0;
        for(int i: arylist){
            sum += i;
        }
        System.out.println(sum);
    }
}

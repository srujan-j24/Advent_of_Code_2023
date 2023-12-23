package Day10;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day10P1 {

    static class CoOrds{
        int c, r;
        char shape;
        CoOrds(int r, int c){
            this.c = c;
            this.r = r;
            this.shape = mazeMat[this.r][this.c];
        }
    }

    static char[][] mazeMat;
    static boolean sameCoOrd(CoOrds c0, CoOrds c1){
        return c0.c == c1.c && c0.r == c1.r;
    }
    static CoOrds getNext(CoOrds cur, HashSet<String> set){
        int r, c;
        switch (cur.shape){
            case '|':
                r = cur.r - 1;
                c = cur.c;
                return set.contains(r+""+c)? new CoOrds(r+2, c) : new CoOrds(r, c);
            case '-':
                r = cur.r;
                c = cur.c-1;
                return set.contains(r+""+c) ? new CoOrds(r, c+2) : new CoOrds(r, c);
            case 'L':
                r = cur.r-1;
                c = cur.c;
                return set.contains(r+""+c) ? new CoOrds(r+1, c+1) : new CoOrds(r, c);
            case 'J':
                r = cur.r-1;
                c = cur.c;
                return set.contains(r+""+c) ? new CoOrds(r+1, c-1) : new CoOrds(r, c);
            case '7':
                r = cur.r+1;
                c = cur.c;
                return set.contains(r+""+c) ? new CoOrds(r-1, c-1) : new CoOrds(r, c);
            case 'F':
                r = cur.r+1;
                c = cur.c;
                return set.contains(r+""+c) ? new CoOrds(r-1, c+1) : new CoOrds(r, c);
        }
        System.out.println("Error");
        System.exit(0);
        return null;
    }
    public static void main(String[] args){
        String input = null;
//        input = "-L|F7\n" +
//                "7S-7|\n" +
//                "L|7||\n" +
//                "-L-J|\n" +
//                "L|-JF\n";
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
        mazeMat = new char[list.size()][list.get(0).length()];
        int mazeRow = 0;
        int sRow = 0, sCol = 0;
        HashSet<String> path0 = new HashSet<>();
        HashSet<String> path1 = new HashSet<>();
        for(int i = 0; i < list.size(); i++){
            String s = list.get(i);
            char[] curRow = mazeMat[mazeRow];
            for(int j = 0; j < s.length(); j++){
                if(s.charAt(j) == 'S'){
                    sRow = i;
                    sCol = j;
                    path1.add(i+""+j);
                    path0.add(i+""+j);
                }
                curRow[j] = s.charAt(j);
            }
            mazeRow++;
        }
        ArrayList<CoOrds> startings = new ArrayList<>();
        char top = mazeMat[sRow-1][sCol];
        char bottom = mazeMat[sRow+1][sCol];
        char right = mazeMat[sRow][sCol+1];
        char left = mazeMat[sRow][sCol-1];

        if(top == '|' || top == '7' || top == 'F'){
            startings.add(new CoOrds(sRow-1, sCol));
        }
        if(bottom == '|' || bottom == 'L' || bottom == 'J'){
            startings.add(new CoOrds(sRow+1, sCol));
        }
        if(right == '-' || right == 'J' || right == '7'){
            startings.add(new CoOrds(sRow, sCol+1));
        }
        if(left == '-' || left == 'L' || left == 'F'){
            startings.add(new CoOrds(sRow, sCol-1));
        }
        CoOrds cur0 = startings.get(0);
        CoOrds cur1 = startings.get(1);
        int steps = 0;
        while(!sameCoOrd(cur0, cur1)){
            cur0 = getNext(cur0, path0);
            cur1 = getNext(cur1, path1);
            path0.add(cur0.r+""+cur0.c);
            path1.add(cur1.r+""+cur1.c);
            steps += 1;
        }
        System.out.println(steps);








    }
}

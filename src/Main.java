import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args){
        String regex = "(1|2|3|4|5|6|7|8|9|one|two|three|four|five|six|seven|eight|nine)";
        Pattern ptrn = Pattern.compile(regex);
        Matcher mt = ptrn.matcher("twone");
        while(mt.find()){
            System.out.println(mt.group());
        }
    }
}

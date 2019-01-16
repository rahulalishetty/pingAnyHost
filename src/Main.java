import com.sun.org.apache.xerces.internal.impl.xpath.regex.Match;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static final int NO_OF_PACKETS=10;
    public static void main(String[] args)  {
        Scanner scanner=new Scanner(System.in);
        String inputIp=scanner.nextLine();
        Pattern timePattern=Pattern.compile("(time=)(\\d+\\.?\\d*)");
        Matcher matcher=null;
        try {
            Process process = Runtime.getRuntime().exec("ping -c " + NO_OF_PACKETS + " " + inputIp);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            Double[] pingTimeArray = new Double[NO_OF_PACKETS];

            String pingResponse = "";
            bufferedReader.readLine();
            int arrayIndex = 0;
            while ((pingResponse = bufferedReader.readLine()) != null) {
//            System.out.println(pingResponse);
                matcher = timePattern.matcher(pingResponse);
                if (matcher.find()) {
                    pingTimeArray[arrayIndex++] = Double.parseDouble(matcher.group(2));
                }
            }
            Arrays.sort(pingTimeArray);
            System.out.println("median of time:" + (pingTimeArray[4] + pingTimeArray[5]) / 2);
            bufferedReader.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}

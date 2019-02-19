import com.sun.org.apache.xerces.internal.impl.xpath.regex.Match;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PingMedian {
    private static final int NO_OF_PACKETS=10;
    private static final Pattern timePattern=Pattern.compile("(time=)(\\d+\\.?\\d*)");
    private static Matcher matcher=null;

    public static void main(String[] args)  {
        Scanner scanner=new Scanner(System.in);
        String inputIp=scanner.nextLine();
        try {
            pingHost(inputIp);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public static void pingHost(String host) throws IOException {
        Process process = Runtime.getRuntime().exec("ping -c " + NO_OF_PACKETS + " " + host);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        Double[] pingTimeArray = new Double[NO_OF_PACKETS];

        String pingResponse = "";
        bufferedReader.readLine();
        int arrayIndex = 0;
        while ((pingResponse = bufferedReader.readLine()) != null) {
            System.out.println(pingResponse);
            matcher = timePattern.matcher(pingResponse);
            if (matcher.find()) {
                pingTimeArray[arrayIndex++] = Double.parseDouble(matcher.group(2));
            }
        }
        Arrays.sort(pingTimeArray);
        System.out.println("median of time:" + (pingTimeArray[4] + pingTimeArray[5]) / 2);
        bufferedReader.close();
    }
}

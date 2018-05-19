import java.util.regex.Pattern;

/**
 * Created by Aderemi.Okeowo on 5/19/2018.
 */
public class Utils {

    public static boolean isMusicExtension(String currentHref) {

        Pattern pattern = Pattern.compile(".*\\.(mp3|m4a|wma)$");
        return pattern.matcher(currentHref).matches();
    }

    public static boolean hasExtension(String currentHref) {
        Pattern pattern = Pattern.compile(".*\\.\\w{2,4}$");
        return pattern.matcher(currentHref).matches();
    }




}

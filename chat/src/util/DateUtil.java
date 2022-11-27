package util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Ôø¼Ñ±¦
 * @date 2022/11/25 20:12
 */
public class DateUtil {
  public static String getDate() {
    Date date = new Date();
    SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd:hh:mm:ss");
    return dateFormat.format(date);
  }
}

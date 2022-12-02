package dungnt.ptit.myspringsocial.ulti;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;

// Lớp static phải là nest class
public final class DataUntil {

    public static String dateFormatToString(Date date, Constant.DATE_FORMAT_PATTERN pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat();
        sdf.applyPattern(pattern.code);
        return sdf.format(date);
    }

    public static Date stringFormatToDate(String str, Constant.DATE_FORMAT_PATTERN pattern) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat();
        sdf.applyPattern(pattern.code);
        return sdf.parse(str);
    }

    public static boolean checkNullOrEmpty(Object o){
        if(o == null) return true;
        if(o instanceof String) return ((String) o).isEmpty() ;
        if(o instanceof Collection) return ((Collection<?>) o).isEmpty() || ((Collection<?>) o).contains(Collections.singleton(null));
        return o.toString().isEmpty();
    }

}

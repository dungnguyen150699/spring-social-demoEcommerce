package dungnt.ptit.myspringsocial.ulti;

public class Constant {
    public enum DATE_FORMAT_PATTERN{
        YYYY_MM_DD ("yyyy-mm-dd"), DD_MM_YYYY("dd-mm-yyyy");
        public String code;
        DATE_FORMAT_PATTERN(String code){
            this.code = code;
        }
    }

    public static class ROLE{
        public enum TYPE{
            USER("user"),ADMIN("admin");
            public String code;
            TYPE(String code){
                this.code = code;
            }
        }
    }

    public static boolean checkNullOrEmpty(Object input){
        if(input instanceof String){
            return input == null || ((String) input).isEmpty();
        }
        return input == null;
    }
}

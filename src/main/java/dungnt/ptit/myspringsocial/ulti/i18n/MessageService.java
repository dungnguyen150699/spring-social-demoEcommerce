package dungnt.ptit.myspringsocial.ulti.i18n;


public interface MessageService {
    public String getMessage(String code);

    public String getMessage(String code, Object... args);
}

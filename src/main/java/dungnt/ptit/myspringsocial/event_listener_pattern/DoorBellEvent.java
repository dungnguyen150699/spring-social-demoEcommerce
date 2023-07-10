package dungnt.ptit.myspringsocial.event_listener_pattern;

import lombok.Data;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/*
DoorBellEvent phải kế thừa lớp ApplicationEvent của Spring
Như vậy nó mới được coi là một sự kiện hợp lệ.

https://viblo.asia/p/spring-boot-xu-ly-su-kien-voi-ateventlistener-atasync-djeZ1bXglWz
 */
@Getter
public class DoorBellEvent extends ApplicationEvent {
    /*
        Mọi Class kế thừa ApplicationEvent sẽ
        phải gọi Constructor tới lớp cha.
     */

    private String guestName;
    public DoorBellEvent(Object source, String guestName) {
        // Object source là object tham chiếu tới
        // nơi đã phát ra event này!
        super(source);
        this.guestName = guestName;
    }
}

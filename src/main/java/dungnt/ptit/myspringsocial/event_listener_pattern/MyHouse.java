package dungnt.ptit.myspringsocial.event_listener_pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(100)
public class MyHouse implements CommandLineRunner {
    @Autowired
    ApplicationEventPublisher applicationEventPublisher;

    /**
     * Hành động bấm chuông cửa
     */
    public void rangDoorbellBy(String guestName) {
        // Phát ra một sự kiện DoorBellEvent
        // source (Nguồn phát ra) chính là class này
        applicationEventPublisher.publishEvent(new DoorBellEvent(this, guestName));
    }

    /**
     * Callback used to run the bean.
     *
     * @param args incoming main method arguments
     * @throws Exception on error
     */
    @Override
    public void run(String... args) throws Exception {
        System.out.println(Thread.currentThread().getName() + ": Loda đi tới cửa nhà !!!");
        System.out.println(Thread.currentThread().getName() + ": => Loda bấm chuông và khai báo họ tên!");
        // gõ cửa
        rangDoorbellBy("Loda");
        System.out.println(Thread.currentThread().getName() +": Loda quay lưng bỏ đi");
    }
}

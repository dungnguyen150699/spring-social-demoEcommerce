package dungnt.ptit.myspringsocial.mockito;

import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;

// link:  https://stackoverflow.com/questions/16467685/difference-between-mock-and-injectmocks
@RunWith(MockitoJUnitRunner.class) // JUnit 4
//@ExtendWith(MockitoExtension.class) // for JUnit 5
public class ProductServiceTestMocito {
    @Mock  //@Mock annotation is used to create the mock object to be injected
    Player player;

    @InjectMocks //@InjectMocks annotation is used to create and inject the mock object Player above
    Game game;

    @Test
    public void attackWithSwordTest() throws Exception {
        Mockito.when(player.getWeapon()).thenReturn("Sword"); // config behav
        assertEquals("Player attack with: Sword", game.attack());
        verify(player).getWeapon();
    }
}


class Game {

    private Player player;

    public Game(Player player) {
        this.player = player;
    }

    public String attack() {
        return "Player attack with: " + player.getWeapon();
    }

}

class Player {

    private String weapon;

    public Player(String weapon) {
        this.weapon = weapon;
    }

    String getWeapon() {
        return weapon;
    }
}
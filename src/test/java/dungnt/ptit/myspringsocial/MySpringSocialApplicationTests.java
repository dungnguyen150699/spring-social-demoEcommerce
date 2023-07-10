package dungnt.ptit.myspringsocial;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.jupiter.api.Test;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class MySpringSocialApplicationTests {
	static ModelMapper mapper = new ModelMapper();
	@Test
	void contextLoads() {
		TypeMap<Game, GameDTO> propertyMapper = mapper.createTypeMap(Game.class, GameDTO.class);
		Converter<Collection, Integer> collectionToSize = c -> c.getSource().size();
		propertyMapper.addMappings(
				mapper -> mapper.using(collectionToSize).map(Game::getPlayers, GameDTO::setTotalPlayers)
		);

		// when collection to size converter is provided
		Game game = new Game();
		game.addPlayer(new Player(1L, "John"));
		game.addPlayer(new Player(2L, "Bob"));
		GameDTO gameDTO = mapper.map(game, GameDTO.class);

		// then it maps the size to a custom field
		assertEquals(2, gameDTO.getTotalPlayers());
	}

	public static void main(String[] args) {
		TypeMap<Game, GameDTO> propertyMapper = mapper.createTypeMap(Game.class, GameDTO.class);
		Converter<Collection, Integer> collectionToSize = c -> c.getSource().size();
		propertyMapper.addMappings(
				mapper -> mapper.using(collectionToSize).map(Game::getPlayers, GameDTO::setTotalPlayers)
		);

		// when collection to size converter is provided
		Game game = new Game();
		game.addPlayer(new Player(1L, "John"));
		game.addPlayer(new Player(2L, "Bob"));
		GameDTO gameDTO = mapper.map(game, GameDTO.class);

		// then it maps the size to a custom field
		assertEquals(2, gameDTO.getTotalPlayers());
	}

}
@Data
@NoArgsConstructor
 class GameDTO {
	// ...

	private int totalPlayers;

	// constructors, getters and setters
}

@Data
@NoArgsConstructor
class Game {
	// ...

	private List<Player> players = new ArrayList<>();

	public void addPlayer(Player player){
		players.add(player);
	}

	// constructors, getters and setters
}

class Player{
	long id;
	String name;
	public Player(long id, String name){
		this.id = id;
		this.name = name;
	}
}


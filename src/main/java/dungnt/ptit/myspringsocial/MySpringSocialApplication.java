package dungnt.ptit.myspringsocial;

import dungnt.ptit.myspringsocial.config.security.AppProperties;
import dungnt.ptit.myspringsocial.controller.response.enums.AuthProvider;
import dungnt.ptit.myspringsocial.pojo.model.Role;
import dungnt.ptit.myspringsocial.pojo.model.User;
import dungnt.ptit.myspringsocial.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.HashSet;

@SpringBootApplication
@EnableConfigurationProperties(AppProperties.class)
public class MySpringSocialApplication implements CommandLineRunner {
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private UserRepository userRepository;

	public static void main(String[] args) {
		SpringApplication.run(MySpringSocialApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
//		User user = new User()
//				.setEmail("admin150699@gmail.com")
//				.setName("Nguyễn Tiến Dũng")
//				.setPassword(passwordEncoder.encode("1"))
//				.setProvider(AuthProvider.local)
//				.setRoles(Collections.singleton(new Role("ADMIN")));
//		userRepository.save(user);
	}
}

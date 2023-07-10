package dungnt.ptit.myspringsocial;

import dungnt.ptit.myspringsocial.config.security.AppProperties;
import dungnt.ptit.myspringsocial.controller.response.enums.AuthProvider;
import dungnt.ptit.myspringsocial.pojo.model.Role;
import dungnt.ptit.myspringsocial.pojo.model.User;
import dungnt.ptit.myspringsocial.pojo.payload.userPayLoad.UserManagementSearch;
import dungnt.ptit.myspringsocial.pojo.response.projection.ProductProjection;
import dungnt.ptit.myspringsocial.repository.ProductRepository;
import dungnt.ptit.myspringsocial.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import java.util.Collections;
import java.util.HashSet;

@SpringBootApplication
@EnableConfigurationProperties(AppProperties.class)
public class MySpringSocialApplication extends SpringBootServletInitializer implements CommandLineRunner {
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private ApplicationContext context;

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

		UserManagementSearch student = (UserManagementSearch) this.context.getBean("UserManagementSearch");
//		System.out.println("Name : " + student.getName() );
		System.out.println("Email : " + student.getEmail() );

		ProductProjection productProjection = productRepository.getProductByName("Mikasa").get();
		int x = productProjection.getCount();
		System.out.println(productProjection.toString());
	}
}

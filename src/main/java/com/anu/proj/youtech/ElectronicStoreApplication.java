package com.anu.proj.youtech;

import com.anu.proj.youtech.Models.Role;

import com.anu.proj.youtech.Repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableWebMvc
public class ElectronicStoreApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ElectronicStoreApplication.class, args);
	}

	@Autowired
	PasswordEncoder passwordEncoder;
	@Autowired
	private RoleRepository roleRepository;

	@Value("${admin.role.id}")
	private String role_admin_id;

	@Value("${normal.role.id}")
	private String role_normal_id;

	@Override
	public void run(String... args) throws Exception {

		try{

			Role roleAdmin=Role.builder().roleId(role_admin_id).roleName("ROLE_ADMIN").build();
			Role roleNormal=Role.builder().roleId(role_normal_id).roleName("ROLE_NORMAL").build();

			roleRepository.save(roleAdmin);
			roleRepository.save(roleNormal);

		}catch(Exception e)
		{
			e.printStackTrace();
		}

		System.out.println(passwordEncoder.encode("root"));
	}


}

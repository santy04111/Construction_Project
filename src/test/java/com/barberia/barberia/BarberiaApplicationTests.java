package com.barberia.barberia;

import com.barberia.barberia.entities.Usuario;
import com.barberia.barberia.repository.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
class BarberiaApplicationTests {


	@Autowired
	private UsuarioRepository repo;
	@Test
	public void CrearUsuarioTest() {
		Usuario us = new Usuario();
		us.set
	}

}

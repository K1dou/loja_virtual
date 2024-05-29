package com.K1dou.Loja.virtual;

import com.K1dou.Loja.virtual.model.Acesso;
import com.K1dou.Loja.virtual.repository.AcessoRepository;
import com.K1dou.Loja.virtual.service.AcessoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class LojaVirtualApplicationTests {


	@Autowired
	private AcessoService acessoService;

	@Autowired
	private AcessoRepository acessoRepository;



	@Test
	public void testCadastraAcesso() {

		Acesso acesso = new Acesso();
		acesso.setDescricao("ROLE_ADMIN");
		acessoRepository.save(acesso);

	}

}

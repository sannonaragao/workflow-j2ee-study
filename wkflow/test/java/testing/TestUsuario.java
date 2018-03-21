package testing;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class TestUsuario {


	public static void main(String[] args) {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("workflow");
		EntityManager manager = factory.createEntityManager();
		
		EntityTransaction trx = manager.getTransaction();
		trx.begin();
		
/*		Cliente cliente = new Cliente();
		cliente.setNome("JoÃ£o das Couves");
		cliente.setEmail("joao@dascouves.com");
		cliente.setDocumentoReceitaFederal("123.123.123-12");
		cliente.setTipo(TipoPessoa.FISICA);
		
		Endereco endereco = new Endereco();
		endereco.setLogradouro("Rua das Aboboras Vermelhas");
		endereco.setNumero("111");
		endereco.setCidade("UberlÃ¢ndia");
		endereco.setUf("MG");
		endereco.setCep("38400-000");
		endereco.setCliente(cliente);
		
		cliente.getEnderecos().add(endereco);*/
		
//		manager.persist(cliente);
		
		trx.commit();
	} 
}

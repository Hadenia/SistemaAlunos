package br.com.feltex.academicnet;

import br.com.feltex.academicnet.model.Aluno;
import br.com.feltex.academicnet.repository.AlunoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;

import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/* Inicializar em um porta randomica, por defull é 8080 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AcademicnetApplicationTests {

	@LocalServerPort
	private int port;

	/* Manipulação direta no banco de dados */
	@Autowired
	private AlunoRepository alunoRepository;

	/*Chamar a API via Rest chamada Api real*/
	@Autowired
	private TestRestTemplate restTemplate;

	/*Transformar string em objeto e objeto em string  */
	private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

	final Instant dataCadastro = Instant.parse("2021-01-04T10:20:19.0Z");
	final Aluno aluno = new Aluno(123L, "Jose da Silva", "33444-0093", "contato@feltex.com.br", dataCadastro);
	final Aluno alunoParaAlterar = new Aluno(123L, "Jose da Silva Santos", "33444-99999", "santos@feltex.com.br", dataCadastro);

	@Test
	void crudAluno() throws Exception {
		alunoRepository.deleteAll();
		incluirAluno(aluno);
		alterarAluno(alunoParaAlterar); /*Enviou o objeto para banco de dados*/
										/*Quando consultar o objeto, deve ser o mesmo*/
		var alunoConsultado = consultarAluno(alunoParaAlterar.getMatricula());

		/* Verifica se os dois objetos (aluno) são iguais, se os testes estiverem passando*/
		assertEquals(alunoParaAlterar, alunoConsultado);
		deletarAluno(alunoConsultado.getMatricula());

		var listAlunos = listrAlunos();
		assertEquals(0, listAlunos.size());
	}


	@ParameterizedTest
	@NullAndEmptySource
	void dadosInvalidos(String conteudo) throws Exception {
		alunoRepository.deleteAll();
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> httpRequest = new HttpEntity<>(objectMapper.writeValueAsString(conteudo), httpHeaders);
		var responseCode = restTemplate.postForEntity(getURLDoServico(), httpRequest, Aluno.class).getStatusCode();
		assertEquals( HttpStatus.BAD_REQUEST , responseCode);
	}


	private Aluno consultarAluno(long matricula) {
		/*getURLDoServico == http://localhost:" + port + "/alunos/";*/
		return restTemplate.getForObject(getURLDoServico() + matricula, Aluno.class);
	}


	private void deletarAluno(Long matricula) {
		var responseCode = restTemplate.exchange(getURLDoServico() + matricula,
				HttpMethod.DELETE,
				new HttpEntity<>(new HttpHeaders()),
				String.class).getStatusCode();
		/*Testa Status OK, HTTP OK */
		assertEquals(HttpStatus.OK, responseCode);
	}

	private void alterarAluno(Aluno alunoParaAlterar) {

		var responseCode = restTemplate.exchange(getURLDoServico(), HttpMethod.PUT,
				new HttpEntity<>(alunoParaAlterar),
				String.class).getStatusCode();
		assertEquals(HttpStatus.OK, responseCode);
	}

	private void incluirAluno(final Aluno aluno) throws Exception {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> httpRequest = new HttpEntity<>(objectMapper.writeValueAsString(aluno), httpHeaders);
		var responseCode = restTemplate.postForEntity(getURLDoServico(), httpRequest, Aluno.class).getStatusCode();
		assertEquals(HttpStatus.OK, responseCode);
	}

	private List<Aluno> listrAlunos() {
		return restTemplate.exchange(getURLDoServico(),
				HttpMethod.GET,
				new HttpEntity<>(new HttpHeaders()),
				List.class).getBody();
	}

	private String getURLDoServico() {
		return "http://localhost:" + port + "/alunos/";
	}


}

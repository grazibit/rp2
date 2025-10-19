import model.Curso;
import model.StatusCurso;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.CursoService;
import service.JsonDataManager;

import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

public class CursoServiceTest {

    private JsonDataManager dataManager;
    private CursoService cursoService;

    @BeforeEach
    void setUp() {
        this.dataManager = new JsonDataManager();
        this.cursoService = new CursoService(dataManager);
    }

    @Test
    void criarCursoDeveAdicionarNovoCursoAPersistencia() {
        String titulo = "Resolução Problemas";
        String descricao = "materia que blablabla sla";
        String professorID = "u3";
        int tamanhoLista = dataManager.getCursos().size();

        Curso novoCurso = cursoService.criarCurso(titulo, descricao, professorID);
        assertNotNull(novoCurso);

        assertEquals(titulo, novoCurso.getTitulo());
        assertEquals(descricao, novoCurso.getDescricao());

        assertEquals(StatusCurso.PENDENTE_APROVACAO, novoCurso.getStatus());
        assertEquals(tamanhoLista + 1 , dataManager.getCursos().size());
    }

    // REQUISITO: Editar cursos existentes (Professor/Admin)
    @Test
    void editarCursoDeveAtualizarTituloEDescricao() {
        // TODO: Testar a edição de um curso existente (ex: "c1"):
        // 1. Chamar editarCurso() e verificar se o retorno é 'true'.
        // 2. Recuperar o curso na persistência (dataManager).
        // 3. Verificar se o título e a descrição foram atualizados corretamente.
    }

    // REQUISITO: Configurar proteção por PIN de acesso (Professor)
    @Test
    void configurarPinDeveAdicionarPinAoCurso() {
        // TODO: Testar a configuração de PIN em um curso (ex: "c1"):
        // 1. Chamar configurarPin() e verificar se o retorno é 'true'.
        // 2. Recuperar o curso na persistência (dataManager).
        // 3. Verificar se o PIN foi adicionado/configurado corretamente.
    }

    // REQUISITO: Aprovar/rejeitar cursos (Administrador)
    @Test
    void aprovarCursoDeveMudarStatusParaAtivo() {
        // TODO: Testar a aprovação de um curso PENDENTE (ex: "c2"):
        // 1. Chamar aprovarCurso() e verificar se o retorno é 'true'.
        // 2. Recuperar o curso na persistência (dataManager).
        // 3. Verificar se o status do curso mudou para ATIVO.
    }

    @Test
    void rejeitarCursoDeveMudarStatusParaInativo() {
        // TODO: Testar a rejeição de um curso (ex: "c1"):
        // 1. Chamar rejeitarCurso() e verificar se o retorno é 'true'.
        // 2. Recuperar o curso na persistência (dataManager).
        // 3. Verificar se o status do curso mudou para INATIVO.
    }

    // REQUISITO: Visualizar catálogo de cursos disponíveis (Estudante/Comum)
    @Test
    void visualizarCatalogoDeveRetornarApenasCursosAtivos() {
        // TODO: Testar a visualização do catálogo:
        // 1. Chamar visualizarCatalogo().
        // 2. Verificar se a lista retornada contém APENAS cursos com StatusCurso.ATIVO.
        // 3. Verificar se o tamanho da lista está correto (baseado nos dados iniciais).
    }

    // REQUISITO: Ingressar em cursos (com inserção de PIN quando necessário)
    @Test
    void ingressarCursoComPinDeveFuncionarComPinCorreto() {
        // TODO: Testar o ingresso em um curso com PIN (ex: "c2"):
        // 1. Garantir que o curso está ATIVO (configurar se necessário).
        // 2. Chamar ingressarCurso() com o PIN CORRETO.
        // 3. Verificar se o retorno é 'true'.
    }

    @Test
    void ingressarCursoComPinDeveFalharComPinIncorreto() {
        // TODO: Testar o ingresso em um curso com PIN (ex: "c2"):
        // 1. Garantir que o curso está ATIVO (configurar se necessário).
        // 2. Chamar ingressarCurso() com um PIN INCORRETO.
        // 3. Verificar se o retorno é 'false'.
    }

    @Test
    void ingressarCursoSemPinDeveFuncionar() {
        // TODO: Testar o ingresso em um curso SEM PIN (ex: "c1"):
        // 1. Chamar ingressarCurso() passando 'null' ou uma string vazia como PIN.
        // 2. Verificar se o retorno é 'true'.
    }
}
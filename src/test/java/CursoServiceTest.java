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
        assertEquals(tamanhoLista + 1, dataManager.getCursos().size());
    }

    @Test
    void editarCursoDeveAtualizarTituloEDescricao() {
        Curso cursoEditar = cursoService.criarCurso("Resolução Problemas", "materia que blablabla sla", "u3");

        String cursoId = cursoEditar.getId();
        String novoTitulo = "POO";
        String novaDescricao = "Ai sla oq não sei oq la";

        boolean editou = cursoService.editarCurso(cursoId, novoTitulo, novaDescricao);
        assertTrue(editou);

        Optional<Curso> cursoAtualizadoOpt = dataManager.getCursos().stream()
                .filter(c -> c.getId().equals(cursoId)).findFirst();

        assertTrue(cursoAtualizadoOpt.isPresent());

        Curso cursoAtualizado = cursoAtualizadoOpt.get();
        assertEquals(novoTitulo, cursoAtualizado.getTitulo());
        assertEquals(novaDescricao, cursoAtualizado.getDescricao());
    }

    @Test
    void configurarPinDeveAdicionarPinAoCurso() {
        Curso cursoBase = cursoService.criarCurso("teste", "ah teste", "u3");
        String cursoId = cursoBase.getId();
        String pin = "123";

        boolean sucesso = cursoService.configurarPin(cursoId, pin);
        assertTrue(sucesso);

        Curso cursoAtualizado = dataManager.getCursos().stream()
                .filter(c -> c.getId().equals(cursoId)).findFirst().get();

        assertEquals(pin, cursoAtualizado.getPinAcesso());
    }

    @Test
    void aprovarCursoDeveMudarStatusParaAtivo() {
        Curso cursoPendente = cursoService.criarCurso("curso", "curso", "u3");
        String cursoId = cursoPendente.getId();

        boolean sucesso = cursoService.aprovarCurso(cursoId);

        assertTrue(sucesso);

        Curso cursoAtualizado = dataManager.getCursos().stream()
                .filter(c -> c.getId().equals(cursoId)).findFirst().get();

        assertEquals(StatusCurso.ATIVO, cursoAtualizado.getStatus());
    }

    @Test
    void rejeitarCursoDeveMudarStatusParaInativo() {
        Curso cursoPendente = cursoService.criarCurso("teste", "teste", "u3");
        String cursoId = cursoPendente.getId();

        boolean sucesso = cursoService.rejeitarCurso(cursoId);

        assertTrue(sucesso);

        Curso cursoAtualizado = dataManager.getCursos().stream()
                .filter(c -> c.getId().equals(cursoId)).findFirst().get();

        assertEquals(StatusCurso.INATIVO, cursoAtualizado.getStatus());
    }

    @Test
    void visualizarCatalogoDeveRetornarApenasCursosAtivos() {
        Curso cursoAtivoEsperado = cursoService.criarCurso("curso teste", "teste ativo", "u3");
        String idCursoAtivo = cursoAtivoEsperado.getId();

        cursoService.criarCurso("escondido", "pendente", "u3");

        Curso cursoInativo = cursoService.criarCurso("Curso Rejeitado", "Descrição Inativa", "u3");
        cursoService.rejeitarCurso(cursoInativo.getId());

        cursoService.aprovarCurso(idCursoAtivo);

        List<Curso> catalogo = cursoService.visualizarCatalogo();

        assertEquals(3, catalogo.size());

        boolean todosSaoAtivos = catalogo.stream().allMatch(c -> c.getStatus() == StatusCurso.ATIVO);

        assertTrue(todosSaoAtivos);
    }

    @Test
    void ingressarCursoComPinDeveFuncionarComPinCorreto() {
        String pinSecreto = "7890";
        Curso cursoComPin = cursoService.criarCurso("Curso Secreto", "muito secreto", "u3");
        String cursoId = cursoComPin.getId();

        cursoService.aprovarCurso(cursoId);

        boolean pinConfigurado = cursoService.configurarPin(cursoId, pinSecreto);
        assertTrue(pinConfigurado);

        boolean sucesso = cursoService.ingressarCurso(cursoId, pinSecreto);

        assertTrue(sucesso);
    }

    @Test
    void ingressarCursoComPinDeveFalharComPinIncorreto() {
        String pinCerto = "7890";
        String pinErrado = "0000";

        Curso cursoComPin = cursoService.criarCurso("curso protegido", "teste de falha", "u3");
        String cursoId = cursoComPin.getId();

        cursoService.aprovarCurso(cursoId);

        cursoService.configurarPin(cursoId, pinCerto);

        boolean sucesso = cursoService.ingressarCurso(cursoId, pinErrado);

        assertFalse(sucesso);
    }

    @Test
    void ingressarCursoSemPinDeveFuncionar() {
        Curso cursoLivre = cursoService.criarCurso("Curso Livre", "Conteúdo aberto", "u3");
        String cursoId = cursoLivre.getId();

        cursoService.aprovarCurso(cursoId);

        cursoService.configurarPin(cursoId, null);

        boolean sucesso = cursoService.ingressarCurso(cursoId, null);

        assertTrue(sucesso);
    }
}
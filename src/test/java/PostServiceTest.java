import model.Post;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import service.JsonDataManager;
import service.PostService;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

public class PostServiceTest {

    private JsonDataManager dataManager;
    private PostService postService;

    @BeforeEach
    void setUp() {
        this.dataManager = new JsonDataManager();
        this.postService = new PostService(dataManager);
    }

    // REQUISITO: Visualizar posts/artigos (Usuário Comum)
    @Test
    @DisplayName("DeveRetornarTodosOsPostsIniciais")
    void visualizarTodosDeveRetornarTodosOsPostsIniciais() {
        // TODO: Testar a visualização de todos os posts:
        List<Post> posts = postService.visualizarTodos();
        assertEquals(2, posts.size());
    }

    // REQUISITO: Remover posts inadequados (Administrador)
    @Test
    @DisplayName("DeveRemoverPostExistente")
    void removerPostDeveRemoverPostExistente() {
        int tamanho = postService.visualizarTodos().size();
        boolean resultado = postService.removerPost("p1");
        assertTrue(resultado, "A remoção de um post existente deveria retornar true.");
        int tamanhoFinal = postService.visualizarTodos().size();
        assertEquals(tamanho - 1, tamanhoFinal);

        //
        //TODO RICHARD
        // 4. Garantir que o post "p1" não existe mais na persistência (dataManager).


    }

    @Test
    @DisplayName("removerPostInexistenteDeveRetornarFalso")
    void removerPostInexistenteDeveRetornarFalso() {
        boolean resultado = postService.removerPost("p999");
        assertFalse(resultado);

    }

    // REQUISITO: Curtir/descurtir posts (Usuário Comum)
    @Test
    @DisplayName("DeveIncrementarContadorDeCurtidas")
    void curtirPostDeveIncrementarContadorDeCurtidas() {
        // TODO RICHARD
        //  Testar a função de curtir post (ex: "p2"):
        // 1. Obter o número de curtidas iniciais do post.


        // 2. Chamar curtirPost("p2") e verificar se retorna 'true'.


        // 3. Verificar se o contador de curtidas do post na persistência (dataManager) aumentou em 1.

    }

    // REQUISITO: Filtrar artigos por temas/tags (Usuário Comum)
    @Test
    @DisplayName("DeveRetornarPostsComTagCorrespondente")
    void filtrarPorTagDeveRetornarPostsComTagCorrespondente() {
        List<Post> postsFiltrados = postService.filtrarPorTag("Java");
        assertEquals(1, postsFiltrados.size());
        assertEquals("p1", postsFiltrados.getFirst().getId());

    }

    @Test
    @DisplayName("DeveIgnorarCaseSensitivity")
    void filtrarPorTagDeveIgnorarCaseSensitivity() {
        List<Post> postsFiltrados = postService.filtrarPorTag("java");
        assertEquals(1, postsFiltrados.size());
        assertEquals("p1", postsFiltrados.getFirst().getId());

    }

    @Test
    @DisplayName("DeveRetornarListaVazia")
    void filtrarPorTagInexistenteDeveRetornarListaVazia() {
        List<Post> postsFiltrados = postService.filtrarPorTag("python");
        assertTrue(postsFiltrados.isEmpty());


    }

    // REQUISITO: Visualizar métricas de engajamento (Administrador)
    @Test
    @DisplayName("DeveRetornarSomaCorreta")
    void getCurtidasTotaisDeveRetornarSomaCorreta() {
        int curtidas = postService.getCurtidasTotais();
        assertEquals(2, curtidas);
        postService.curtirPost("p2");
        int curtidasNovas = postService.getCurtidasTotais();
        assertEquals(3, curtidasNovas);

    }
}
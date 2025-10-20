import model.Post;
import org.junit.jupiter.api.BeforeEach;
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
    void visualizarTodosDeveRetornarTodosOsPostsIniciais() {
        // TODO: Testar a visualização de todos os posts:
        // 1. Chamar visualizarTodos().
        List<Post> posts = postService.visualizarTodos();

        // 2. Verificar se a lista retornada tem o tamanho esperado (2 nos dados iniciais).
        assertEquals(2, posts.size());
    }

    // REQUISITO: Remover posts inadequados (Administrador)
    @Test
    void removerPostDeveRemoverPostExistente() {
        // TODO: Testar a remoção de um post existente (ex: "p1"):
        // 1. Armazenar o tamanho inicial da lista de posts.
        int tamanho = postService.visualizarTodos().size();

        // 2. Chamar removerPost("p1") e verificar se retorna 'true'.
        boolean resultado = postService.removerPost("p1");
        assertTrue(resultado,"A remoção de um post existente deveria retornar true.");

        // 3. Verificar se o tamanho da lista diminuiu em 1.
        int tamanhoFinal = postService.visualizarTodos().size();
        assertEquals(tamanho-1, tamanhoFinal);

        //TODO
        // 4. Garantir que o post "p1" não existe mais na persistência (dataManager).


    }

    @Test
    void removerPostInexistenteDeveRetornarFalso() {
        // TODO: Testar a remoção de um post que não existe:
        // 1. Chamar removerPost() com um ID inexistente (ex: "p999").
        boolean resultado = postService.removerPost("p999");

        // 2. Verificar se o retorno é 'false'.
        assertFalse(resultado);

    }

    // REQUISITO: Curtir/descurtir posts (Usuário Comum)
    @Test
    void curtirPostDeveIncrementarContadorDeCurtidas() {
        // TODO: Testar a função de curtir post (ex: "p2"):
        // 1. Obter o número de curtidas iniciais do post.


        // 2. Chamar curtirPost("p2") e verificar se retorna 'true'.


        // 3. Verificar se o contador de curtidas do post na persistência (dataManager) aumentou em 1.



    }

    // REQUISITO: Filtrar artigos por temas/tags (Usuário Comum)
    @Test
    void filtrarPorTagDeveRetornarPostsComTagCorrespondente() {
        // TODO: Testar a filtragem por uma tag existente (ex: "Java"):
        // 1. Chamar filtrarPorTag() com a tag.
        List<Post> postsFiltrados = postService.filtrarPorTag("Java");

        // 2. Verificar se a lista retornada tem o tamanho esperado (1).
        assertEquals(1,postsFiltrados.size());

        // 3. Verificar se o post retornado é o correto (ex: "Novidades do Java 21").
        assertEquals("p1", postsFiltrados.getFirst().getId());

    }

    @Test
    void filtrarPorTagDeveIgnorarCaseSensitivity() {
        // TODO: Testar a filtragem com uma tag existente, usando case insensível (ex: "java"):
        // 1. Chamar filtrarPorTag() com a tag em minúsculas ou maiúsculas.
        List<Post> postsFiltrados = postService.filtrarPorTag("java");

        // 2. Verificar se o resultado é o mesmo do teste anterior.
        assertEquals(1,postsFiltrados.size());
        assertEquals("p1",postsFiltrados.getFirst().getId());
    }

    @Test
    void filtrarPorTagInexistenteDeveRetornarListaVazia() {
        // TODO: Testar a filtragem por uma tag que não existe (ex: "Python"):
        // 1. Chamar filtrarPorTag() com a tag inexistente.
        List<Post> postsFiltrados = postService.filtrarPorTag("python");


        // 2. Verificar se a lista retornada está vazia (isEmpty() == true).
        assertTrue(postsFiltrados.isEmpty());

    }

    // REQUISITO: Visualizar métricas de engajamento (Administrador)
    @Test
    void getCurtidasTotaisDeveRetornarSomaCorreta() {
        // TODO: Testar o cálculo do total de curtidas:
        // 1. Chamar getCurtidasTotais() e verificar se o total inicial é correto (2).
        int curtidas = postService.getCurtidasTotais();
        assertEquals(2,curtidas);

        // 2. Simular uma nova interação (ex: curtirPost("p2")).
        postService.curtirPost("p2");


        // 3. Chamar getCurtidasTotais() novamente e verificar se o novo total (3) está correto.
        int curtidasNovas = postService.getCurtidasTotais();
        assertEquals(3,curtidasNovas);


    }
}
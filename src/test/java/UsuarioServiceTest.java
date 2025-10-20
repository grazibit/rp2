import model.PapelUsuario;
import model.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.JsonDataManager;
import service.UsuarioService;

import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

public class UsuarioServiceTest {

    private JsonDataManager dataManager;
    private UsuarioService usuarioService;

    // Configuração executada antes de cada teste
    @BeforeEach
    void setUp() {
        // Garante que o estado inicial (dados simulados) seja redefinido para cada teste
        this.dataManager = new JsonDataManager();
        this.usuarioService = new UsuarioService(dataManager);
    }

    @Test
    void visualizarTodosDeveRetornarQuatroUsuariosIniciais() {
        List<Usuario> usuarios = usuarioService.visualizarTodos();
        assertEquals(4, usuarios.size());
    }

    @Test
    void alterarNivelAcessoDeveMudarOModeloDeUsuario() {
        String userId = "u4";
        PapelUsuario novoPapel = PapelUsuario.ESTUDANTE;
        boolean resultado = usuarioService.alterarNivelAcesso(userId, novoPapel);
        assertTrue(resultado);

        Optional<Usuario> usuarioAtualizado = dataManager.getUsuarios().stream()
                .filter(u -> u.getId().equals(userId))
                .findFirst();

        assertEquals(novoPapel, usuarioAtualizado.get().getPapel());
    }

    @Test
    void alterarNivelAcessoInexistenteDeveFalhar() {
        String userId = "u9";
        PapelUsuario novoPapel = PapelUsuario.ADMINISTRADOR;

        boolean resultado = usuarioService.alterarNivelAcesso(userId, novoPapel);

        assertFalse(resultado);
    }

    @Test
    void buscarUsuariosDeveFiltrarPorNome() {
        List<Usuario> resultado = usuarioService.buscarUsuarios("ana", null);
        assertEquals(1, resultado.size());
        assertEquals("Ana Admin",resultado.get(0).getNome());
    }

    @Test
    void buscarUsuariosDeveFiltrarPorNomeEPapel(){
        List<Usuario> resultado = usuarioService.buscarUsuarios("ana", PapelUsuario.ADMINISTRADOR);
        assertEquals(1,resultado.size());

        assertEquals("Ana Admin", resultado.get(0).getNome());
        assertEquals(PapelUsuario.ADMINISTRADOR,resultado.get(0).getPapel());
    }

    @Test
    void buscarUsuariosDeveFiltrarPorEmail() {
        // TODO: Testar a busca de usuários por parte do email (ex: "@codefolio.com"):
        // 1. Chamar buscarUsuarios("@codefolio.com", null).
        // 2. Verificar se a lista retornada tem o tamanho correto (4).
    }

    @Test
    void buscarUsuariosDeveFiltrarPorPapel() {
        // TODO: Testar a busca de usuários por PapelUsuario (ex: PROFESSOR):
        // 1. Chamar buscarUsuarios(null, PapelUsuario.PROFESSOR).
        // 2. Verificar se a lista retornada tem o tamanho correto (1) e o papel do usuário está correto.
    }

    // REQUISITO: Ordenar usuários por diferentes critérios (Admin)
    @Test
    void ordenarUsuariosPorNomeDeveFuncionar() {
        // TODO: Testar a ordenação de usuários por nome:
        // 1. Chamar ordenarUsuarios("nome").
        // 2. Verificar se os primeiros elementos da lista estão na ordem alfabética esperada (ex: "Ana Admin", "Bruno Professor").
    }

    @Test
    void ordenarUsuariosPorPapelDeveFuncionar() {
        // TODO: Testar a ordenação de usuários por papel:
        // 1. Chamar ordenarUsuarios("papel").
        // 2. Verificar se os primeiros elementos da lista estão na ordem de papel esperada (ex: USUARIO_COMUM, ESTUDANTE).
    }

    // REQUISITO: Editar informações pessoais (Usuário Comum)
    @Test
    void editarPerfilDeveMudarONomeDoUsuario() {
        // TODO: Testar a edição de informações de perfil (ex: mudar o nome do "u4"):
        // 1. Chamar editarPerfil() e verificar se retorna 'true'.
        // 2. Recuperar o usuário na persistência (dataManager).
        // 3. Verificar se o nome do usuário foi atualizado corretamente.
    }

    @Test
    void buscarUsuariosDeveRetornarListaVaziaParaTermoInexistente() {
        List<Usuario> resultado = usuarioService.buscarUsuarios("abc", null);
        assertTrue(resultado.isEmpty());
    }

    @Test
    void editarPerfilInexistenteDeveFalhar() {
        String userID = "u9";
        String novoNome = "Teste";
        boolean resultado = usuarioService.editarPerfil(userID, novoNome);
        assertFalse(resultado);
    }
}
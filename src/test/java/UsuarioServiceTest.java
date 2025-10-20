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
    void buscarUsuariosDeveFiltrarPorEmail() {
        List<Usuario> resultado = usuarioService.buscarUsuarios("@codefolio.com", null);

        assertNotNull(resultado, "A lista de usuarios nao pode ser nula");
        assertEquals(4, resultado.size(), "Retorna 4 email de usuarios com @codefolio.com");

        for (Usuario usuario : resultado) {
            assertTrue(usuario.getEmail().contains("@codefolio.com"), "O email do usuario deve ter @codefolio.com como parte");
        }
    }

    @Test
    void buscarUsuariosDeveFiltrarPorPapel() {
        List<Usuario> resultado = usuarioService.buscarUsuarios(null, PapelUsuario.PROFESSOR);

        assertNotNull(resultado, "A lista de usuarios nao pode ser nula");
        assertEquals(1, resultado.size(), "Tem que ter apenas 1 usuario com papel de professor");

        Usuario usuario = resultado.get(0);
        assertEquals(PapelUsuario.PROFESSOR, usuario.getPapel(), "Tem que retornar o papel do usuario como PROFESSOR");
    }

    @Test
    void ordenarUsuariosPorNomeDeveFuncionar() {
        List<Usuario> resultado = usuarioService.ordenarUsuarios("nome");

        assertNotNull(resultado, "A lista ordenada não deve ser nula");
        assumeTrue(resultado.size() >= 2, "É necessário ter pelo menos 2 usuários para testar");

        String primeiro = resultado.get(0).getNome();
        String segundo = resultado.get(1).getNome();

        assertTrue(primeiro.compareToIgnoreCase(segundo) <= 0, "Os nomes devem estar em ordem alfabetica");

        for (int i = 0; i < resultado.size() - 1; i++) {
            String atual = resultado.get(i).getNome();
            String proximo = resultado.get(i + 1).getNome();
            assertTrue(atual.compareToIgnoreCase(proximo) <= 0, "A lista não está totalmente ordenada por nome");
        }
    }

    @Test
    void ordenarUsuariosPorPapelDeveFuncionar() {
        List<Usuario> resultado = usuarioService.ordenarUsuarios("papel");

        assertNotNull(resultado, "A lista não deve ser nula");
        assumeTrue(resultado.size() >= 2, "É necessário ter ao menos 2 usuários para testar ordenação");

        for (int i = 0; i < resultado.size() - 1; i++) {
            PapelUsuario atual = resultado.get(i).getPapel();
            PapelUsuario proximo = resultado.get(i + 1).getPapel();
            assertTrue(atual.ordinal() <= proximo.ordinal(),
                    "Os papéis devem estar ordenados conforme a ordem do enum PapelUsuario");
        }
    }

    @Test
    void editarPerfilDeveMudarONomeDoUsuario() {
        Optional<Usuario> usuarioAntes = dataManager.getUsuarios().stream()
                .filter(u -> u.getId().equals("u4"))
                .findFirst();

        assumeTrue(usuarioAntes.isPresent(), "Usuário 'u4' deve existir na base de dados inicial");

        boolean resultado = usuarioService.editarPerfil("u4", "Novo Nome de Teste");

        assertTrue(resultado, "A edição de perfil deve retornar true");

        Optional<Usuario> usuarioDepois = dataManager.getUsuarios().stream()
                .filter(u -> u.getId().equals("u4"))
                .findFirst();

        assertTrue(usuarioDepois.isPresent(), "Usuário 'u4' deve existir após edição");
        assertEquals("Novo Nome de Teste", usuarioDepois.get().getNome(),
                "O nome do usuário deve ter sido atualizado corretamente");
    }

    @Test
    void buscarUsuariosPorNomeeEmailDeveFuncionar() {
        List<Usuario> resultado = usuarioService.buscarUsuarios("milena","@codefolio.com");

        assertNotNull(resultado, "A lista de usuários não deve ser nula");
        assertFalse(resultado.isEmpty(), "Deve retornar pelo menos um usuário que combine nome e email");

        for (Usuario usuario : resultado) {
            assertTrue(usuario.getNome().toLowerCase().contains("milena"),
                    "O nome do usuário deve conter 'milena'");
            assertTrue(usuario.getEmail().toLowerCase().contains("@codefolio.com"),
                    "O e-mail do usuário deve conter '@codefolio.com'");
        }
    }

    @Test
    void recuperarUsuarioPorIdDeveRetornarUsuarioCorreto() {
        List<Usuario> resultado = dataManager.getUsuarios().stream()
                .filter(u -> u.getId().equalsIgnoreCase("u1"))
                .toList();

        assertNotNull(resultado, "A lista de usuários não deve ser nula");
        assertEquals(1, resultado.size(), "Deve retornar exatamente um usuário com o ID informado");

        Usuario usuario = resultado.get(0);
        assertEquals("u1", usuario.getId(), "O ID do usuário deve ser 'u1'");
        assertNotNull(usuario.getNome(), "O nome do usuário não deve ser nulo");
        assertNotNull(usuario.getEmail(), "O e-mail do usuário não deve ser nulo");
    }
}
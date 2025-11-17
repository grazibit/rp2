package sua.pasta.rp2.testes; // Use o pacote correto do seu projeto

import model.PapelUsuario;
import service.CursoService;
import service.JsonDataManager;
import service.PostService;
import service.UsuarioService;

// Classe MainApp declarada explicitamente
public class MainApp {

    // Método main tradicional (público, estático, void, com String[] args)
    public static void main(String[] args) {
        // 1. Inicializa o Data Manager com dados simulados
        JsonDataManager dataManager = new JsonDataManager();

        // 2. Inicializa os Serviços
        UsuarioService usuarioService = new UsuarioService(dataManager);
        CursoService cursoService = new CursoService(dataManager);
        PostService postService = new PostService(dataManager);

        // ATENÇÃO: Substitua 'IO.println' pelo método de saída correto (System.out.println)
        System.out.println("--- DADOS INICIAIS (SIMULAÇÃO JSON) ---");
        System.out.println("Usuários:\n" + dataManager.exportUsuariosToJson());
        System.out.println("\nCursos:\n" + dataManager.exportCursosToJson());
        System.out.println("\n----------------------------------------\n");

        // Exemplo de Requisito 1: Gerenciamento de Usuários (ADMINISTRADOR)
        System.out.println("ADMIN: Alterando papel de Carla (u3) para PROFESSOR...");
        usuarioService.alterarNivelAcesso("u3", PapelUsuario.PROFESSOR);
        System.out.println("Busca por Professores:");
        usuarioService.buscarUsuarios(null, PapelUsuario.PROFESSOR)
                .forEach(u -> System.out.println("- " + u.getNome() + " | " + u.getPapel()));

        // Exemplo de Requisito 2: Gerenciamento de Cursos (ADMINISTRADOR)
        System.out.println("\nADMIN: Aprovando curso c2...");
        cursoService.aprovarCurso("c2");
        System.out.println("Catálogo de Cursos Ativos:");
        cursoService.visualizarCatalogo()
                .forEach(c -> System.out.println("- " + c.getTitulo() + " (Status: " + c.getStatus() + ")"));

        // Exemplo de Requisito 3: Acesso a Cursos (ESTUDANTE)
        System.out.println("\nESTUDANTE: Tentando ingressar no curso c2 (com PIN 1234)...");
        boolean ingressou = cursoService.ingressarCurso("c2", "1234");
        System.out.println("Ingresso bem-sucedido? " + ingressou);

        // Exemplo de Requisito 4: Interação com Posts (USUÁRIO COMUM)
        System.out.println("\nCOMUM: Filtrando posts por tag 'Java'...");
        postService.filtrarPorTag("Java")
                .forEach(p -> System.out.println("- " + p.getTitulo() + " (Curtidas: " + p.getCurtidas() + ")"));

        System.out.println("\n--- SIMULAÇÃO JSON APÓS ALTERAÇÕES ---");
        System.out.println("Usuários:\n" + dataManager.exportUsuariosToJson());
    }
}
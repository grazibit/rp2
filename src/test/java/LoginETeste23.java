import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.JavascriptExecutor;
import io.github.bonigarcia.wdm.WebDriverManager;
import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class LoginETeste23 {

    private WebDriver driver;
    private WebDriverWait wait;
    private final Duration TIMEOUT = Duration.ofSeconds(15);
    private final String URL_BASE = "https://testes.codefolio.com.br/";
    private JavascriptExecutor js;

    private final String FIREBASE_KEY = "firebase:authUser:AIzaSyARn2qVrSSndFu9JSo5mexrQCMxmORZzCg:[DEFAULT]";
    private final String FIREBASE_VALUE = "{\"apiKey\":\"AIzaSyARn2qVrSSndFu9JSo5mexrQCMxmORZzCg\",\"appName\":\"[DEFAULT]\",\"createdAt\":\"1762135293795\",\"displayName\":\"Richard Lopes do Amaral\",\"email\":\"richardamaral.aluno@unipampa.edu.br\",\"emailVerified\":true,\"isAnonymous\":false,\"lastLoginAt\":\"1763363009521\",\"phoneNumber\":null,\"photoURL\":\"https://lh3.googleusercontent.com/a/ACg8ocKAXty-y5Uv6Bu5KmJ0FYuH7b9InieOMwH61AHfmtFAsElhwG4H=s96-c\",\"providerData\":[{\"providerId\":\"google.com\",\"uid\":\"114097117142889543538\",\"displayName\":\"Richard Lopes do Amaral\",\"email\":\"richardamaral.aluno@unipampa.edu.br\",\"phoneNumber\":null,\"photoURL\":\"https://lh3.googleusercontent.com/a/ACg8ocKAXty-y5Uv6Bu5KMj0FYuH7b9InieOMwH61AHfmtFAsElhWG4H=s96-c\",\"isPrimaryMember\":true,\"federatedId\":\"114097117142889543538\",\"rawId\":\"114097117142889543538\",\"screenName\":null}],\"stsTokenManager\":{\"accessToken\":\"eyJhbGciOiJSUzI1NiIsImtpZCI6IjQ1YTZjMGMyYjgwMDcxN2EzNGQ1Y2JiYmYzOWI4NGI2NzYxMjgyNjUiLCJ0eXAiOiJKV1QifQ.eyJuYW1lIjoiUmljaGFyZCBMb3BlcyBkbyBBbWFyYWwiLCJwaWN0dXJlIjoiaHR0cHM6Ly9saDMuZ29vZ2xldXNlcmNvbnRlbnQuY29tL2EvQUNnOG9jS0FYdHkteTVVdjZCdTVLbUowRll1SDdiOUluaWVPTXdINjFBSGZtdEZBc0VsaHdHNEg9czk2LWMiLCJpc3MiOiJodHRwczovL3NlY3VyZXRva2VuLmdvb2dsZS5jb20vcmVhY3QtbmEtcHJhdGljYSIsImF1ZCI6InJlYWN0LW5hLXByYXRpY2EiLCJhdXRoX3RpbWUiOjE3NjIxOTA5MjgsInVzZXJfaWQiOiJuRHh4Z1puSHVmZ2hkQzVGR0JUeTZlNjIxYnMxIiwic3ViIjoibkR4eGdabkh1ZmdoZEM1RkdCVHk2ZTYyMWJzMSIsImlhdCI6MTc2MzM4MDkwNywiZXhwIjoxNzYzMzg0NTA3LCJlbWFpbCI6InJpY2hhcmRhbWFyYWwuYWx1bm9AdW5pcGFtcGEuZWR1LmJyIiwiZW1haWxfdmVyaWZpZWQiOnRydWUsImZpcmViYXNlIjp7ImlkZW50aXRpZXMiOnsiZ29vZ2xlLmNvbSI6WyIxMTQwOTcxMTcxNDI4ODk1NDM1MzgiXSwiZW1haWwiOlsicmljaGFyZGFtYXJhbC5hbHVub0B1bmlwYW1wYS5lZHUuYnIiXX0sInNpZ25faW5fcHJvdmlkZXIiOiJnb29nbGUuY29tIn19.BELsi0DuA1e6neJImmQ_kd1tmOuq-aG0ebvtJpKl0Bi2FScdPmkZ4OUHoLHLQTzRazx9MqEt_pY8ohIuc3ZA5Wp3pknyvwR0AEx-Vjmnm0DkXhLAaxZcfptaIsvChGdpzvPXeWAJe-Pv9jr3VcpOgEItJU924wYGKdUoz6SoRISV8iRHg_Rh4qYiGe0aHBMlgpxZeQrEdakNPwaTxPynK2Yo8D0tZj281XBuvPi2GWdMhNVoWYWKR-4Pm0wWAaPsvvVqekGaOPXrhuxQSyV_mcg9Yix356z0tFZfPyV3hjva_qJobVpBmsuaJrAcINSC7dGtx_vUN9TywBkBtfUnBw\",\"expirationTime\":1763384503787,\"refreshToken\":\"AMf-vByB7rxEQtw49WU8msQD7U8hmW4ZcF2ld3wcxbVM8rFXQXIxAEeeiL99x9Jq2AD6LqhF5e3UEfdklZny1pO3JcAJAxd0hUPkrVI5JKqpJEbljNAsIwVqxQZcpxP_M3xJYeGgxVBcWZrc2y8fEyZmMxIa-qUY1d2DTPlUFMR9xBWXZHTjwvloSP1d8uo4xpbRSa15gKHJMTd8R_OG10DG7bY8rQb22ZxaAtsmG1KKz0VQvKWMJwC5rZ7qscs3raXdtpHsVPnSAh1BVNhwSN4pqTj-o2gyRS1KI691VrDTEIhKQZVHm933DTrIIzGhJs-H2lt3OEgjbH2_YV-2dKo0CK8XSbX6gxdmG19pvfk-e28iXlGChB7lNi_LhHTpA7wQa_9ZvLYJEcZ39IFakKrZSlY2U5vDdraLvvG2dk-F7dPWk6_x5kA9cxmlQjrIZkS_F4YbmvbLS0pQSabSj-lhhL8FjiQo-Q\"},\"tenantId\":null,\"uid\":\"nDxxgZnHufghdC5FGBTy6e621bs1\",\"_redirectEventId\":null}";

    @BeforeEach
    public void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();

        driver.manage().timeouts().scriptTimeout(Duration.ofSeconds(30));
        wait = new WebDriverWait(driver, TIMEOUT);
        js = (JavascriptExecutor) driver;

        driver.get(URL_BASE);

        try {
            js.executeScript("window.localStorage.setItem(arguments[0], arguments[1]);", FIREBASE_KEY, FIREBASE_VALUE);
        } catch (Exception e) {
            throw new RuntimeException("Falha no setup do Local Storage para login", e);
        }
        driver.navigate().refresh();
    }

    @Test
    public void testRF23EdicaoDeAvaliacao() {
        navegarParaTelaDeAvaliacoes();
        editarAvaliacao();
    }

    private void verificarLoginEAbrirMenu() {
        WebElement profileButton = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.cssSelector("button[aria-label='Configurações da Conta']")
        ));
        js.executeScript("arguments[0].click();", profileButton);

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//li[normalize-space()='Gerenciamento de Cursos']")
        ));
    }

    private void clicarGerenciamentoCursos() {
        try {
            WebElement gerenciamentoButton = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//li[normalize-space()='Gerenciamento de Cursos']")
            ));
            gerenciamentoButton.click();
            wait.until(ExpectedConditions.urlContains("/manage-courses"));
        } catch (Exception e) {
            assertTrue(false, "Falha ao clicar em Gerenciamento de Cursos.");
        }
    }

    private void navegarParaTelaDeAvaliacoes() {
        verificarLoginEAbrirMenu();
        clicarGerenciamentoCursos();

        System.out.println("Acessando o curso através do botão 'Gerenciar Curso'...");
        try {
            String xpathBotaoGerenciarCurso = "//button[contains(normalize-space(), 'Gerenciar Curso')]";

            WebElement botaoGerenciarCurso = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath(xpathBotaoGerenciarCurso)
            ));
            botaoGerenciarCurso.click();

            wait.until(ExpectedConditions.urlContains("/adm-cursos"));
            System.out.println("✅ Curso acessado com sucesso.");

            String xpathAvaliacoes = "//button[normalize-space()='Avaliações' or .//svg[@data-testid='StarIcon']]";

            WebElement avaliacoesButton = wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.xpath(xpathAvaliacoes)
            ));

            js.executeScript("arguments[0].scrollIntoView(true);", avaliacoesButton);
            js.executeScript("arguments[0].click();", avaliacoesButton);
            System.out.println("Clicou no botão 'Avaliações'.");

            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//table")));

            js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
            System.out.println("✅ Acesso à aba 'Avaliações' concluído.");

        } catch (Exception e) {
            assertTrue(false, "Falha na navegação até a aba Avaliações. Exceção: " + e.getMessage());
        }
    }

    private void editarAvaliacao() {
        System.out.println("\n--- RF23: Teste de Edição de Avaliação ---");
        try {
            String xpathBotaoEditar = "//tr[./td[contains(text(), 'ID-2345')]]/td[3]//button";

            WebElement editarButton = wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.xpath(xpathBotaoEditar)
            ));


            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].click();", editarButton);

            js.executeScript("arguments[0].scrollIntoView(true);", editarButton);
            js.executeScript("arguments[0].click();", editarButton);
            System.out.println("Clicou em 'Editar'.");

            WebElement notaInput = wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.xpath("//input[@id='nota' or contains(@name, 'nota') or contains(@placeholder, 'Nota')]")
            ));
            notaInput.clear();
            notaInput.sendKeys("9");
            System.out.println("Nota alterada para 9.");

            WebElement salvarButton = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//button[normalize-space()='Salvar Alterações']")
            ));
            salvarButton.click();
            System.out.println("Submeteu a alteração.");

            wait.until(ExpectedConditions.invisibilityOf(salvarButton));

            WebElement novaNotaNaTabela = wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.xpath("//td[normalize-space()='9']")
            ));
            assertTrue(novaNotaNaTabela.isDisplayed(), "A nova nota (9) não foi exibida. A edição falhou.");
            System.out.println("✅ RF23 Sucesso: Avaliação editada e nota '9' validada.");

        } catch (Exception e) {
            assertTrue(false, "Falha no teste de Edição (RF23). Exceção: " + e.getMessage());
        }
    }

    @AfterEach
    public void teardown() {
        if (driver != null) {
            // driver.quit();
        }
    }
}

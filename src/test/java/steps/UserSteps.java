package steps;

import POJO.BodyUser;
import com.github.javafaker.Faker;
import io.cucumber.java.pt.*;
import io.restassured.response.Response;
import services.UserService;

import java.util.Locale;

import static org.junit.Assert.assertEquals;

public class UserSteps {
    private UserService userService;
    private BodyUser bodyUser;
    private Response response;
    private Faker faker;
    private String id;
    private String name;
    private String email;

    public UserSteps(){
        this.userService =  new UserService();
        this.faker = new Faker(new Locale("pt-BR"));
        this.name = faker.harryPotter().character();
        this.email = name.toLowerCase(Locale.ROOT).replaceAll("\\s+", "").replaceAll(",", "") + ".email@email.com";
    }
    @Quando("crio um novo usuario com dados válidos")
    public void crioUmNovoUsuarioComDadoValidos() {
        bodyUser = BodyUser.builder().nome(name).email(email).build();
        response = userService.postUser(bodyUser);
        id = response.jsonPath().getString("_id");
        assertEquals(201, response.getStatusCode());
    }

    @Então("deve retornar mensagem {string}")
    public void deveRetornarMensagem(String message) {
        assertEquals(message, response.jsonPath().getString("message"));
    }

    @E("deve constar na lista de usuários cadastrados")
    public void deveConstarNaListaDeUsuáriosCadastrados() {
        response = userService.getUserById(id);
        response.then().log().all();
        assertEquals(200, response.getStatusCode());
        assertEquals(name, response.jsonPath().getString("nome"));
        assertEquals(email, response.jsonPath().getString("email"));
        assertEquals("senha123", response.jsonPath().getString("password"));
        assertEquals("false", response.jsonPath().getString("administrador"));
    }

    @Quando("crio um novo administrador com dados válidos")
    public void crioUmNovoAdministradorComDadosVálidos() {
        bodyUser = BodyUser.builder().nome(name).email(email).administrador("true").build();
        response = userService.postUser(bodyUser);
        id = response.jsonPath().getString("_id");
        assertEquals(201, response.getStatusCode());
    }

    @E("deve constar novo adminitador na lista de usuários cadastrados")
    public void deveConstarNovoAdminitadorNaListaDeUsuáriosCadastrados() {
        response = userService.getUserById(id);
        response.then().log().all();
        assertEquals(200, response.getStatusCode());
        assertEquals(name, response.jsonPath().getString("nome"));
        assertEquals(email, response.jsonPath().getString("email"));
        assertEquals("senha123", response.jsonPath().getString("password"));
        assertEquals("true", response.jsonPath().getString("administrador"));
    }

    @Quando("insiro um email ja cadastrado")
    public void insiroUmEmailJaCadastrado() {
    }

    @Então("Deve retornar erro {string}")
    public void deveRetornarErro(String arg0) {
    }

    @Quando("insiro um {string} inválido")
    public void insiroUmInválido(String arg0) {
    }

    @Quando("preencho o campo nome com {string}")
    public void preenchoOCampoNomeCom(String arg0) {
    }

    @E("preencho o campo email com {string}")
    public void preenchoOCampoEmailCom(String arg0) {
    }

    @E("preencho o campo password com {string}")
    public void preenchoOCampoPasswordCom(String arg0) {
    }

    @E("preencho o campo  administrador com {string}")
    public void preenchoOCampoAdministradorCom(String arg0) {
    }

}

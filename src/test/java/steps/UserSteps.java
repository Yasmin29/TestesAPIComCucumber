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
        bodyUser = BodyUser.builder().nome(name).email(email).build();
        userService.postUser(bodyUser);
        response = userService.postUser(bodyUser);
        assertEquals(400, response.getStatusCode());

    }

    @Então("Deve retornar erro {string}")
    public void deveRetornarErro(String message) {
        assertEquals(message, response.jsonPath().getString("message"));
    }

    @Quando("insiro um {string} inválido")
    public void insiroUmInválido(String email) {
        bodyUser = BodyUser.builder().nome(name).email(email).build();
        response = userService.postUser(bodyUser);
        assertEquals(400, response.getStatusCode());
    }

    @Então("Retorna mensagem {string}")
    public void retornaMensagem(String message) {
        assertEquals(message, response.jsonPath().getString("email"));
        response.then().log().all();
    }


    @Dado("que preencho os campos com {string}, {string}, {string},{string}")
    public void quePreenchoOsCamposCom(String nome, String email, String password, String administrador) {
        bodyUser = BodyUser.builder().nome(nome).email(email).password(password).administrador(administrador).build();
        response = userService.postUser(bodyUser);
        assertEquals(400, response.getStatusCode());
    }

    @Então("retorna erro 400")
    public void retorna() {
        assertEquals(400, response.getStatusCode());
        response.then().log().all();
    }
}

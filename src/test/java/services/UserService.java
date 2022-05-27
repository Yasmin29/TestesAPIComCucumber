package services;

import POJO.BodyUser;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class UserService {
    private static final String endpoint = "https://serverest.dev/usuarios";
    private static final String slash = "/";

    public Response getUser(){
        return given()
                .contentType("application/json")
                .when()
                .get(endpoint);
    }

    public Response getUserById(String id){
        return given()
                .contentType("application/json")
                .when()
                .get(endpoint + slash + id);
    }

        public Response postUser (BodyUser bodyUser){
        return given()
                .contentType("application/json")
                .body(bodyUser)
                .when()
                .post(endpoint);

    }

    public Response deleteUser(String id){
        return given()
                .contentType("application/json")
                .when()
                .delete(endpoint + slash + id);
    }

    public Response putUser(String id, BodyUser bodyUser){
        return given()
                .contentType("application/json")
                .body(bodyUser)
                .when()
                .post(endpoint + slash + id);

    }
}

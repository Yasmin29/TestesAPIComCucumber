package POJO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BodyUser {
    private String nome;
    private String email;
    @Builder.Default
    private String password = "senha123";
    @Builder.Default
    private String administrador = "false";

   /* public BodyUser() {
    }

    public BodyUser(String nome, String email, String password,String administrador) {
        this.nome = nome;
        this.email = email;
        this.password = password;
        this.administrador = administrador;
    }*/

}

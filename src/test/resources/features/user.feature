#language: pt
Funcionalidade: Criar novo usuario
  Como usuario
  Gostaria de criar um  novo usuario
  Para que possa acessar a loja

  Cenário: Criar um usuario com dados válido
    Quando crio um novo usuario com dados válidos
    Então deve retornar mensagem "Cadastro realizado com sucesso"
    E deve constar na lista de usuários cadastrados

  Cenário: Criar um administrador com dados válidos
    Quando crio um novo administrador com dados válidos
    Então deve retornar mensagem "Cadastro realizado com sucesso"
    E deve constar novo adminitador na lista de usuários cadastrados

  Cenário: Criar usuario com email já cadastrado
    Quando insiro um email ja cadastrado
    Então Deve retornar erro "Este email já está sendo usado"

  Esquema do Cenário: Criar usuario com email inválido
    Quando insiro um "<email>" inválido
    Então Retorna mensagem "email deve ser um email válido"
    Exemplos:
      | email              |
      | email.invalido.com |
      | email@invalido     |
      | email@invalido.    |

  Esquema do Cenário: Criar usuario sem preencher o campo "<campo>"
    Dado que preencho os campos com "<nome>", "<email>", "<password>","<administrador>"
    Então retorna erro 400

    Exemplos:
      | campo         | nome      | email            | password | administrador |
      | nome          |           | email@email.com  | senha123 | false         |
      | email         | Guilherme |                  | senha123 | false         |
      | password      | Arthur    | arthur@email.com |          | false         |
      | administrador | Amanda    | amanda@email.com | senha123 |               |

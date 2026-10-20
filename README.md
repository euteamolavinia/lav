# lav, api de banco

lav vem de lavinia o nome da [minha namorada](https://github.com/scriptlver) q eu amo tanto, me inspirou a voltar a estudar código e é sem dúvidas a minha maior inspiração pra qualquer coisa q eu faça na vida. eu te amo muito amor, q o projeto sirva pra eternizar o nosso amor e q nós possamos sempre lembrar como uma homenagem fofinha sempre q vc olhar o github meu bem <3 

o projeto foi feito pra estudar a arquitetura de um sistema de banco, construindo apis REST persistência de dados e integridade 100% fiel nas operações usando o mínimo de ferramentas pra assistenciar

usei funções do claude e do gemini/gemini notebook pra construir os migrations (q é uma tortura aprender sozinho, eu tentei), consertar algumas coisas no java q tava dando pau e guiar as pesquisas, então possúi código de IA! 

### tecnologias usadas

- GnuCOBOL
- Java 25 + Spring Boot
- PostgreSQL
- Spring Data JPA
- Flyway
- Maven
- Postman

### oq funciona e consultas interessantes só pra eu lembrar
- cadastro e consulta de clientes
- abertura e consulta de contas bancárias
- depósitos com atualização de saldo
- registro e consulta do histórico de movimentações
- validações e tratamento de erros

POST	/api/v1/customers	cadastrar cliente

GET	/api/v1/customers/{id}	consultar cliente

GET	/api/v1/customers	listar clientes

POST	/api/v1/accounts	abrir conta

GET	/api/v1/accounts/{accountNumber}	consultar conta por número

GET	/api/v1/accounts/id/{id}	consultar uma conta pelo seu uid (vou tirar na próxima versão e adotar um método melhor pra encontrar quem está por trás do número de conta)

GET	/api/v1/accounts/customer/{customerId}	listar todas as contas de um cliente

POST	/api/v1/accounts/{accountNumber}/deposits	fazer um depósito

GET	/api/v1/accounts/{accountNumber}/transactions?page=0&size=10	Consultar histórico paginado



**projeto em desenvolvimento.**
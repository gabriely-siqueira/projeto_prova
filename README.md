Clinic Project
O Clinic Project é um sistema gerenciador de agendamentos em uma clínica médica. Ele permite que funcionários da clínica cadastrem médicos, pacientes e realizem o agendamento de consultas. O sistema possui as seguintes funcionalidades:

Médicos
Os funcionários da clínica podem cadastrar, editar, excluir e listar médicos no sistema. Ao cadastrar um médico, as seguintes informações devem ser preenchidas:

Nome
E-mail
Telefone
Especialidade (Ortopedia, Cardiologia, Ginecologia ou Dermatologia)
Endereço completo

Pacientes
Os funcionários da clínica também podem cadastrar pacientes, editar, excluir e listar. Ao cadastrar um paciente, as seguintes informações devem ser preenchidas:

Nome
E-mail
Telefone
CPF
Endereço completo

Agendamento de Consultas
O sistema permite o agendamento,  edição, exclusão e listagem de consultas. Ao realizar o agendamento, as seguintes informações devem ser preenchidas:

Paciente
Médico
Data/Hora da consulta
Regras de Negócio

O Clinic Project possui as seguintes regras de negócio:
Não é permitido o agendamento de consultas com pacientes inativos no sistema.
Não é permitido o agendamento de consultas com médicos inativos no sistema.
Não é permitido o agendamento de uma consulta com um médico que já possui outra consulta agendada na mesma data/hora.

Tecnologias Utilizadas
O Clinic Project foi desenvolvido utilizando as seguintes tecnologias:
Java
Spring Boot
Spring Data JPA
Spring Security
PostgreSQL
Maven

Configuração e Execução
Para executar o Clinic Project em sua máquina local, siga as etapas abaixo:
Certifique-se de ter o Java JDK (versão 17 ou superior) instalado em seu sistema.
Configure um banco de dados PostgreSQL e atualize as configurações de conexão no arquivo application.properties.
Execute o comando mvn spring-boot:run na raiz do projeto para iniciar a aplicação.
Acesse a aplicação no navegador usando o endereço http://localhost:8080.

Próximos Passos
O Clinic Project é um sistema básico para gerenciamento de agendamentos em uma clínica médica. Você pode expandir suas funcionalidades adicionando recursos como autenticação de usuários, geração de relatórios e integração com sistemas externos. Sinta-se à vontade para contribuir com o projeto, adicionar novas funcionalidades e melhorar sua usabilidade.

Desenvovedora do Projeto
O Clinic Project foi desenvolvido por Gabriely de Souza Siqueira. Se você tiver alguma dúvida ou sugestão, sinta-se à vontade para entrar comigo.


insert into city (id,name,state)
values (1,'Tubarão','SC' );
insert into city (id,name,state)
values (2,'Florianópolis','SC' );
insert into city (id,name,state)
values (3,'Criciúma','SC' );

INSERT INTO address (id, street, neighborhood, postal_code, complement, number, city_id)
VALUES (1, 'Example Street 1', 'Neighborhood Example 1', '12345-678', 'complement 1', '1', 1);

INSERT INTO address (id, street, neighborhood, postal_code, complement, number, city_id)
VALUES (2, 'Example Street 2', 'Neighborhood Example 2', '12345-739', 'complement 2', '2', 2);

INSERT INTO address (id, street, neighborhood, postal_code, complement, number, city_id)
VALUES (3, 'Example Street 3', 'Neighborhood Example 3', '12345-029', 'complement 3', '3', 3);
INSERT INTO address (id, street, neighborhood, postal_code, complement, number, city_id)
VALUES (4, 'Example Street 4', 'Neighborhood Example 4', '12345-079', 'complement 4', '4', 3);

Insert into specialty (id, description) values (1, 'orthology');
Insert into specialty (id, description) values (2, 'gynecology');
Insert into specialty (id, description) values (3, 'cardiology');
Insert into specialty (id, description) values (4, 'dermatology');

INSERT INTO doctor (id, name, email,cpf, specialty_id, address_id)
VALUES (1, 'João Silva', 'joaosilva@example.com','1111111111', 1, 1);

INSERT INTO doctor (id, name, email,cpf, specialty_id, address_id)
VALUES (2, 'Maria Santos', 'mariasantos@example.com','2222222222', 2, 2);

INSERT INTO doctor (id, name, email, cpf,specialty_id, address_id)
VALUES (3, 'Pedro Oliveira', 'pedrooliveira@example.com','3333333333', 3, 3);

INSERT INTO doctor (id, name, email,cpf, specialty_id, address_id)
VALUES (4, 'Jéssica Souza', 'jessicasouza@example.com','44444444444', 4, 4);

INSERT INTO patient (id, name, email,cpf, address_id)
VALUES (1, 'Paulo Siqueira', 'paulosiqueira@example.com','1423532923', 1);

INSERT INTO patient (id, name, email,cpf, address_id)
VALUES (2, 'Cláudia Oliveira', 'claudiaoliveira@example.com','5555555555', 2);

INSERT INTO patient (id, name, email,cpf, address_id)
VALUES (3, 'Ana Rodrigues', 'anarodrigues@example.com','1231234123', 3);
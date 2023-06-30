package br.com.trier.spring_matutino.services;

import java.util.List;

import br.com.trier.spring_matutino.domain.User;

public interface UserService {

	User insert(User employee);
	List<User> listAll();
	User findById(Integer id);
	User update(User employee);
	void delete(Integer id);
	User findByEmail(String email);
	List<User> findByName(String name);
}



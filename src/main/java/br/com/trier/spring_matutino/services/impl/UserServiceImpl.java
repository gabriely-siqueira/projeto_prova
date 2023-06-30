package br.com.trier.spring_matutino.services.impl;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import br.com.trier.spring_matutino.domain.User;
import br.com.trier.spring_matutino.repositories.UserRepository;
import br.com.trier.spring_matutino.services.UserService;
import br.com.trier.spring_matutino.services.exceptions.IntegrityViolationException;
import br.com.trier.spring_matutino.services.exceptions.ObjectNotFoundException;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserRepository repository;

	@Override
	public User insert(User user) {
		validateUser(user);
		return repository.save(user);
	}
	
	private void validateUser(User user) {
		if(user == null) {
			throw new IntegrityViolationException("O usuário está nulo");
		} else if(user.getName() == null || user.getName().isBlank()) {
			throw new IntegrityViolationException("O nome está vazio");
		} else if(user.getEmail() == null || user.getEmail().isBlank()) {
			throw new IntegrityViolationException("O email está vazio");
		}
		validateEmail(user);
	}
	
	private void validateEmail(User user) {
		Optional<User> usuarioOptional = repository.findByEmail(user.getEmail());
		if (usuarioOptional.isPresent()) {
			if (user.getId() != usuarioOptional.get().getId()) {
				throw new IntegrityViolationException("Esse email já existe");
			}
		}
	}

	@Override
	public List<User> listAll() {
		if (repository.findAll().isEmpty()) {
			throw new ObjectNotFoundException("Não há usuários cadastrados");
		}
		return repository.findAll(Sort.by(Sort.Direction.ASC, "id"));
	}

	@Override
	public User findById(Integer id) {
		return repository.findById(id).orElseThrow(() 
				-> new ObjectNotFoundException("Usuário %s não encontrado".formatted(id)));
	}

	@Override
	public User update(User user) {
		if(!listAll().contains(user)) {
			throw new ObjectNotFoundException("Esse usuário não existe");
		}
		return insert(user);
	}

	@Override
	public void delete(Integer id) {
		repository.delete(findById(id));
	}

	@Override
	public User findByEmail(String email) {
		return repository.findByEmail(email).orElseThrow(() 
				-> new ObjectNotFoundException("Usuário com email %s não encontrado".formatted(email)));
	}

	@Override
	public List<User> findByName(String name) {
		if (repository.findByNameContainsIgnoreCase(name).isEmpty()) {
			throw new ObjectNotFoundException("Não há nenhum usuário com o nome " + name);
		}
		return repository.findByNameContainsIgnoreCase(name);
	}
}
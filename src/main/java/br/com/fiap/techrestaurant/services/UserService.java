package br.com.fiap.techrestaurant.services;

import br.com.fiap.techrestaurant.dtos.LoginDto;
import br.com.fiap.techrestaurant.entities.User;
import br.com.fiap.techrestaurant.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> findAllUsers(int page, int size) {
        int offset = (page - 1) * size;
        return this.userRepository.findAll(size, offset);
    }

    public Optional<User> findUserById(Long id) {
        return this.userRepository.findById(id);
    }

    public void saveUser(User user) {
        setCurrentDate(user);
        Integer save = this.userRepository.save(user);
        Assert.state(save == 1, "Erro ao salvar o usuario " + user.getName());
    }

    public void updateUser(User user, Long id) {
        setCurrentDate(user);
        Integer update = this.userRepository.update(user, id);
        Assert.state(update == 1, "Usuario nao encontrado");
    }

    public void deleteUser(Long id) {
        Integer delete = this.userRepository.delete(id);
        Assert.state(delete == 1, "Veiculo nao encontrado");
    }

    public Boolean validateUser(LoginDto loginDto) {
        return this.userRepository.validateUser(loginDto);
    }

    private static void setCurrentDate(User user) {
        user.setModificationDate(new Date());
    }
}

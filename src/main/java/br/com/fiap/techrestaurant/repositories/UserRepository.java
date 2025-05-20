package br.com.fiap.techrestaurant.repositories;

import br.com.fiap.techrestaurant.entities.Login;
import br.com.fiap.techrestaurant.entities.User;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UserRepository {

    private final JdbcClient jdbcClient;

    public UserRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public List<User> findAll(int size, int offset) {
        return this.jdbcClient
                .sql("SELECT * FROM users LIMIT :size OFFSET :offset")
                .param("size", size)
                .param("offset", offset)
                .query(User.class)
                .list();
    }

    public Optional<User> findById(Long id) {
        return this.jdbcClient
                .sql("SELECT * FROM users WHERE id = :id")
                .param("id", id)
                .query(User.class)
                .optional();
    }

    public Integer save(User user) {
        return this.jdbcClient
                .sql("""
                        INSERT INTO users (name, email, login, password, modificationDate, address)
                        VALUES (:name, :email, :login, :password, :modificationDate, :address)
                    """)
                .param("name", user.getName())
                .param("email", user.getEmail())
                .param("login", user.getLogin())
                .param("password", user.getPassword())
                .param("modificationDate", user.getModificationDate())
                .param("address", user.getAddress())
                .update();
    }

    public Integer update(User user, Long id) {
        return this.jdbcClient
                .sql("""
                        UPDATE users
                        SET name = :name, email = :email, login = :login,
                            password = :password, modificationDate = :modificationDate, address = :address
                        WHERE id = :id
                    """)
                .param("name", user.getName())
                .param("email", user.getEmail())
                .param("login", user.getLogin())
                .param("password", user.getPassword())
                .param("modificationDate", user.getModificationDate())
                .param("address", user.getAddress())
                .param("id", id)
                .update();
    }

    public Integer delete(Long id) {
        return this.jdbcClient
                .sql("DELETE * FROM users WHERE id = :id")
                .param("id", id)
                .update();
    }

    public Boolean validateUser(Login login) {
        return this.jdbcClient
                .sql("SELECT 1 FROM users WHERE login = :login AND password = :password")
                .param("login", login.getLogin())
                .param("password", login.getPassword())
                .query(Boolean.class)
                .optional()
                .orElse(false);
    }
}

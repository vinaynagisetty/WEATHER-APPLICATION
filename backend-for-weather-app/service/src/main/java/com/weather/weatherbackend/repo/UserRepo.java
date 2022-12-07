package com.weather.weatherbackend.repo;


        import java.util.Optional;

        import com.weather.weatherbackend.domain.User;
        import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Integer> {

    Optional<User> findByUserNameAndPassword(String username, String password);

}

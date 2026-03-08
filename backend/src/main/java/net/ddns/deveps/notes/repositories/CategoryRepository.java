package net.ddns.deveps.notes.repositories;

import net.ddns.deveps.notes.entities.Category;
import net.ddns.deveps.notes.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findByUser(User user);
    Optional<Category> findByIdAndUser(Long id, User user);
}

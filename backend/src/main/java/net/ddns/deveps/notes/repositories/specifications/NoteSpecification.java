package net.ddns.deveps.notes.repositories.specifications;

import net.ddns.deveps.notes.entities.Note;
import net.ddns.deveps.notes.entities.User;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class NoteSpecification {

    private NoteSpecification() {}

    public static Specification<Note> hasUser(User user) {
        return (root, query, cb) -> cb.equal(root.get("user"), user);
    }

    public static Specification<Note> titleOrContentContains(String text) {
        return (root, query, cb) -> {
            String pattern = "%" + text.toLowerCase() + "%";
            return cb.or(
                    cb.like(cb.lower(root.get("title")), pattern),
                    cb.like(cb.lower(root.get("content")), pattern)
            );
        };
    }

    public static Specification<Note> hasCategory(Long categoryId) {
        return (root, query, cb) -> cb.equal(root.get("category").get("id"), categoryId);
    }

    public static Specification<Note> createdAfter(LocalDate date) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(
                root.get("createdAt"), date.atStartOfDay()
        );
    }

    public static Specification<Note> createdBefore(LocalDate date) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(
                root.get("createdAt"), date.plusDays(1).atStartOfDay()
        );
    }
}

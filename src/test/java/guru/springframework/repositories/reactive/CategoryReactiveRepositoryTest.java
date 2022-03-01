package guru.springframework.repositories.reactive;

import guru.springframework.domain.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@DataMongoTest
public class CategoryReactiveRepositoryTest {

    @Autowired
    CategoryReactiveRepository repository;

    @BeforeEach
    public void Setup() {
        repository.deleteAll().block();
    }

    @Test
    public void testSave() {
        Category category = new Category();
        category.setDescription("Each");

        repository.save(category).block();

        Long count = repository.count().block();

        assertEquals(1, count.longValue());
    }

    @Test
    public void testFindByDescription() {
        Category category = new Category();
        category.setDescription("Each");

        repository.save(category).block();

        Category foundCategory = repository.findByDescription("Each").block();

        assertNotNull(foundCategory);
    }
}
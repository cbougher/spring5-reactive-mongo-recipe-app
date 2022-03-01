package guru.springframework.repositories.reactive;

import guru.springframework.domain.Category;
import guru.springframework.domain.UnitOfMeasure;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@DataMongoTest
public class UnitOfMeasureReactiveRepositoryTest {

    @Autowired
    UnitOfMeasureReactiveRepository repository;

    @BeforeEach
    public void Setup() {
        repository.deleteAll().block();
    }

    @Test
    public void testSave() {
        UnitOfMeasure unitOfMeasure = new UnitOfMeasure();
        unitOfMeasure.setDescription("Each");

        repository.save(unitOfMeasure).block();

        Long count = repository.count().block();

        assertEquals(1, count.longValue());
    }

    @Test
    public void testFindByDescription() {
        UnitOfMeasure unitOfMeasure = new UnitOfMeasure();
        unitOfMeasure.setDescription("Each");

        repository.save(unitOfMeasure).block();

        UnitOfMeasure foundUnitOfMeasure = repository.findByDescription("Each").block();

        assertNotNull(foundUnitOfMeasure);
    }
}
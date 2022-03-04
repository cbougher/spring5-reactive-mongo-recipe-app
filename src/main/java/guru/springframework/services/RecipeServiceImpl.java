package guru.springframework.services;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.converters.RecipeCommandToRecipe;
import guru.springframework.converters.RecipeToRecipeCommand;
import guru.springframework.domain.Recipe;
import guru.springframework.repositories.reactive.RecipeReactiveRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Created by jt on 6/13/17.
 */
@Slf4j
@Service
public class RecipeServiceImpl implements RecipeService {

    private final RecipeReactiveRepository recipeRepository;
    private final RecipeCommandToRecipe recipeCommandToRecipe;
    private final RecipeToRecipeCommand recipeToRecipeCommand;

    public RecipeServiceImpl(RecipeReactiveRepository recipeRepository, RecipeCommandToRecipe recipeCommandToRecipe, RecipeToRecipeCommand recipeToRecipeCommand) {
        this.recipeRepository = recipeRepository;
        this.recipeCommandToRecipe = recipeCommandToRecipe;
        this.recipeToRecipeCommand = recipeToRecipeCommand;
    }

    @Override
    public Flux<Recipe> getRecipes() {
        log.debug("I'm in the service");

        return recipeRepository.findAll();
    }

    @Override
    public Mono<Recipe> findById(String id) {
        return recipeRepository.findById(id);
    }

    @Override
    public Mono<RecipeCommand> findCommandById(String id) {

        return findById(id).mapNotNull(recipeToRecipeCommand::convert).map(recipeCommand -> {
            if(recipeCommand.getIngredients() != null && recipeCommand.getIngredients().size() > 0) {
                recipeCommand.getIngredients().forEach(ing -> {
                    ing.setRecipeId(recipeCommand.getId());
                });
            }
            return recipeCommand;
        });
    }

    @Override
    public Mono<RecipeCommand> saveRecipeCommand(@NonNull RecipeCommand command) {
        return recipeRepository.save(recipeCommandToRecipe.convert(command))
                .mapNotNull(recipeToRecipeCommand::convert);
    }

    @Override
    public Void deleteById(String idToDelete) {
        return recipeRepository.deleteById(idToDelete).block();
    }
}

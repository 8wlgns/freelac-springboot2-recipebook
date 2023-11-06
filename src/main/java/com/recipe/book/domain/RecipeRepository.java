package com.recipe.book.domain;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RecipeRepository extends JpaRepository<Recipe, Long>{
	
	@Query("SELECT recipe From Recipe recipe ORDER BY recipe.id DESC")
	List<Recipe> findAllDesc();
}

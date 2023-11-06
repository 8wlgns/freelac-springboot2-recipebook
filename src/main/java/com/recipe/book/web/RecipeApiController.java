package com.recipe.book.web;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.recipe.book.dto.RecipeRequestDto;
import com.recipe.book.dto.RecipeResponseDto;
import com.recipe.book.dto.RecipeUpdateDto;
import com.recipe.book.service.RecipeService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class RecipeApiController {

	private final RecipeService recipeService;
	
	@GetMapping("/api/v1/recipe/{id}")
	public RecipeResponseDto findById(@PathVariable Long id) {
		return recipeService.findById(id);
	}
	
	@PostMapping("/api/v1/recipe")
	public Long save(@RequestBody RecipeRequestDto requestDto) {
		return recipeService.save(requestDto);
	}
	
	@PutMapping("/api/v1/recipe/{id}")
	public Long update(@PathVariable Long id, @RequestBody RecipeUpdateDto updateDto) {
		return recipeService.update(id, updateDto);
	}
	
	@DeleteMapping("/api/v1/recipe/{id}")
	public Long recipeUpdate(@PathVariable Long id) {
		recipeService.delete(id);
		
		return id;
	}
}

package com.recipe.book.dto;

import com.recipe.book.domain.Recipe;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Getter
@NoArgsConstructor
public class RecipeResponseDto {
	private Long id;
	private String title;
	private String content;
	
	public RecipeResponseDto(Recipe recipe) {
		this.id = recipe.getId();
		this.title = recipe.getTitle();
		this.content = recipe.getContent();
	}
}

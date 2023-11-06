package com.recipe.book.dto;

import com.recipe.book.domain.Recipe;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RecipeRequestDto {
	private String title;
	private String content;
	
	@Builder
	public RecipeRequestDto(String title, String content) {
		this.title = title;
		this.content = content;
	}
	
	public Recipe toEntitiy() {
		return Recipe.builder().title(title).content(content).build();
	}
}

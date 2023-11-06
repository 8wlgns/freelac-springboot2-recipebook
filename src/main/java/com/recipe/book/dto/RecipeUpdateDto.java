package com.recipe.book.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class RecipeUpdateDto {
	private String title;
	private String content;
	
	@Builder
	public RecipeUpdateDto(String title, String content) {
		this.title = title;
		this.content = content;
	}
}

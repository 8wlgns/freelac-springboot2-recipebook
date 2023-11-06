package com.recipe.book.dto;

import java.time.LocalDateTime;

import com.recipe.book.domain.Recipe;

import lombok.Getter;

@Getter
public class RecipeListResponseDto {
	private Long id;
	private String title;
	private String content;
	private LocalDateTime modifiedDate;
	
	public RecipeListResponseDto(Recipe entitiy) {
		this.id = entitiy.getId();
		this.title = entitiy.getTitle();
		this.content = entitiy.getContent();
		this.modifiedDate = entitiy.getModifiedDate();
	}
}

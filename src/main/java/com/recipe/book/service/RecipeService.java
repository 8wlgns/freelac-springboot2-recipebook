package com.recipe.book.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.recipe.book.domain.Recipe;
import com.recipe.book.domain.RecipeRepository;
import com.recipe.book.dto.RecipeListResponseDto;
import com.recipe.book.dto.RecipeRequestDto;
import com.recipe.book.dto.RecipeResponseDto;
import com.recipe.book.dto.RecipeUpdateDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class RecipeService {
	
	private final RecipeRepository repository;
	
	public RecipeResponseDto findById(Long id) {
		Recipe entity = repository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다 id = " + id));
		return new RecipeResponseDto(entity);
	}
	
	@Transactional
	public Long save(RecipeRequestDto requestDto) {
		return repository.save(requestDto.toEntitiy()).getId();
	}
	
	@Transactional
	public Long update(Long id, RecipeUpdateDto updateDto) {
		Recipe recipe = repository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다 id = " + id));
		recipe.update(updateDto.getTitle(), updateDto.getContent());
		return id;
	}
	
	@Transactional()
	public List<RecipeListResponseDto> findAllDesc(){
		return repository.findAllDesc().stream()
				.map(RecipeListResponseDto::new)
				.collect(Collectors.toList());
	}
	
	@Transactional
	public void delete(Long id) {
		Recipe entity = repository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다 id = " + id));
		repository.delete(entity);
	}
	
	
}

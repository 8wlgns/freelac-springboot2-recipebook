package com.recipe.book.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RecipeRepositoryTest {

	@Autowired
	private RecipeRepository repository;
	
	@After
	public void cleanUp() {
		repository.deleteAll();
	}
	
//	@Test
//	public void 게시글저장_불러오기() {
//		String title = "title";
//		String content = "content";
//		
//		repository.save(Recipe.builder()
//							  .title(title)
//							  .content(content)
//							  .build());
//		
//		List<Recipe> list = repository.findAll();
//		
//		Recipe recipe = list.get(0);
//		assertEquals(title, recipe.getTitle());
//		assertEquals(content, recipe.getContent());
//	}
	
	@Test
	public void BaseTimeEntity_등록() {
		//given
		LocalDateTime now = LocalDateTime.of(2023, 10, 27, 0, 0, 0);
		repository.save(Recipe.builder()
								.title("title")
								.content("content")
								.build());
		
		//when
		List<Recipe> list = repository.findAll();
		
		//then
		Recipe recipe = list.get(0);
		
		System.out.println(">>>>>> createdDate=" + recipe.getCreatedDate()+", modifiedDate=" + recipe.getModifiedDate());
		
		assertThat(recipe.getCreatedDate()).isAfter(now);
		assertThat(recipe.getModifiedDate()).isAfter(now);
	}
}

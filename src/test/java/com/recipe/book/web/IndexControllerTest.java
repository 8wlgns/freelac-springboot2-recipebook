package com.recipe.book.web;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import com.recipe.book.domain.Recipe;
import com.recipe.book.domain.RecipeRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class IndexControllerTest {
	

	@LocalServerPort
	private int port;
	
	@Autowired
	private TestRestTemplate restTemplate;
	
	@Autowired
	private RecipeRepository repository;
	
	@Before
	public void tearUp() throws Exception{
		String title = "title";
		String content = "content";
		Recipe recpie = Recipe.builder().title(title).content(content).build();
		repository.save(recpie);
	}
	
	@Test
	public void 메인페이지_로딩() {
		//when
		String body = this.restTemplate.getForObject("/", String.class);
		
		System.out.println(body);
		
		//then
		assertThat(body).contains("글 등록");
		assertThat(body).contains("게시글번호");
		
		List<Recipe> list = repository.findAllDesc();
		assertThat(list).hasSizeGreaterThan(0);
	}

	
	@Test
	public void 등록페이지_로딩() {
		//when
		String body = this.restTemplate.getForObject("/recipe/save", String.class);
		
		//then
		assertThat(body).contains("게시글 등록");
	}

}

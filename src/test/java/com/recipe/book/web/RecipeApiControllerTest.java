package com.recipe.book.web;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.recipe.book.domain.Recipe;
import com.recipe.book.domain.RecipeRepository;
import com.recipe.book.dto.RecipeRequestDto;
import com.recipe.book.dto.RecipeResponseDto;
import com.recipe.book.dto.RecipeUpdateDto;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RecipeApiControllerTest {

	@LocalServerPort
	private int port;
	
	@Autowired
	private TestRestTemplate restTemplate;
	
	@Autowired
	private RecipeRepository repository;
	
	private final String title = "title";
	private final String content = "content";
	
	@Before
	public void tearUp() throws Exception{
		Recipe recpie = Recipe.builder().title(title).content(content).build();
		repository.save(recpie);
	}
	
	@After
	public void tearDown() throws Exception{
		repository.deleteAll();
	}
	
	@Test
	public void test_upload_recipe() {
		String title = "title2";
		String content = "content2";
		RecipeRequestDto requestDto = RecipeRequestDto.builder()
														.title(title)
														.content(content).build();
		String url = "http://localhost:" + port + "/api/v1/recipe";
		
		ResponseEntity<Long> responseEntity = restTemplate
													.postForEntity(url, requestDto, Long.class);
		
		
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertTrue(0L < responseEntity.getBody());
		
		Recipe recpie = repository.findAll().get(1);
		assertEquals(title, recpie.getTitle());
		assertEquals(content, recpie.getContent());
	}
	
	@Test
	public void test_get_recipe() {
		String url = "http://localhost:" + port + "/api/v1/recipe/1";
		ResponseEntity<RecipeResponseDto> responseEntity = restTemplate
																.getForEntity(url, RecipeResponseDto.class);
		
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(title, responseEntity.getBody().getTitle());
		assertEquals(content, responseEntity.getBody().getContent());
	}
	
	@Test
	public void test_update_recipe() {
		
		Long updatedId = repository.findAll().get(0).getId();
		
		String updatedTitle = "updatedTitle";
		String updatedContent = "updatedContent";
		RecipeUpdateDto updateDto = RecipeUpdateDto.builder()
											.title(updatedTitle)
											.content(updatedContent).build();
		
		
		String url = "http://localhost:" + port + "/api/v1/recipe/" + updatedId;
		
		HttpEntity<RecipeUpdateDto> requestEntity = new HttpEntity<>(updateDto);
		
		ResponseEntity<Long> responseEntity = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, Long.class);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		
		assertTrue(0L < responseEntity.getBody());
		
		Recipe updateRecpie = repository.findAll().get(0);
		assertEquals(updatedTitle, updateRecpie.getTitle());
		assertEquals(updatedContent, updateRecpie.getContent());
	}
	
}

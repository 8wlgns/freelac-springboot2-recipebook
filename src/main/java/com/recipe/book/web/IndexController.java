package com.recipe.book.web;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.recipe.book.domain.user.User;
import com.recipe.book.dto.RecipeResponseDto;
import com.recipe.book.service.RecipeService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class IndexController {

		private final RecipeService recipeService;
		private final HttpSession httpSession;
		
		@GetMapping("/")
		public String index(Model model) {
			model.addAttribute("recipes", recipeService.findAllDesc());
			//SessionUser user = (SessionUser) httpSession.getAttribute("user");
			User u = (User) httpSession.getAttribute("user");
			if (u != null) {
				model.addAttribute("userName", u.getName());
			}
			return "index";
		}
		
		@GetMapping("/recipe/save")
		public String recipeSave() {
			return "recipe-save";
		}
		
		@GetMapping("/recipe/update/{id}")
		public String recipeUpdate(@PathVariable Long id, Model model) {
			RecipeResponseDto dto = recipeService.findById(id);
			model.addAttribute("recipe", dto);
			
			return "recipe-update";
		}
		
}

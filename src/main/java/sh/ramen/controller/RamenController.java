package sh.ramen.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import sh.ramen.dto.Ramen;
import sh.ramen.service.FavyService;

@RestController
public class RamenController {

	@Autowired
	private FavyService favyService;

	@GetMapping(path = "get")
	public List<Ramen> get() {

		return favyService.findAll();
	}
}

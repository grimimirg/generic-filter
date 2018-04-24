package it.genericfilter;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/test")
public class EntityExController {

	@Autowired
	private GenericService<EntityEx> genericService;

	@RequestMapping(method = RequestMethod.POST)
	public @ResponseBody List<EntityEx> testfIlter(@RequestBody EntityEx entity) throws Exception {
		return genericService.init(EntityEx.class).filteredList(entity, new EntityExCriterionFilter());
	}

}
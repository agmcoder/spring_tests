package es.codeurjc.ais.nitflex.restassured;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.servlet.ModelAndView;

import es.codeurjc.ais.nitflex.Application;
import es.codeurjc.ais.nitflex.film.Film;

@WebAppConfiguration
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = Application.class)
public class E2EFilmTest {

	@Autowired
	private MockMvc mvc;

	@Test
    public void createfilm() throws Exception {
		
		String title = "Lo que el viento se llevo";
		String synopsis = "synopsis";
		int year = 2000;
		String url = "https://www.urjc.es/images/Logos/logo-urjc-25.png";
		
		ResultActions resultActions  =(ResultActions) mvc.perform(post("/createfilm")
    			.param("title", title)
    			.param("synopsis", synopsis)
    			.param("year", Integer.toString(year))
    			.param("url", url)
    			.param("id", "0"))
    		    .andExpect(status().is(302))
    		    .andDo(print());
    	
		MvcResult result = resultActions.andReturn();
		ModelAndView model = result.getModelAndView();
		String view = model.getViewName();
		String[] aux = view.split("/");
		long id = Long.parseLong(aux[aux.length-1]);
		
		ResultActions resultActions2  =(ResultActions) mvc.perform(get("/films/"+id)
				.contentType(MediaType.APPLICATION_JSON))
    		    .andExpect(status().is(200))
    		    .andDo(print());
		result = resultActions2.andReturn();
		model = result.getModelAndView();
		Film film = (Film) model.getModel().get("film");
    	
		assertEquals(film.getTitle(), title);
		assertEquals(film.getUrl(), url);
		assertEquals(film.getYear(), year);
		assertEquals(film.getSynopsis(), synopsis);
		assertEquals(film.getId(), id);
 
    }

}

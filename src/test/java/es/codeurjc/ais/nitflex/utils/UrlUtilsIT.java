package es.codeurjc.ais.nitflex.utils;

import static org.assertj.core.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.server.ResponseStatusException;

import es.codeurjc.ais.nitflex.Application;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = Application.class)
@AutoConfigureMockMvc
@WebAppConfiguration
public class UrlUtilsIT {

	@Autowired
	private UrlUtils urlUtils;

	@Test
	public void urlMal() {
		String url = "hppp:/www.google.";

		try {
			urlUtils.checkValidImageURL(url);
			fail("fallo en la URL de la imagen");
		} catch (ResponseStatusException e) {
			assertEquals("400 BAD_REQUEST \"The url format is not valid\"", e.getMessage());
		}

	}
	
	@Test
	public void urlNoImage() {
		String url = "http://www.google.com";

		try {
			urlUtils.checkValidImageURL(url);
			fail("fallo en la URL de la imagen");
		} catch (ResponseStatusException e) {
			assertEquals("400 BAD_REQUEST \"The url is not an image resource\"", e.getMessage());
		}

	}

	@Test
	public void errorDownloadImage() {
		String url = "http://www.google.png";

		try {
			urlUtils.checkValidImageURL(url);
			fail("fallo en la URL de la imagen");
		} catch (ResponseStatusException e) {
			assertEquals("500 INTERNAL_SERVER_ERROR \"Problem at checking URL: " + url + "\"", e.getMessage());
		}

	}
	
	@Test
	public void urlOk() {
		String url = "https://www.urjc.es/images/Logos/logo-urjc-25.png";

		try {
			urlUtils.checkValidImageURL(url);
		} catch (ResponseStatusException e) {
			fail("La descarga dela imagen debería ser correcta");
		}

	}
}

package es.codeurjc.ais.nitflex.restassured;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import es.codeurjc.ais.nitflex.Application;
import io.restassured.RestAssured;
import io.restassured.response.Response;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = Application.class)
public class E2EDeleteFilmTest {

	@LocalServerPort
	private int port;

	@BeforeEach
	public void setUp() {
		RestAssured.port = port;
	}
	
	private static Document convertStringToXMLDocument(String xmlString) {
		// Parser that produces DOM object trees from XML content
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

		// API to obtain DOM Document instance
		DocumentBuilder builder = null;
		try {
			// Create DocumentBuilder with default configuration
			builder = factory.newDocumentBuilder();

			// Parse the content to Document object
			Document doc = builder.parse(new InputSource(new StringReader(xmlString)));
			return doc;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Test
	public void deletefilm() throws Exception {

		String title = "Lo que el viento se llevo";
		String synopsis = "synopsis";
		int year = 2000;
		String url = "https://www.urjc.es/images/Logos/logo-urjc-25.png";

		try {
			Response response = given()
			 .params("title", title)
			 .params("synopsis", synopsis)
			 .params("year", year)
			 .params("url", url)
			 .params("id", 0)
			.when().post("/createfilm")
			.andReturn();
			
			String location = response.getHeader("Location");
			String[] aux = location.split("/");
			long id = Long.parseLong(aux[aux.length-1]);
			
			response = given()
					.when().get("/removefilm/"+id)
					.andReturn();

			response = given()
					.when().get("/films/"+id)
					.andReturn();
			// Use method to convert XML string content to XML Document object
			Document doc = convertStringToXMLDocument(response.getBody().asString());
		

			String removefilm = doc.getElementsByTagName("h1").item(0).getTextContent().trim();
			
			assertEquals(removefilm, "All films");
		} catch (Exception e) {
			e.printStackTrace();
		}
		

	}

}

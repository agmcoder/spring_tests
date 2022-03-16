package es.codeurjc.ais.nitflex.film;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;

import es.codeurjc.ais.nitflex.Application;
import es.codeurjc.ais.nitflex.notification.NotificationService;
import es.codeurjc.ais.nitflex.utils.UrlUtils;

//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = Application.class)
//@WebAppConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
//@AutoConfigureMockMvc
public class FilmServiceTest {

	@InjectMocks
	private FilmService filmService;

	@Mock
	private FilmRepository filmRepository;

	@Mock
	private NotificationService notificationService;

	@Mock
	private UrlUtils urlUtils;

	@Before(value = "")
	public void init() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void getAllFilmTest() {
		List<Film> lstFilm = new ArrayList<Film>();
		Film film1 = new Film("title1", "synopsis1", 2022, "url1");
		Film film2 = new Film("title2", "synopsis2", 2022, "url2");
		Film film3 = new Film("title3", "synopsis3", 2022, "url3");

		lstFilm.add(film1);
		lstFilm.add(film2);
		lstFilm.add(film3);

		when(filmRepository.findAll()).thenReturn(lstFilm);

		// test
		List<Film> lstResult = filmService.findAll();

		assertEquals(3, lstResult.size());
		verify(filmRepository, times(1)).findAll();
	}

	@Test
	public void saveTest() {
		Film film = new Film("title1", "synopsis1", 2022, "url1");

		when(filmRepository.save(film)).thenReturn(film);

		// test
		Film newFilm = filmService.save(film);

		verify(filmRepository, times(1)).save(film);
		verify(urlUtils, times(1)).checkValidImageURL(film.getUrl());
		verify(notificationService, times(1))
				.notify("Film Event: Film with title=" + newFilm.getTitle() + " was created");

		assertEquals(film.getTitle(), newFilm.getTitle());
		assertEquals(film.getUrl(), newFilm.getUrl());
		assertEquals(film.getYear(), newFilm.getYear());
		assertEquals(film.getSynopsis(), newFilm.getSynopsis());

	}

	@Test
	public void deleteTest() {
		long id = 1l;

		// test
		filmService.delete(id);

		verify(filmRepository, times(1)).deleteById(id);
		verify(notificationService, times(1)).notify("Film Event: Film with id=" + id + " was deleted");
	}

}

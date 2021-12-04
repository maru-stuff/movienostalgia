package marustuff.movienostalgia;

import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

@Service
public class OmbdService {
    private static final String MOVIE_ATTRIBUTE_MODEL_NAME = "movie";
    private static final String BY_ID_MODEL_VIEW = "byid";
    private static final String API_KEY = "15913db";

    private static final Logger logger = LoggerFactory.getLogger(OmbdService.class);
    private static final String RANDOM_MOVIE_LIST_VIEW = "movieList";
    private static final String MOVIE_LIST_ATTRIBUTE_MODEL_NAME = "movieList";
    private static final String GENERIC_ERROR_VIEW = "error";
    private static final String OMDB_API_ENDPOINT = "http://www.omdbapi.com/?apikey=";
    private static final String IMDB_ID_URL_PARAMETER="&i=";
    private static final String IMBD_ID_PREFIX = "tt0";
    private static final String IMDB_ID_RANDOM_NUMBER_FORMAT = "%06d";
    private static final String NOT_AVAIBLE = "N/A";

    public Movie getMovieByimdbId(String imdbId) throws MovieNotFoundException {
        RestTemplate restTemplate = new RestTemplate();
        Movie movie = restTemplate.getForObject(OMDB_API_ENDPOINT + API_KEY +  IMDB_ID_URL_PARAMETER + imdbId, Movie.class);
        return movie;
    }

    public String getMovieByIdView(String imbdId, Model model) throws MovieNotFoundException {
        model.addAttribute(MOVIE_ATTRIBUTE_MODEL_NAME, getMovieByimdbId(imbdId));
        return BY_ID_MODEL_VIEW;
    }

    public Movie getRandomMovie(){
        String imdbId;
        Movie movie;
        while (true) {
            int randomNum = ThreadLocalRandom.current().nextInt(000000, 399999);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e){
                logger.error("InterruptedException");
            }
            imdbId = IMBD_ID_PREFIX + String.format(IMDB_ID_RANDOM_NUMBER_FORMAT, randomNum);
            logger.info("imbdId = " + imdbId);
            try {
                movie = getMovieByimdbId(imdbId);

                if(movie.getTitle().equals(null)||movie.getTitle().equals("#DUPE#")){

                    continue;
                } else if (movie.getPoster().equals(NOT_AVAIBLE)||movie.getPoster().equals(null)||movie.getTitle().equals(null)||movie.getTitle().equals(NOT_AVAIBLE)) {


                    continue;
                } else {
                    logger.info("title=" + movie.getTitle());
                    logger.info("poster=" + movie.getPoster());
                    logger.info("plot=" + movie.getPlot());
                    logger.info("movie="+movie.toString());
                    break;
                }
            } catch (MovieNotFoundException e) {
                logger.error(e.getMessage());
                continue;
            }
        }
        return movie;
    }

    public String getRandomMovieView(Model model) {
        model.addAttribute(MOVIE_ATTRIBUTE_MODEL_NAME, getRandomMovie());
        return BY_ID_MODEL_VIEW;
    }

    public String getRandomMovieListView(Model model) {
        ArrayList<Movie> movieList = new ArrayList<Movie>();
        for (int i = 1; i <= 4; i++) {

            movieList.add(getRandomMovie());
            logger.info(i + " th movie generated");
        }

        model.addAttribute(MOVIE_LIST_ATTRIBUTE_MODEL_NAME, movieList);
        return RANDOM_MOVIE_LIST_VIEW;
    }
}

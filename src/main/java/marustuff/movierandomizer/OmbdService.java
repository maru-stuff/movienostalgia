package marustuff.movierandomizer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

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
    private static final String NOT_AVAILABLE = "N/A";
    private static final String DUPLICATE = "#DUPE#";

    public Movie getMovieByimdbId(String imdbId) throws MovieNotFoundException {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(OMDB_API_ENDPOINT + API_KEY +  IMDB_ID_URL_PARAMETER + imdbId, Movie.class);
    }

    public String getMovieByIdView(String imdbId, Model model) throws MovieNotFoundException {
        model.addAttribute(MOVIE_ATTRIBUTE_MODEL_NAME, getMovieByimdbId(imdbId));
        return BY_ID_MODEL_VIEW;
    }

    public Movie getRandomMovie(){
        String imdbId;
        Movie movie;
        while (true) {
            int randomNum = ThreadLocalRandom.current().nextInt(0, 399999);
            try {
                Thread.sleep(300);
            } catch (InterruptedException e){
                logger.error("InterruptedException"+e.getMessage());
            }
            imdbId = IMBD_ID_PREFIX + String.format(IMDB_ID_RANDOM_NUMBER_FORMAT, randomNum);
            logger.info("imdbId = " + imdbId);
            try {
                movie = getMovieByimdbId(imdbId);
                if(hasNecessaryData(movie)){
                    logger.info("movie="+movie);
                    return movie;
                }
            } catch (MovieNotFoundException e) {
                logger.warn(e.getMessage());
            }
        }
    }

    private boolean hasNecessaryData(Movie movie){
        return movie.getTitle() !=null &&
                movie.getPoster() !=null &&
                !movie.getTitle().equals(DUPLICATE) &&
                !movie.getPoster().equals(NOT_AVAILABLE)&&
                !movie.getTitle().equals(NOT_AVAILABLE)&&
                !movie.getPlot().equals(NOT_AVAILABLE);
    }

    public String getRandomMovieView(Model model) {
        model.addAttribute(MOVIE_ATTRIBUTE_MODEL_NAME, getRandomMovie());
        return BY_ID_MODEL_VIEW;
    }

    public String getRandomMovieListView(Model model) {
        ArrayList<Movie> movieList = new ArrayList<>();
        for (int i = 1; i <= 4; i++) {

            movieList.add(getRandomMovie());
            logger.info(i + "movie generated");
        }

        model.addAttribute(MOVIE_LIST_ATTRIBUTE_MODEL_NAME, movieList);
        return RANDOM_MOVIE_LIST_VIEW;
    }
}

package marustuff.movierandomizer;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class MovieRandomizerController {
    private static final String GENERIC_ERROR_VIEW = "error";
    @Autowired
    private final OmbdService ombdService;

    @GetMapping("/movies/{imdbId}")
    public String getMovieByImdbId(@PathVariable String imdbId, Model model) {
        try {
            return ombdService.getMovieByIdView(imdbId, model);
        } catch (MovieNotFoundException e) {
            return GENERIC_ERROR_VIEW;
        }
    }

    @GetMapping("/movies/random")
    public String getRandomMovie(Model model) {
        return ombdService.getRandomMovieView(model);
    }

    @GetMapping("/movies/randomlist")
    public String getRandomMovieList(Model model) {
        return ombdService.getRandomMovieListView(model);
    }
}

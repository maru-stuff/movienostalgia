package marustuff.movienostalgia;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class IndexController {
    private static final String GENERIC_ERROR_VIEW = "error";
    @Autowired
    private final OmbdService ombdService;

    @GetMapping("/byid")
    public String doStuff(@RequestParam("imdbid") String imdbId, Model model){
            try{
                return ombdService.getMovieByIdView(imdbId,model);
            } catch(Exception e){
                return GENERIC_ERROR_VIEW;
            }

    }
    @GetMapping("/random")
    public String getRandom(Model model){
        try{
            return ombdService.getRandomMovieView(model);
        } catch(Exception e){
            return GENERIC_ERROR_VIEW;
        }
    }

    @GetMapping("/")
    public String getRandomList(Model model){
        try{
            return ombdService.getRandomMovieListView(model);
        }catch(Exception e){
            return GENERIC_ERROR_VIEW;
        }
    }
}

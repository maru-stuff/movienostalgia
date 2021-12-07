package marustuff.movierandomizer;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Movie {
    @JsonProperty("Title")
    String title;
    @JsonProperty("Year")
    String year;
    @JsonProperty("Genre")
    String genre;
    @JsonProperty("Plot")
    String plot;
    @JsonProperty("Poster")
    String poster;
}

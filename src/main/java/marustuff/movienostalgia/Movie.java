package marustuff.movienostalgia;

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
    @JsonProperty("Rated")
    String rated;
    @JsonProperty("Released")
    String released;
    @JsonProperty("Runtime")
    String runtime;
    @JsonProperty("Genre")
    String genre;
    @JsonProperty("Director")
    String director;
    @JsonProperty("Writer")
    String writer;
    @JsonProperty("Actors")
    String actors;
    @JsonProperty("Plot")
    String plot;
    @JsonProperty("Language")
    String language;
    @JsonProperty("Country")
    String country;
    @JsonProperty("Awards")
    String awards;
    @JsonProperty("Poster")
    String poster;
}

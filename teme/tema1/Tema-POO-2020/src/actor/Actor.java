package actor;

import fileio.ActorInputData;
import video.Movie;
import video.Serial;

import java.awt.image.AreaAveragingScaleFilter;
import java.util.ArrayList;
import java.util.Map;

public class Actor {
    /**
     * actor name
     */
    private String name;
    /**
     * description of the actor's career
     */
    private String careerDescription;
    /**
     * videos starring actor
     */
    private ArrayList<String> filmography;
    /**
     * awards won by the actor
     */
    private Map<ActorsAwards, Integer> awards;

    public Actor(ActorInputData actorData) {
        this.name = actorData.getName();
        this.careerDescription = actorData.getCareerDescription();
        this.filmography = actorData.getFilmography();
        this.awards = actorData.getAwards();
    }

    public Double getRating(ArrayList<Movie> movieList, ArrayList<Serial> serialList) {
        double rating = 0.0;
        int ratingCount = 0;
        for (String video : this.filmography) {
            for (Movie movie : movieList) {
                if (movie.getCast().contains(this.name)) {
                    rating += movie.getGrade();
                    ratingCount++;
                }
            }
            for (Serial serial : serialList) {
                if (serial.getCast().contains(this.name)) {
                    rating += serial.getGrade();
                    ratingCount++;
                }
            }
            if (ratingCount != 0) {
                rating /= ratingCount;
            }
        }
        return rating;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public ArrayList<String> getFilmography() {
        return filmography;
    }

    public void setFilmography(final ArrayList<String> filmography) {
        this.filmography = filmography;
    }

    public Map<ActorsAwards, Integer> getAwards() {
        return awards;
    }

    public String getCareerDescription() {
        return careerDescription;
    }

    public void setCareerDescription(final String careerDescription) {
        this.careerDescription = careerDescription;
    }

    @Override
    public String toString() {
        return "ActorData{"
                + "name='" + name + '\''
                + ", careerDescription='"
                + careerDescription + '\''
                + ", filmography=" + filmography + '}';
    }
}

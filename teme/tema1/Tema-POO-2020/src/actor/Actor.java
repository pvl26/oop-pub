package actor;

import fileio.ActorInputData;
import video.Movie;
import video.Serial;

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

    /**
     * Actor constructor
     * @param actorData actor data
     */
    public Actor(final ActorInputData actorData) {
        this.name = actorData.getName();
        this.careerDescription = actorData.getCareerDescription();
        this.filmography = actorData.getFilmography();
        this.awards = actorData.getAwards();
    }

    /**
     * Get mean of rating of movies and show the actor starred in
     * @param movieList list of movies
     * @param serialList list of shows
     * @return mean in Double
     */
    public Double getRating(final ArrayList<Movie> movieList,
                            final ArrayList<Serial> serialList) {
        double rating = 0.0;
        int ratingCount = 0;
        for (String video : this.filmography) {
            for (Movie movie : movieList) {
                if (movie.getTitle().equals(video)) {
                    rating += movie.getGrade();
                    if (movie.getGrade() != 0.0) {
                        ratingCount++;
                    }
                }
            }
            for (Serial serial : serialList) {
                if (serial.getTitle().equals(video)) {
                    rating += serial.getGrade();
                    if (serial.getGrade() != 0.0) {
                        ratingCount++;
                    }
                }
            }
        }
        if (ratingCount != 0) {
            rating /= ratingCount;
        }
        return rating;
    }

    /**
     * Get name of actor
     * @return String
     */
    public String getName() {
        return name;
    }

    /**
     * Set actors name
     * @param name of actor as String
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * Get list of films and shows the actor starred in
     * @return list of shows and movies
     */
    public ArrayList<String> getFilmography() {
        return filmography;
    }

    /**
     * Set filmography list
     * @param filmography list of Strings with movie and show names
     */
    public void setFilmography(final ArrayList<String> filmography) {
        this.filmography = filmography;
    }

    /**
     * Get awards list
     * @return Map of ActorAwards and their count
     */
    public Map<ActorsAwards, Integer> getAwards() {
        return awards;
    }

    /**
     * Get career description
     * @return String of carrer description
     */
    public String getCareerDescription() {
        return careerDescription;
    }

    /**
     * Method to set career description
     * @param careerDescription String containing career description
     */
    public void setCareerDescription(final String careerDescription) {
        this.careerDescription = careerDescription;
    }

    /**
     * ToString method
     * @return String with actor fields data
     */
    @Override
    public String toString() {
        return "ActorData{"
                + "name='" + name + '\''
                + ", careerDescription='"
                + careerDescription + '\''
                + ", filmography=" + filmography + '}';
    }
}

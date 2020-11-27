package main;

import action.Action;
import action.Queries;
import actor.Actor;
import org.json.JSONObject;
import user.User;
import video.Movie;
import video.Serial;
import checker.Checkstyle;
import checker.Checker;
import common.Constants;
import fileio.Input;
import fileio.InputLoader;
import fileio.Writer;
import org.json.simple.JSONArray;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * The entry point to this homework. It runs the checker that tests your implentation.
 */
public final class Main {
    /**
     * for coding style
     */
    private Main() {
    }

    /**
     * Call the main checker and the coding style checker
     * @param args from command line
     * @throws IOException in case of exceptions to reading / writing
     */
    public static void main(final String[] args) throws IOException {
        File directory = new File(Constants.TESTS_PATH);
        Path path = Paths.get(Constants.RESULT_PATH);
        if (!Files.exists(path)) {
            Files.createDirectories(path);
        }

        File outputDirectory = new File(Constants.RESULT_PATH);

        Checker checker = new Checker();
        checker.deleteFiles(outputDirectory.listFiles());

        for (File file : Objects.requireNonNull(directory.listFiles())) {

            String filepath = Constants.OUT_PATH + file.getName();
            File out = new File(filepath);
            boolean isCreated = out.createNewFile();
            if (isCreated) {
                action(file.getAbsolutePath(), filepath);
            }
        }

        checker.iterateFiles(Constants.RESULT_PATH, Constants.REF_PATH, Constants.TESTS_PATH);
        Checkstyle test = new Checkstyle();
        test.testCheckstyle();
    }

    /**
     * @param filePath1 for input file
     * @param filePath2 for output file
     * @throws IOException in case of exceptions to reading / writing
     */
    public static void action(final String filePath1,
                              final String filePath2) throws IOException {
        InputLoader inputLoader = new InputLoader(filePath1);
        Input input = inputLoader.readData();

        Writer fileWriter = new Writer(filePath2);
        JSONArray arrayResult = new JSONArray();

        //TODO add here the entry point to your implementation
        ArrayList<Movie> movieList = new ArrayList<>();
        input.getMovies().forEach(movieInputData -> {
            Movie movie = new Movie(movieInputData);
            movieList.add(movie);
        });

        ArrayList<Serial> serialList = new ArrayList<>();
        input.getSerials().forEach(serialInputData -> {
            Serial serial = new Serial(serialInputData);
            serialList.add(serial);
        });

        ArrayList<User> userList = new ArrayList<>();
        input.getUsers().forEach(userInputData -> {
            User user =  new User(userInputData, movieList, serialList);
            userList.add(user);
        });

        ArrayList<Action> actionList = new ArrayList<>();
        input.getCommands().forEach(commandInputData -> {
            Action action = new Action(commandInputData);
            actionList.add(action);
        });

        ArrayList<Actor> actorsList = new ArrayList<>();
        input.getActors().forEach(actorInputData -> {
            Actor actor = new Actor(actorInputData);
            actorsList.add(actor);
        });

        Stream<Action> actionStream = actionList.stream();

        actionStream.forEachOrdered(action -> {
            switch (action.getActionType().toLowerCase()) {
                case "command" -> {
                    User user = userList.stream().filter(usr -> usr.getUsername().equals(action.getUsername())).findFirst().orElse(null);
                    if (user == null) { return; }
                    JSONObject jsonObject = new JSONObject();
                    switch (action.getType().toLowerCase()) {
                        case "favorite" -> jsonObject = user.addToFavoriteMovies(action.getActionId(), action.getTitle());
                        case "view" -> jsonObject = user.viewVideo(action.getActionId(), action.getTitle(), movieList, serialList);
                        case "rating" -> jsonObject = user.addRating(action.getActionId(), action.getTitle(), action.getGrade(), action.getSeasonNumber(), movieList, serialList);
                    }
                    arrayResult.add(jsonObject);
                }
                case "query" -> {
                    JSONObject jsonObject = new JSONObject();
                    switch (action.getObjectType().toLowerCase()) {
                        case "actors" -> {
                            switch (action.getCriteria().toLowerCase()) {
                                case "average" -> jsonObject = Queries.averageActorQuery(action.getActionId(), actorsList, action.getNumber(), action.getSortType(), action.getFilters(), movieList, serialList);
                                case "awards" -> jsonObject = Queries.awardsActorQuery(action.getActionId(), actorsList, action.getNumber(), action.getSortType(), action.getFilters(), movieList, serialList);
                                case "filter_description" -> jsonObject = Queries.filtersActorQuery(action.getActionId(), actorsList, action.getNumber(), action.getSortType(), action.getFilters(), movieList, serialList);
                            }
                        }
                        case "movies" -> {
                            switch (action.getCriteria().toLowerCase()) {
                                case "ratings" -> jsonObject = Queries.bestRatedMoviesQuery(action.getActionId(), movieList, action.getNumber(), action.getSortType(), action.getFilters());
                                case "favorite" -> jsonObject = Queries.favoriteMoviesQuery(action.getActionId(), movieList, action.getNumber(), action.getSortType(), action.getFilters(), userList);
                                case "longest" -> jsonObject = Queries.longestMoviesQuery(action.getActionId(), movieList, action.getNumber(), action.getSortType(), action.getFilters());
                                case "most_viewed" -> jsonObject = Queries.mostViewedMoviesQuery(action.getActionId(), movieList, action.getNumber(), action.getSortType(), action.getFilters());
                            }
                        }
                        case "shows" -> {
                            switch (action.getCriteria().toLowerCase()) {
                                case "ratings" -> jsonObject = Queries.bestRatedSerialsQuery(action.getActionId(), serialList, action.getNumber(), action.getSortType(), action.getFilters());
                                case "favorite" -> jsonObject = Queries.favoriteSerialsQuery(action.getActionId(), serialList, action.getNumber(), action.getSortType(), action.getFilters(), userList);
                                case "longest" -> jsonObject = Queries.longestSerialsQuery(action.getActionId(), serialList, action.getNumber(), action.getSortType(), action.getFilters());
                                case "most_viewed" -> jsonObject = Queries.mostViewedSerialsQuery(action.getActionId(), serialList, action.getNumber(), action.getSortType(), action.getFilters());
                            }
                        }
                        case "users" -> jsonObject = Queries.userQuery(action.getActionId(), userList, action.getNumber(), action.getSortType());
                    }
                    arrayResult.add(jsonObject);
                }
                case "recommendation" -> {
                    User user = userList.stream().filter(usr -> usr.getUsername().equals(action.getUsername())).findFirst().orElse(null);
                    if (user == null) { return; }
                    JSONObject jsonObject = new JSONObject();
                    switch (action.getType().toLowerCase()) {
                        case "standard" -> jsonObject = user.standardSearch(action.getActionId(), movieList, serialList);
                        case "best_unseen" -> jsonObject = user.bestUnseen(action.getActionId(), movieList, serialList);
                        case "popular" -> jsonObject = user.popularRecommendation(action.getActionId(), movieList, serialList);
                        case "favorite" -> jsonObject = user.favoriteRecommendation(action.getActionId(), movieList, serialList, userList);
                        case "search" -> jsonObject = user.premiumSearch(action.getActionId(), movieList, serialList, action.getGenre());
                    }
                    arrayResult.add(jsonObject);
                }
            }
        });
        fileWriter.closeJSON(arrayResult);
    }
}

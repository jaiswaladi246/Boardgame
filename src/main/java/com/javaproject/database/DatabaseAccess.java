package com.javaproject.database;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Repository;

import com.javaproject.beans.BoardGame;
import com.javaproject.beans.Review;

import lombok.AllArgsConstructor;
import lombok.Data;

@Repository
// @AllArgsConstructor
public class DatabaseAccess {

    // autowired using AllArgsConstructor
    @Autowired
    private NamedParameterJdbcTemplate jdbc;

    public List<String> getAuthorities() {

        MapSqlParameterSource namedParameters = new MapSqlParameterSource();

        String query = "SELECT DISTINCT authority FROM authorities";

        List<String> authorities = jdbc.queryForList(query, namedParameters, String.class);

        return authorities;
    }

    public List<BoardGame> getBoardGames() {

        String query = "SELECT * FROM boardgames";

        BeanPropertyRowMapper boardgameMapper = new BeanPropertyRowMapper<>(BoardGame.class);

        List<BoardGame> boardgames = jdbc.query(query, boardgameMapper);
        return boardgames;
    }

    public BoardGame getBoardGame(Long id) {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();

        String query = "SELECT * FROM boardgames WHERE id = :id";
        namedParameters.addValue("id", id);
        BeanPropertyRowMapper boardgameMapper = new BeanPropertyRowMapper<>(BoardGame.class);
        List<BoardGame> boardgames = jdbc.query(query, namedParameters, boardgameMapper);
        if (boardgames.isEmpty()) {
            return null;
        } else {
            return boardgames.get(0);
        }
    }

    public List<Review> getReviews(Long id) {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();

        String query = "SELECT * FROM reviews WHERE gameId = :id";
        namedParameters.addValue("id", id);
        BeanPropertyRowMapper reviewMapper = new BeanPropertyRowMapper<>(Review.class);
        List<Review> reviews = jdbc.query(query, namedParameters, reviewMapper);
        if (reviews.isEmpty()) {
            return null;
        } else {
            return reviews;
        }
    }

    public Long addBoardGame(BoardGame boardgame) {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        String query = "INSERT INTO boardgames (name, level, minPlayers, maxPlayers, gameType) VALUES (:name, :level, :minPlayers, :maxPlayers, :gameType)";
        namedParameters
                .addValue("name", boardgame.getName())
                .addValue("level", boardgame.getLevel())
                .addValue("minPlayers", boardgame.getMinPlayers())
                .addValue("maxPlayers", boardgame.getMaxPlayers())
                .addValue("gameType", boardgame.getGameType());
        KeyHolder generatedKey = new GeneratedKeyHolder();
        int returnValue = jdbc.update(query, namedParameters, generatedKey);
        Long boardGameId = (Long) generatedKey.getKey();
        return (returnValue > 0) ? boardGameId : 0;
    }

    public int addReview(Review review) {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        String query = "INSERT INTO reviews (gameId, text) VALUES (:gameId, :text)";
        namedParameters.addValue("gameId", review.getGameId())
                .addValue("text", review.getText());

        return jdbc.update(query, namedParameters);
    }

    public int deleteReview(Long id) {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        String query = "DELETE FROM reviews WHERE id = :id";
        namedParameters.addValue("id", id);
        return jdbc.update(query, namedParameters);
    }

    public Review getReview(Long id) {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();

        String query = "SELECT * FROM reviews WHERE id = :id";
        namedParameters.addValue("id", id);
        BeanPropertyRowMapper reviewMapper = new BeanPropertyRowMapper<>(Review.class);
        List<Review> reviews = jdbc.query(query, namedParameters, reviewMapper);
        if (reviews.isEmpty()) {
            return null;
        } else {
            return reviews.get(0);
        }
    }

    public int editReview(Review review) {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();

        String query = "UPDATE reviews SET text = :text "
                + "WHERE id = :id";

        namedParameters
                .addValue("text", review.getText())
                .addValue("id", review.getId());
        return jdbc.update(query, namedParameters);
    }
}

package com.javaproject;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import com.javaproject.beans.BoardGame;
import com.javaproject.beans.Review;
import com.javaproject.database.DatabaseAccess;

@SpringBootTest
@AutoConfigureMockMvc
class TestDatabase {

    private DatabaseAccess da;

    @Autowired
    public void setDatabase(DatabaseAccess da) {
        this.da = da;
    }

    @Test
    public void testDatabaseAddBoardGame() {
        BoardGame boardGame = new BoardGame();
        boardGame.setName("onecard");
        boardGame.setLevel(1);
        boardGame.setMinPlayers(2);
        boardGame.setMaxPlayers("+");
        boardGame.setGameType("Party Game");

        int originalSize = da.getBoardGames().size();

        da.addBoardGame(boardGame);
        int newSize = da.getBoardGames().size();

        assertEquals(newSize, originalSize + 1);
    }

    // @Test
    // public void testDatabaseAddReview() {
    // List<BoardGame> boardGames = da.getBoardGames();
    // Long boardgameId = boardGames.get(0).getId();

    // Review review = new Review();
    // review.setGameId(boardgameId);
    // review.setText("This is review text");

    // int originalSize = da.getReviews(boardgameId).size();

    // da.addReview(review);
    // int newSize = da.getReviews(boardgameId).size();

    // assertEquals(newSize, originalSize + 1);
    // }

    // @Test
    // public void testDatabaseDeleteReview() {
    // List<BoardGame> boardGames = da.getBoardGames();
    // Long boardgameId = boardGames.get(0).getId();

    // List<Review> reviews = da.getReviews(boardgameId);
    // Long reviewId = reviews.get(0).getId();

    // int originalSize = da.getReviews(boardgameId).size();

    // da.deleteReview(reviewId);
    // int newSize = da.getReviews(boardgameId).size();
    // assertEquals(newSize, originalSize - 1);
    // }
}

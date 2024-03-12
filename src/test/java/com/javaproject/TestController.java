package com.javaproject;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;

import com.javaproject.beans.BoardGame;
import com.javaproject.beans.Review;
import com.javaproject.database.DatabaseAccess;

@SpringBootTest
@AutoConfigureMockMvc
class TestController {

    private DatabaseAccess da;
    private MockMvc mockMvc;

    @Autowired
    public void setDatabase(DatabaseAccess da) {
        this.da = da;
    }

    @Autowired
    public void setMockMvc(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    @Test
    public void testRoot() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"));
    }

    @Test
    public void testAddBoardGame() throws Exception {
        LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();

        requestParams.add("name", "onecard");
        requestParams.add("level", "1");
        requestParams.add("minPlayers", "2");
        requestParams.add("maxPlayers", "+");
        requestParams.add("gameType", "Party Game");

        int origSize = da.getBoardGames().size();
        mockMvc.perform(post("/boardgameAdded").params(requestParams))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/"))
                .andDo(print());
        int newSize = da.getBoardGames().size();
        assertEquals(newSize, origSize + 1);
    }

    @Test
    public void testEditReview() throws Exception {
        List<BoardGame> boardGames = da.getBoardGames();
        Long boardgameId = boardGames.get(0).getId();

        List<Review> reviews = da.getReviews(boardgameId);
        Review review = reviews.get(0);
        Long reviewId = review.getId();

        review.setText("Edited text");

        mockMvc.perform(post("/reviewAdded").flashAttr("review", review))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/" + review.getGameId() + "/reviews"));

        review = da.getReview(reviewId);
        assertEquals(review.getText(), "Edited text");
    }

    @Test
    public void testDeleteReview() throws Exception {
        List<BoardGame> boardGames = da.getBoardGames();
        Long boardgameId = boardGames.get(0).getId();

        List<Review> reviews = da.getReviews(boardgameId);
        Long reviewId = reviews.get(0).getId();

        int origSize = reviews.size();

        mockMvc.perform(get("/deleteReview/{id}", reviewId))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/" + boardgameId + "/reviews"));

        int newSize = da.getReviews(boardgameId).size();

        assertEquals(newSize, origSize - 1);
    }
}

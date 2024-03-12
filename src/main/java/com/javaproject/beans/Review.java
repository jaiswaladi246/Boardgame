package com.javaproject.beans;

import lombok.Data;

@Data
public class Review {

    private Long id;
    private Long gameId;
    private String text;
}

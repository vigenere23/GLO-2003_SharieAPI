package com.github.glo2003.models;

import com.github.glo2003.exceptions.ParameterParsingException;

import java.util.Date;

public class Rating {

    private Date date;
    private Integer score;


    public Rating(){

    }

    public Rating(Integer _score) throws ParameterParsingException {
        setScore(_score);
        date = new Date();
    }

    public Integer getScore() {
        return score;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setScore(Integer score) throws ParameterParsingException {
        if(score >= 0 && score <= 5){
            this.score = score;
        } else{
            throw new ParameterParsingException("The score must be between 0 and 5.",  "Integer");
        }
    }
}

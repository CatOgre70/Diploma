package ru.diploma.project.jd6team5.model;

import ru.diploma.project.jd6team5.dto.Comment;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ResponseWrapperComment {
    private int count = 0;
    private List<Comment> results = new ArrayList<Comment>();

    public ResponseWrapperComment(int count, List<Comment> results) {
        this.count = count;
        this.results = results;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<Comment> getResults() {
        return results;
    }

    public void setResults(List<Comment> results) {
        this.results = results;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ResponseWrapperComment)) return false;
        ResponseWrapperComment that = (ResponseWrapperComment) o;
        return Objects.equals(getResults(), that.getResults());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getResults());
    }

    @Override
    public String toString() {
        return "ResponseWrapperComment{" +
                "count=" + count +
                ", results=" + results +
                '}';
    }
}

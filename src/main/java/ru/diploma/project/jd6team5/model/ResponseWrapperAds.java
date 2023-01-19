package ru.diploma.project.jd6team5.model;

import ru.diploma.project.jd6team5.dto.Advertisement;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ResponseWrapperAds {
    private int count = 0;
    private List<Advertisement> results = new ArrayList<Advertisement>();

    public ResponseWrapperAds(int count, List<Advertisement> results) {
        this.count = count;
        this.results = results;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<Advertisement> getResults() {
        return results;
    }

    public void setResults(List<Advertisement> results) {
        this.results = results;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ResponseWrapperAds)) return false;
        ResponseWrapperAds that = (ResponseWrapperAds) o;
        return Objects.equals(getResults(), that.getResults());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getResults());
    }

    @Override
    public String toString() {
        return "ResponseWrapperAds{" +
                "count=" + count +
                ", results=" + results +
                '}';
    }
}

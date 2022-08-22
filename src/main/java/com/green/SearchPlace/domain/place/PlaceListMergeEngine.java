package com.green.SearchPlace.domain.place;

import com.green.SearchPlace.domain.response.place.ResponsePlace;

import java.util.ArrayList;
import java.util.List;

public class PlaceListMergeEngine <M extends Place,S extends Place>{

    private final List<M> mainPlaceList;
    private final List<S> subPlaceList;
    private final List<ResponsePlace> mergedPlaceList = new ArrayList<>();

    public PlaceListMergeEngine(List<M> mainPlaceList, List<S> subPlaceList) {
        this.mainPlaceList = new ArrayList<>(mainPlaceList);
        this.subPlaceList = new ArrayList<>(subPlaceList);
    }

    public List<ResponsePlace> mergedResponsePlaceList() {
        addPriorityPlaces();
        addRemainingResponseMainPlaces();
        addRemainingResponseSubPlaces();
        return mergedPlaceList;
    }

    private void addPriorityPlaces() {
        for (M mainPlaceList : mainPlaceList) {
            for (S subPlaceList : subPlaceList) {
                ResponsePlace mainResponsePlace = mainPlaceList.toResponsePlace();
                ResponsePlace subResponsePlace = subPlaceList.toResponsePlace();
                if (mainResponsePlace.equals(subResponsePlace)){
                    mergedPlaceList.add(mainResponsePlace);
                    mainPlaceList.setTitle("");
                    subPlaceList.setTitle("");
                    break;
                }
            }
        }
    }

    private void addRemainingResponseMainPlaces() {
        mainPlaceList.stream()
                .filter(place -> !place.getTitle().equals(""))
                .forEach(place -> mergedPlaceList.add(place.toResponsePlace()));
    }

    private void addRemainingResponseSubPlaces() {
        subPlaceList.stream()
                .filter(place -> !place.getTitle().equals(""))
                .forEach(place -> mergedPlaceList.add(place.toResponsePlace()));
    }

}

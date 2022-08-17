package com.green.SearchPlace.domain;

import java.util.ArrayList;
import java.util.List;

public class PlaceListMergeEngine {

    private final List<Place> kakaoPlaceList;
    private final List<Place> naverPlaceList;
    private final List<Place> mergedPlaceList;

    public PlaceListMergeEngine(List<Place> kakaoPlaceList, List<Place> naverPlaceList) {
        this.kakaoPlaceList = new ArrayList<>(kakaoPlaceList);
        this.naverPlaceList = new ArrayList<>(naverPlaceList);
        this.mergedPlaceList = new ArrayList<>();
    }

    public void merge() {
        for (Place kakaoPlace : kakaoPlaceList) {
            for (Place naverPlace : naverPlaceList) {
                Place kakaoResponsePlace = kakaoPlace.toResponsePlace();
                Place naverResponsePlace = naverPlace.toResponsePlace();
                if (kakaoResponsePlace.equals(naverResponsePlace)){
                    mergedPlaceList.add(kakaoResponsePlace);
                    kakaoPlace.setTitle("");
                    naverPlace.setTitle("");
                    break;
                }
            }
        }
        for (Place kakaoPlace : kakaoPlaceList) {
            if (!kakaoPlace.getTitle().equals("")) {
                mergedPlaceList.add(kakaoPlace.toResponsePlace());
            }
        }
        for (Place naverPlace : naverPlaceList) {
            if (!naverPlace.getTitle().equals("")) {
                mergedPlaceList.add(naverPlace.toResponsePlace());
            }
        }
    }

    public List<Place> getMergedPlaceList() {
        return mergedPlaceList;
    }

}

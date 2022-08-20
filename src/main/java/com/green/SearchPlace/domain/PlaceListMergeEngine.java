package com.green.SearchPlace.domain;

import java.util.ArrayList;
import java.util.List;

public class PlaceListMergeEngine {

    private final List<KakaoPlace> kakaoPlaceList;
    private final List<NaverPlace> naverPlaceList;
    private final List<ResponsePlace> mergedPlaceList = new ArrayList<>();

    public PlaceListMergeEngine(List<KakaoPlace> kakaoPlaceList, List<NaverPlace> naverPlaceList) {
        this.kakaoPlaceList = new ArrayList<>(kakaoPlaceList);
        this.naverPlaceList = new ArrayList<>(naverPlaceList);
    }

    public List<ResponsePlace> merge() {
        for (Place kakaoPlace : kakaoPlaceList) {
            for (Place naverPlace : naverPlaceList) {
                ResponsePlace kakaoResponsePlace = kakaoPlace.toResponsePlace();
                ResponsePlace naverResponsePlace = naverPlace.toResponsePlace();
                if (kakaoResponsePlace.equals(naverResponsePlace)){
                    mergedPlaceList.add(kakaoResponsePlace);
                    kakaoPlace.setTitle("");
                    naverPlace.setTitle("");
                    break;
                }
            }
        }
        addRemainingResponseKakaoPlaces();
        addRemainingResponseNaverPlaces();
        return mergedPlaceList;
    }

    private void addRemainingResponseKakaoPlaces() {
        kakaoPlaceList.stream()
                .filter(place -> !place.getTitle().equals(""))
                .forEach(place -> mergedPlaceList.add(place.toResponsePlace()));
    }

    private void addRemainingResponseNaverPlaces() {
        naverPlaceList.stream()
                .filter(place -> !place.getTitle().equals(""))
                .forEach(place -> mergedPlaceList.add(place.toResponsePlace()));
    }

    public List<ResponsePlace> getMergedPlaceList() {
        return mergedPlaceList;
    }

}

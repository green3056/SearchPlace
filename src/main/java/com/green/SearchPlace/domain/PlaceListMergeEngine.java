package com.green.SearchPlace.domain;

import java.util.ArrayList;
import java.util.List;

public class PlaceListMergeEngine {

    private final List<Place> kakaoPlaceList;
    private final List<Place> naverPlaceList;
    private final List<ResponsePlace> mergedPlaceList = new ArrayList<>();

    public PlaceListMergeEngine(List<Place> kakaoPlaceList, List<Place> naverPlaceList) {
        this.kakaoPlaceList = new ArrayList<>(kakaoPlaceList);
        this.naverPlaceList = new ArrayList<>(naverPlaceList);
    }

    public void merge() {
        for (Place kakaoPlace : kakaoPlaceList) {
            for (Place naverPlace : naverPlaceList) {
                ResponsePlace kakaoResponsePlace = kakaoPlace.toResponsePlace("kakao");
                ResponsePlace naverResponsePlace = naverPlace.toResponsePlace("naver");
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
                mergedPlaceList.add(kakaoPlace.toResponsePlace("kakao"));
            }
        }
        for (Place naverPlace : naverPlaceList) {
            if (!naverPlace.getTitle().equals("")) {
                mergedPlaceList.add(naverPlace.toResponsePlace("naver"));
            }
        }
    }

    public List<ResponsePlace> getMergedPlaceList() {
        return mergedPlaceList;
    }

}

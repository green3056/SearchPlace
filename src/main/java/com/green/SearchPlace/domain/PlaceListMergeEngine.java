package com.green.SearchPlace.domain;

import java.util.ArrayList;
import java.util.List;

public class PlaceListMergeEngine {

    private final List<KakaoPlace> kakaoPlaceList;
    private final List<NaverPlace> naverPlaceList;
    private final List<ResponsePlace> mergedPlaceList;

    public PlaceListMergeEngine(List<KakaoPlace> kakaoPlaceList, List<NaverPlace> naverPlaceList) {
        this.kakaoPlaceList = new ArrayList<>(kakaoPlaceList);
        this.naverPlaceList = new ArrayList<>(naverPlaceList);
        this.mergedPlaceList = new ArrayList<>();
    }

    public void merge() {
        for (KakaoPlace kakaoPlace : kakaoPlaceList) {
            for (NaverPlace naverPlace : naverPlaceList) {
                Address kakaoPlaceAddress = new Address(kakaoPlace.getAddress_name());
                Address naverPlaceAddress = new Address(naverPlace.getAddress());
                if (kakaoPlaceAddress.equals(naverPlaceAddress)){
                    mergedPlaceList.add(new ResponsePlace(kakaoPlace));
                    kakaoPlace.setPlace_name("");
                    naverPlace.setTitle("");
                    break;
                }
            }
        }
        for (KakaoPlace kakaoPlace : kakaoPlaceList) {
            if (!kakaoPlace.getPlace_name().equals("")) {
                mergedPlaceList.add(new ResponsePlace(kakaoPlace));
            }
        }
        for (NaverPlace naverPlace : naverPlaceList) {
            if (!naverPlace.getTitle().equals("")) {
                mergedPlaceList.add(new ResponsePlace(naverPlace));
            }
        }
    }

    public List<ResponsePlace> getMergedPlaceList() {
        return mergedPlaceList;
    }

}

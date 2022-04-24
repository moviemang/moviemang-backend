package com.moviemang.coreutils.model.vo;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class HttpClientRequest {

    private String url = "";
    private Map<String, Object> data = new HashMap<>();
    private Object bodyData;
    private Map<String, String> header = new HashMap<>();
    private String routeParamKey = "";
    private String routeParamValue = "";

}

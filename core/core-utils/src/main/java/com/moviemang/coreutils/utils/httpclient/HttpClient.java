package com.moviemang.coreutils.utils.httpclient;

import com.fasterxml.jackson.core.JsonProcessingException;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.ObjectMapper;
import com.mashape.unirest.http.Unirest;
import com.moviemang.coreutils.model.vo.HttpClientRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@Slf4j
public class HttpClient<T> {

    public HttpClient() {
        Unirest.setTimeouts(5000, 3000);
        Unirest.setObjectMapper(new ObjectMapper() {
            com.fasterxml.jackson.databind.ObjectMapper mapper
                    = new com.fasterxml.jackson.databind.ObjectMapper();

            public String writeValue(Object value) {
                try {
                    return mapper.writeValueAsString(value);
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
            }

            public <T> T readValue(String value, Class<T> valueType) {
                try {
                    return mapper.readValue(value, valueType);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

    }

    public static String get(HttpClientRequest httpClientRequest) throws Exception {
        Map<String, Object> params = httpClientRequest.getData().entrySet().stream().filter(d -> !StringUtils.isEmpty(d.getValue())).collect(Collectors.toMap(o -> o.getKey(), o -> o.getValue()));
        HttpResponse<JsonNode> response = Unirest.get(httpClientRequest.getUrl())
                .headers(httpClientRequest.getHeader())
                .queryString(params)
                .asJson();

        return response.getBody().toString();
    }

    public static <T> T getGenieric(HttpClientRequest httpClientRequest, Class<T> classOfT) throws Exception {
        Map<String, Object> params = httpClientRequest.getData().entrySet().stream().filter(d -> !StringUtils.isEmpty(d.getValue())).collect(Collectors.toMap(o -> o.getKey(), o -> o.getValue()));

        HttpResponse<T> response = Unirest.get(httpClientRequest.getUrl())
                .headers(httpClientRequest.getHeader())
                .queryString(params)
                .asObject(classOfT);

        return response.getBody();
    }

    public static <T> T post(HttpClientRequest httpClientRequest, Class<T> classOfT) throws Exception {
        HttpResponse<T> response = Unirest.post(httpClientRequest.getUrl())
                .headers(httpClientRequest.getHeader())
                .routeParam(httpClientRequest.getRouteParamKey(), httpClientRequest.getRouteParamValue())
                .body(httpClientRequest.getBodyData())
                .asObject(classOfT);

        if (200 == response.getStatus()) {
            return response.getBody();
        } else {
            log.info("{}", httpClientRequest);
            throw new Exception();
        }
    }

    public static <T> T postBodyWithoutTryCatch(HttpClientRequest httpClientRequest, Class<T> classOfT) throws Exception {
        HttpResponse<T> response = Unirest.post(httpClientRequest.getUrl())
                .headers(httpClientRequest.getHeader())
                .body(httpClientRequest.getBodyData())
                .asObject(classOfT);

        return response.getBody();
    }


    public static <T> T getWithRouteParam(HttpClientRequest httpClientRequest, Class<T> classOfT) throws Exception {
        HttpResponse<T> response = Unirest.get(httpClientRequest.getUrl())
                .headers(httpClientRequest.getHeader())
                .routeParam(httpClientRequest.getRouteParamKey(), httpClientRequest.getRouteParamValue())
                .queryString(httpClientRequest.getData())
                .asObject(classOfT);

        return response.getBody();
    }


    public static String getWithThreeRouteParam(HttpClientRequest httpClientRequest) throws Exception {
        List<String> keyList = Arrays.asList(httpClientRequest.getRouteParamKey().split(","));
        List<String> valueList = Arrays.asList(httpClientRequest.getRouteParamValue().split(","));

        HttpResponse<JsonNode> response = Unirest.get(httpClientRequest.getUrl())
                .headers(httpClientRequest.getHeader())
                .routeParam(keyList.get(0), valueList.get(0))
                .routeParam(keyList.get(1), valueList.get(1))
                .routeParam(keyList.get(2), valueList.get(2))
                .queryString(httpClientRequest.getData())
                .asJson();

        return response.getBody().toString();
    }

    public static <T> T getWithTwoRouteParam(HttpClientRequest httpClientRequest, Class<T> classOfT) throws Exception {
        List<String> keyList = Arrays.asList(httpClientRequest.getRouteParamKey().split(","));
        List<String> valueList = Arrays.asList(httpClientRequest.getRouteParamValue().split(","));

        HttpResponse<T> response = Unirest.get(httpClientRequest.getUrl())
                .headers(httpClientRequest.getHeader())
                .routeParam(keyList.get(0), valueList.get(0))
                .routeParam(keyList.get(1), valueList.get(1))
                .queryString(httpClientRequest.getData())
                .asObject(classOfT);

        return response.getBody();
    }

}

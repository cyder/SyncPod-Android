package com.cyder.atsushi.youtubesync.server;

import com.github.tomakehurst.wiremock.junit.WireMockRule;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import org.junit.Rule;
import org.junit.Test;

import java.nio.charset.StandardCharsets;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by chigichan24 on 2018/01/23.
 */
public class SignInTest {
    @Rule
    public WireMockRule wireMockRule = new WireMockRule(8080);
    @Test
    public void post() throws Exception {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet("http://localhost:8080/__admin");
        org.apache.http.HttpResponse response = httpclient.execute(httpGet);

        httpGet = new HttpGet("http://localhost:8080/login");
        httpGet.setHeader(new BasicHeader("Accept","text/plain"));
        response = httpclient.execute(httpGet);

        assertThat(EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8), is("{\"result\":\"success\"}"));
    }

}


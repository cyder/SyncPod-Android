package com.cyder.atsushi.youtubesync.server;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit.WireMockRule;

import org.junit.Rule;
import org.junit.Test;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.matching;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;

/**
 * Created by chigichan24 on 2018/01/23.
 */
public class SignInTest {
    @Rule
    public WireMockRule wireMockRule = new WireMockRule(8080);

    @Test
    public void post() throws Exception {
        stubFor(WireMock.post(urlPathEqualTo("/login"))
                .withHeader("Content-Type", matching("application/json"))
                .willReturn(aResponse()));
    }

}


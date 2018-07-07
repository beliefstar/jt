package com.jtboot.web.config;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HttpClientConfig {

    @Value("${http.request.connectionRequestTimeout}")
    private Integer connectionRequestTimeout;

    @Value("${http.request.connectTimeout}")
    private Integer connectTimeout;

    @Value("${http.request.socketTimeout}")
    private Integer socketTimeout;

    @Value("${http.request.staleConnectionCheckEnabled}")
    private Boolean staleConnectionCheckEnabled;

    @Value("${http.pool.maxTotal}")
    private Integer maxTotal;

    @Value("${http.pool.defaultMaxPerRoute}")
    private Integer defaultMaxPerRoute;

    @Bean(destroyMethod = "close")
    public PoolingHttpClientConnectionManager httpClientConnectionManager() {
        PoolingHttpClientConnectionManager manager = new PoolingHttpClientConnectionManager();
        manager.setMaxTotal(maxTotal);
        manager.setDefaultMaxPerRoute(defaultMaxPerRoute);
        return manager;
    }

    @Bean
    public CloseableHttpClient httpClient(PoolingHttpClientConnectionManager manager) {
        HttpClientBuilder builder = HttpClientBuilder.create();
        builder.setConnectionManager(manager);
        CloseableHttpClient build = builder.build();
        return build;
    }

    @Bean
    public RequestConfig builder() {
        RequestConfig.Builder builder = RequestConfig.custom();
        builder.setConnectionRequestTimeout(connectionRequestTimeout);
        builder.setConnectTimeout(connectTimeout);
        builder.setSocketTimeout(socketTimeout);
        builder.setStaleConnectionCheckEnabled(staleConnectionCheckEnabled);
        return builder.build();
    }

}

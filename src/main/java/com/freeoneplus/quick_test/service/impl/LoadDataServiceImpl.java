package com.freeoneplus.quick_test.service.impl;

import com.freeoneplus.quick_test.pojo.BaseSchemaInfo;
import com.freeoneplus.quick_test.service.LoadDataService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.FileEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultRedirectStrategy;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
public class LoadDataServiceImpl implements LoadDataService {

    @Override
    public boolean dorisCsvStreamLoad(BaseSchemaInfo baseSchemaInfo, String tableName, ArrayList<String> dataList) {
        try {
            return loadCsv(baseSchemaInfo, tableName, dataList);
        } catch (Exception e) {
            log.error("Stream load error!");
            return false;
        }
    }

    //Build http client builder
    private final static HttpClientBuilder httpClientBuilder = HttpClients
            .custom()
            .setRedirectStrategy(new DefaultRedirectStrategy() {
                @Override
                protected boolean isRedirectable(String method) {
                    // If the connection target is FE, you need to deal with 307 redirect。
                    return true;
                }
            });

    /**
     * File import
     *
     * @param filePath
     * @throws Exception
     */
    public void loadLocalFile(String filePath, String host, int port, String user, String password, String databaseName, String tableName) throws Exception {
        File file = new File(filePath);
        try (CloseableHttpClient client = httpClientBuilder.build()) {
            String loadUrl = String.format("http://%s:%s/api/%s/%s/_stream_load",
                    host, port, databaseName, tableName);
            HttpPut put = new HttpPut(loadUrl);
            put.removeHeaders(HttpHeaders.CONTENT_LENGTH);
            put.removeHeaders(HttpHeaders.TRANSFER_ENCODING);
            put.setHeader(HttpHeaders.EXPECT, "100-continue");
            put.setHeader(HttpHeaders.AUTHORIZATION, basicAuthHeader(user, password));

            // You can set stream load related properties in the Header, here we set label and column_separator.
            put.setHeader("label", UUID.randomUUID().toString());
            put.setHeader("column_separator", ",");

            // Set up the import file. Here you can also use StringEntity to transfer arbitrary data.
            FileEntity entity = new FileEntity(file);
            put.setEntity(entity);

            try (CloseableHttpResponse response = client.execute(put)) {
                String loadResult = "";
                if (response.getEntity() != null) {
                    loadResult = EntityUtils.toString(response.getEntity());
                }

                final int statusCode = response.getStatusLine().getStatusCode();
                if (statusCode != 200) {
                    throw new IOException(String.format("Stream load failed. status: %s load result: %s",
                            statusCode, loadResult));
                }
                System.out.println("Get load result: " + loadResult);
            }
        }
    }

    /**
     * JSON import
     *
     * @throws Exception
     */
    public boolean loadCsv(BaseSchemaInfo baseSchemaInfo, String tableName, ArrayList<String> dataList) throws Exception {
        try (CloseableHttpClient client = httpClientBuilder.build()) {
            String loadUrl = String.format("http://%s:%s/api/%s/%s/_stream_load",
                    baseSchemaInfo.getHost(), baseSchemaInfo.getPort(), baseSchemaInfo.getDbName(), tableName);
            HttpPut put = new HttpPut(loadUrl);
            put.removeHeaders(HttpHeaders.CONTENT_LENGTH);
            put.removeHeaders(HttpHeaders.TRANSFER_ENCODING);
            put.setHeader(HttpHeaders.EXPECT, "100-continue");
            put.setHeader(HttpHeaders.AUTHORIZATION, basicAuthHeader(baseSchemaInfo.getUsername(), baseSchemaInfo.getPassword()));

            put.setHeader("label", UUID.randomUUID().toString());
            put.setHeader("column_separator", ",");

            String datas = dataList.stream().collect(Collectors.joining("\n"));
            StringEntity entity = new StringEntity(datas);
            put.setEntity(entity);
            try (CloseableHttpResponse response = client.execute(put)) {
                String loadResult = "";
                if (response.getEntity() != null) {
                    loadResult = EntityUtils.toString(response.getEntity());
                }
                final int statusCode = response.getStatusLine().getStatusCode();
                if (statusCode != 200) {
                    log.error(String.format("Stream load failed. status: %s load result: %s", statusCode, loadResult));
                    return false;
                }
                log.info("Get load result: " + loadResult);
                return true;
            }
        }
    }

    /**
     * Construct authentication information, the authentication method used by doris here is Basic Auth
     *
     * @param username
     * @param password
     * @return
     */
    private String basicAuthHeader(String username, String password) {
        final String tobeEncode = username + ":" + password;
        byte[] encoded = Base64.encodeBase64(tobeEncode.getBytes(StandardCharsets.UTF_8));
        return "Basic " + new String(encoded);
    }


}

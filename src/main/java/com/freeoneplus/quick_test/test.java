package com.freeoneplus.quick_test;

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

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.UUID;
public class test {

    // FE IP Address
    private final static String HOST = "storage.freeoneplus.com";
    // FE port
    private final static int PORT = 8040;
    // db name
    private final static String DATABASE = "test";
    // table name
    private final static String TABLE = "tb_td_app";
    //user name
    private final static String USER = "root";
    //user password
    private final static String PASSWD = "";
    //The path of the local file to be imported
    private final static String LOAD_FILE_NAME = "c:/es/1.csv";

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
     * @param filePath
     * @throws Exception
     */
    public void load(String filePath) throws Exception {
        File file = new File(filePath);
        try (CloseableHttpClient client = httpClientBuilder.build()) {
            String loadUrl = String.format("http://%s:%s/api/%s/%s/_stream_load",
                    HOST, PORT, DATABASE, TABLE);
            HttpPut put = new HttpPut(loadUrl);
            put.removeHeaders(HttpHeaders.CONTENT_LENGTH);
            put.removeHeaders(HttpHeaders.TRANSFER_ENCODING);
            put.setHeader(HttpHeaders.EXPECT, "100-continue");
            put.setHeader(HttpHeaders.AUTHORIZATION, basicAuthHeader(USER, PASSWD));

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
     * @param csvData
     * @throws Exception
     */
    public void loadCsv(String csvData) throws Exception {
        try (CloseableHttpClient client = httpClientBuilder.build()) {
            String loadUrl = String.format("http://%s:%s/api/%s/%s/_stream_load",
                    HOST, PORT, DATABASE, TABLE);
            HttpPut put = new HttpPut(loadUrl);
            put.removeHeaders(HttpHeaders.CONTENT_LENGTH);
            put.removeHeaders(HttpHeaders.TRANSFER_ENCODING);
            put.setHeader(HttpHeaders.EXPECT, "100-continue");
            put.setHeader(HttpHeaders.AUTHORIZATION, basicAuthHeader(USER, PASSWD));

            // You can set stream load related properties in the Header, here we set label and column_separator.
            put.setHeader("label", UUID.randomUUID().toString());
            put.setHeader("column_separator", ",");
            put.setHeader("strict_mode", "true");
            // put.setHeader("format", "json");

            // Set up the import file. Here you can also use StringEntity to transfer arbitrary data.
            StringEntity entity = new StringEntity(csvData);
            put.setEntity(entity);

            try (CloseableHttpResponse response = client.execute(put)) {
                String loadResult = "";
                if (response.getEntity() != null) {
                    loadResult = EntityUtils.toString(response.getEntity());
                }

                final int statusCode = response.getStatusLine().getStatusCode();
                if (statusCode != 200) {
                    throw new IOException(String.format("Stream load failed. status: %s load result: %s", statusCode, loadResult));
                }
                System.out.println("Get load result: " + loadResult);
            }
        }
    }

    /**
     * Construct authentication information, the authentication method used by doris here is Basic Auth
     * @param username
     * @param password
     * @return
     */
    private String basicAuthHeader(String username, String password) {
        final String tobeEncode = username + ":" + password;
        byte[] encoded = Base64.encodeBase64(tobeEncode.getBytes(StandardCharsets.UTF_8));
        return "Basic " + new String(encoded);
    }


    public static void main(String[] args) throws Exception {
        test loader = new test();
        String csvStr = "1381278743,6616489213333399552,NMiMalGcwCihzFfFCoovzcewIBQnJqyCXqWBnbJSsfgIyBhKNEjbsgYBegoj,XNzeypinHEaUEcxEWuBiVtEzitmeDwPcvSxDPLnWiiZCHAiJIqcwMSzlViAGXDHsHIPPZxuxJVIDVpCIyjaMQtnAYb,OnBZvqNjCwADQmQuZiYUyDhFMbcttKjNosMiaaUzFywTimzSGbWblCxoAcCBpSvnLEoKZIVlfaTIeebZpqjolICDDODiIzfbGdBEaiwdkqBGgzOqffxWsbTpveOLCJqbXPcNNNgoXeMyQTBlSLUmsCUTxEVhzpTCXyyIVxfxBVApYyxGEgFMtqizPIuKjEMqvpEkudNiLgYBvpdIuHuYdUBQpJyJVZISYpHqacMjDNcmKKEJiGHuFDYNWVvmekQiMNbimxicsTovsayCIJGAiWoAikhtgvuDmWZeieiGOafHfxnfcVVnHEnUIlWkpWJUAWQITSDcxbhVEsFVLEdPyiqYFYwtaWXMAdhUZtZhBdWMhADsjSKnTAcMNEaIqCxmKjCTxUYXJUOyXXlNDWEZnmeinckOGhNIdvwxzpEntqVVtPuZuukYQvinwxCSHUbAfh,ZqeEdIlbWmCwIipMuxEEkaetGJKBSbpnLAqwXMIIxWqZuLaptTAaqQHAoCfxeMLzTVqbjwdWVqlZLBHkXaIfiOIDiuacbpWltekmJINpUbWmWEoPVwWoikBuCaniVThwTkxWfcDIuUSeOHElSeaPkcKuiENWDCDTfAuIPvBsBdXOUkXldowgbUSliqITiTcByIcwdYNzusTiuTKkNQfuqUFEOzciqIZsVKeSKeNwpNiFINEHEugeTjvwfFfjKVzkVVlEGAPFgyGkwiFGooJddiLtaWkpCzIdXnKPbvDxVNGg,5,42,2044701312,1342539753,JloTvp\n";
        loader.loadCsv(csvStr);
    }
}

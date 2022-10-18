package com.freeoneplus.quick_test.service;

import com.freeoneplus.quick_test.pojo.UrlSchemaInfo;


public interface SchemaService {

    void analysisJsonFileSchema(String schemaFilePath);
    void analysisUrlSchema(UrlSchemaInfo urlSchemaInfo);
}

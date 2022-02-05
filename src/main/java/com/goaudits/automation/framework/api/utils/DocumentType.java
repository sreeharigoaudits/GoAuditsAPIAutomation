package com.goaudits.automation.framework.api.utils;


import com.goaudits.automation.framework.api.modules.Documents.Document;

public interface DocumentType {
    Document getDocument();
    void setDocument (Document document);
}

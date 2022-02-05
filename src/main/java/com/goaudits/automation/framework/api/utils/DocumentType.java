package com.goaudits.automation.framework.api.utils;


import com.goaudits.automation.framework.api.modules.salaried.Document;

public interface DocumentType {
    Document getDocument();
    void setDocument (Document document);
}

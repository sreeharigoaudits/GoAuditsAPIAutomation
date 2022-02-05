package com.goaudits.automation.framework.api.utils;


import com.goaudits.automation.framework.api.modules.loginAndRegistration.User;
import com.goaudits.automation.framework.api.modules.Documents.Document;

import groovy.util.logging.Slf4j;

@Slf4j
public class Enumerations {





    public enum RESOURCE_FILE {
        PDF("sample.pdf"), PNG("00.png"), TXT("dumpfile.dump"),MP4("video.mp4");

        private final String value;

        RESOURCE_FILE(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return value;
        }
    }
    public enum RESOURCE_FILE1 {
        PDF("sample1.pdf"), PNG("image1.PNG"), TXT("note1.txt"),Doc("Doc1.docx"),JPEG("image2.jpeg"), MOV("SampleVideo.mov");

        private final String value;

        RESOURCE_FILE1(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return value;
        }
    }


    public enum ClientType implements Authenticable {
        SUPER_ADMIN("Super Admin"), Auditor("Auditor");

        private final String value;
        private User user;
        ClientType(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return value;
        }

        @Override
        public User getUser() {
            return user;
        }

        @Override
        public void setUser(User user) {
            this.user = user;
        }


    }

    public enum DocumentTypes implements DocumentType {
        SELFIE("Selfie"), KYCFront("Kyc Front");

        private final String value;
        private Document document;

        DocumentTypes(String value) { this.value = value; }

        @Override
        public String toString() { return value; }

        @Override
        public Document getDocument() {
            return document;
        }

        @Override
        public void setDocument(Document document) {
            this.document = document;
        }
    }
}
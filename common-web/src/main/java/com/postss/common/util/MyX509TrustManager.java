package com.postss.common.util;

import java.io.InputStream;
import java.security.KeyStore;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

public class MyX509TrustManager implements X509TrustManager {

    X509TrustManager sunJSSEX509TrustManager;

    private final String keyStoreFile = PropertiesUtil.getProperty("keyStore.file");
    private final String keyStorePassword = PropertiesUtil.getProperty("keyStore.password");

    public MyX509TrustManager() throws Exception {
        InputStream is = null;
        KeyStore ks = KeyStore.getInstance("JKS");
        is = getClass().getResourceAsStream(keyStoreFile);
        ks.load(is, keyStorePassword.toCharArray());
        TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509", "SunJSSE");
        tmf.init(ks);
        TrustManager tms[] = tmf.getTrustManagers();

        for (int i = 0; i < tms.length; i++) {
            if (tms[i] instanceof X509TrustManager) {
                sunJSSEX509TrustManager = (X509TrustManager) tms[i];
                if (is != null) {
                    is.close();
                }
                return;
            }
        }

        throw new Exception("Couldn't initialize");
    }

    /*
     * Delegate to the default trust manager.
     */
    public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        try {
            sunJSSEX509TrustManager.checkClientTrusted(chain, authType);
        } catch (CertificateException excep) {

        }
    }

    public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        try {
            sunJSSEX509TrustManager.checkServerTrusted(chain, authType);
        } catch (CertificateException excep) {

        }
    }

    public X509Certificate[] getAcceptedIssuers() {
        return sunJSSEX509TrustManager.getAcceptedIssuers();
    }
}

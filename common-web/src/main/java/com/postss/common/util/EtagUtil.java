/**
 * 
 */
package com.postss.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.xml.bind.DatatypeConverter;

import com.postss.common.constant.Constant;

/**
 * 七牛上传etag算法
 * @ClassName: Etag
 * @author sjw
 * @date 2016年7月18日 下午2:35:03
 */
public class EtagUtil {

    /*
    算法大体如下：
    如果你能够确认文件 <= 4M，那么
    hash = UrlsafeBase64([0x16, sha1(FileContent)])
    如果文件 > 4M,则
    hash = UrlsafeBase64([0x96, sha1([sha1(Block1), sha1(Block2), ...])]),
    其中 Block 是把文件内容切分为 4M 为单位的一个个块，也就是 
    BlockI = FileContent[I*4M:(I+1)*4M]
    */

    /*
    java 最新版本 
    https://github.com/qiniu/java-sdk/blob/master/src/main/java/com/qiniu/util/Etag.java
    android 最新版本
    https://github.com/qiniu/android-sdk/blob/master/library/src/main/java/com/qiniu/android/utils/Etag.java
    */

    /**
     * 获得etag值
     * @author sjw
     * @date 2016年7月18日 下午2:59:12
     */
    public static String getEtagCode(String fileName) {
        EtagUtil etag = new EtagUtil();
        String etagCode = "";
        try {
            etagCode = etag.calcETag(fileName);
        } catch (NoSuchAlgorithmException ex) {
            System.err.println("Unsupported algorithm:" + ex.getMessage());
        } catch (IOException ex) {
            System.err.println("IO Error:" + ex.getMessage());
        }
        return etagCode;

    }

    public static String getEtagCode(byte[] bytes) {
        EtagUtil etag = new EtagUtil();
        String etagCode = "";
        try {
            etagCode = etag.calcETag(bytes);
        } catch (NoSuchAlgorithmException ex) {
            System.err.println("Unsupported algorithm:" + ex.getMessage());
        }
        return etagCode;

    }

    private final int CHUNK_SIZE = 1 << 22;

    public byte[] sha1(byte[] data) throws NoSuchAlgorithmException {
        MessageDigest mDigest = MessageDigest.getInstance("sha1");
        return mDigest.digest(data);
    }

    public String urlSafeBase64Encode(byte[] data) {
        String encodedString = DatatypeConverter.printBase64Binary(data);
        encodedString = encodedString.replace('+', '-').replace('/', '_');
        return encodedString;
    }

    public String calcETag(String fileName) throws IOException, NoSuchAlgorithmException {
        String etag = "";
        File file = new File(fileName);
        if (!(file.exists() && file.isFile() && file.canRead())) {
            System.err.println("Error: File not found or not readable");
            return etag;
        }
        long fileLength = file.length();
        FileInputStream inputStream = new FileInputStream(file);
        if (fileLength <= CHUNK_SIZE) {
            byte[] fileData = new byte[(int) fileLength];
            inputStream.read(fileData, 0, (int) fileLength);
            byte[] sha1Data = sha1(fileData);
            int sha1DataLen = sha1Data.length;
            byte[] hashData = new byte[sha1DataLen + 1];
            System.arraycopy(sha1Data, 0, hashData, 1, sha1DataLen);
            hashData[0] = 0x16;
            etag = urlSafeBase64Encode(hashData);
        } else {
            int chunkCount = (int) (fileLength / CHUNK_SIZE);
            if (fileLength % CHUNK_SIZE != 0) {
                chunkCount += 1;
            }
            byte[] allSha1Data = new byte[0];
            for (int i = 0; i < chunkCount; i++) {
                byte[] chunkData = new byte[CHUNK_SIZE];
                int bytesReadLen = inputStream.read(chunkData, 0, CHUNK_SIZE);
                byte[] bytesRead = new byte[bytesReadLen];
                System.arraycopy(chunkData, 0, bytesRead, 0, bytesReadLen);
                byte[] chunkDataSha1 = sha1(bytesRead);
                byte[] newAllSha1Data = new byte[chunkDataSha1.length + allSha1Data.length];
                System.arraycopy(allSha1Data, 0, newAllSha1Data, 0, allSha1Data.length);
                System.arraycopy(chunkDataSha1, 0, newAllSha1Data, allSha1Data.length, chunkDataSha1.length);
                allSha1Data = newAllSha1Data;
            }
            byte[] allSha1DataSha1 = sha1(allSha1Data);
            byte[] hashData = new byte[allSha1DataSha1.length + 1];
            System.arraycopy(allSha1DataSha1, 0, hashData, 1, allSha1DataSha1.length);
            hashData[0] = (byte) 0x96;
            etag = urlSafeBase64Encode(hashData);
        }
        inputStream.close();
        return etag;
    }

    public String calcETag(byte[] bytes) throws NoSuchAlgorithmException {
        String etag = "";
        long fileLength = bytes.length;
        if (fileLength <= CHUNK_SIZE) {
            byte[] fileData = bytes;
            byte[] sha1Data = sha1(fileData);
            int sha1DataLen = sha1Data.length;
            byte[] hashData = new byte[sha1DataLen + 1];
            System.arraycopy(sha1Data, 0, hashData, 1, sha1DataLen);
            hashData[0] = 0x16;
            etag = urlSafeBase64Encode(hashData);
        } else {
            int chunkCount = (int) (fileLength / CHUNK_SIZE);
            if (fileLength % CHUNK_SIZE != 0) {
                chunkCount += 1;
            }
            byte[] allSha1Data = new byte[0];
            for (int i = 0; i < chunkCount; i++) {
                byte[] chunkData = new byte[CHUNK_SIZE];
                int bytesReadLen = 0;
                if (bytes.length - i * CHUNK_SIZE > CHUNK_SIZE) {
                    bytesReadLen = CHUNK_SIZE;
                } else {
                    bytesReadLen = bytes.length - i * CHUNK_SIZE;
                }
                System.arraycopy(bytes, i * CHUNK_SIZE, chunkData, 0, bytesReadLen);
                byte[] bytesRead = new byte[bytesReadLen];
                System.arraycopy(chunkData, 0, bytesRead, 0, bytesReadLen);
                byte[] chunkDataSha1 = sha1(bytesRead);
                byte[] newAllSha1Data = new byte[chunkDataSha1.length + allSha1Data.length];
                System.arraycopy(allSha1Data, 0, newAllSha1Data, 0, allSha1Data.length);
                System.arraycopy(chunkDataSha1, 0, newAllSha1Data, allSha1Data.length, chunkDataSha1.length);
                allSha1Data = newAllSha1Data;
            }
            byte[] allSha1DataSha1 = sha1(allSha1Data);
            byte[] hashData = new byte[allSha1DataSha1.length + 1];
            System.arraycopy(allSha1DataSha1, 0, hashData, 1, allSha1DataSha1.length);
            hashData[0] = (byte) 0x96;
            etag = urlSafeBase64Encode(hashData);
        }
        return etag;
    }

    public static void main(String[] args) throws UnsupportedEncodingException {
        System.out.println(getEtagCode("C:\\Users\\jwsun\\Desktop\\systemManage.zip"));
        System.out.println(getEtagCode(FileUtil.importFile(new File("C:\\Users\\jwsun\\Desktop\\systemManage.zip"))
                .getBytes(Constant.CHARSET.ISO_8859_1)));

    }

}

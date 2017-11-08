package com.postss.common.util;


/**
 * 上传七牛工具类
 * @ClassName: QiNiuUtil
 * @author sjw
 * @date 2016年7月19日 下午5:56:56
 * https://portal.qiniu.com/create
 */
public class QiNiuUtil {

    /*private static Auth auth = Auth.create(Constant.QINIU_CONFIG.ACCESS_KEY, Constant.QINIU_CONFIG.SECRET_KEY);

    public static class MyRet {
    	public long fsize;
    	public String key;
    	public String hash;
    	public int width;
    	public int height;
    	public String fileType;
    }

    //处理方法（尾缀）：
    //下载：?attname=     举个栗子：http://7xpss8.com1.z0.glb.clouddn.com/39fd7061-928f-4333-89bf-4b8258c5ac72.jpg?attname=
    //缩放：  ?imageView2/2/h/100   http://7xpss8.com1.z0.glb.clouddn.com/39fd7061-928f-4333-89bf-4b8258c5ac72.jpg?imageView2/2/h/100
    *//**
      * @bucket 存储空间  "jwsun"，
      * key "" null,
      * expires 有效时间 3600，
      * StringMap policy 上传策略 ，returnBody指定返回的参数，当前为：key(文件存储名，在上传对象put方法中指定即可)，hash,width,height
      */
    /*
    public static String getUpToken() {
    return auth
    		.uploadToken(
    				Constant.QINIU_CONFIG.BUCKET,
    				null,
    				3600,
    				new StringMap()
    						.putNotEmpty(
    								"returnBody",
    								"{\"key\":$(key),\"hash\":$(etag),\"width\":$(imageInfo.width),\"height\":$(imageInfo.height),\"fileType\":$(imageInfo.format),\"saveUrl\":$(key)}"));
    }

    //简单上传
    public static String getUpToken2() {
    return auth.uploadToken(Constant.QINIU_CONFIG.BUCKET);
    }

    *//**
      * 文件上传
      * @author sjw
      * @param data,data可以是文件字节数组，或者file对象
      * @param filename
      */
    /*
    public static Map<String, Object> upload(byte[] date, String filename) {
    Map<String, Object> map = new HashMap<String, Object>();
    try {
    	//创建上传对象
    	UploadManager uploadManager = new UploadManager();
    	String token = getUpToken();
    	//调用put方法
    	Response response = uploadManager.put(date, filename, token);
    	//处理结果放回
    	MyRet ret = response.jsonToObject(MyRet.class);
    	map.put("hash", ret.hash);
    	map.put("token", token);
    	map.put("saveName", Constant.QINIU_CONFIG.FILE_ACCESS_URL + ret.key);
    	map.put("width", ret.width);
    	map.put("height", ret.height);
    	map.put("state", "SUCCESS");
    	map.put("fileType", ret.fileType);
    } catch (QiniuException e) {
    	Response r = e.response;
    	map.put("state", "FAIL");
    	System.out.println(r.toString());
    	try {
    		System.out.println(r.bodyString());
    	} catch (QiniuException e1) {

    	}
    }
    return map;

    }

    *//**
      * 存储文件
      * @author sjw
      * @date 2016年7月20日 下午1:08:09
      */
    /*
    public static Map<String, Object> saveFile(MultipartFile file, String saveName) throws IOException {
    MultipartFile newFile = file;
    File tempFile = ((DiskFileItem) ((CommonsMultipartFile) newFile).getFileItem()).getStoreLocation();
    String localPath = tempFile.getAbsolutePath();//本地路径
    long lastDate = tempFile.lastModified();
    Date date = new Date(lastDate);
    String oriName = file.getOriginalFilename().trim();
    Map<String, Object> map = upload(file.getBytes(), saveName);
    map.put("size", file.getSize()); // 单位：KB
    map.put("originalName", oriName);
    map.put("lastTime", Constant.DATE.defaultDateFormat.format(date));//最后更新时间
    map.put("localPath", localPath);
    return map;
    }

    *//**
      * 传入七牛图片网址，输出下载地址
      * @author sjw
      * @date 2016年7月27日 上午11:07:12
      */
    /*
    public static String getDownloadUrl(String saveName, String picName) {
    String[] picNameSplit = picName.split("\\.");
    if (picNameSplit.length == 1) {//图片名没有后缀，默认使用png
    	picName += ".png";
    }
    String url = saveName + "?attname=" + picName;
    return url;
    }

    *//**
      * 传入七牛图片网址，输出图片信息map
      * @author sjw
      * @date 2016年8月17日 下午5:58:26
      */
    /*
    @SuppressWarnings("unchecked")
    public static Map<String, Object> getImageInfoMap(String url) {
    String imageInfo = getImageInfoJson(url);
    if (imageInfo == null || imageInfo == "")
    	return null;
    ObjectMapper objectMapper = new ObjectMapper();
    Map<String, Object> map = null;
    try {
    	map = objectMapper.readValue(imageInfo, HashMap.class);
    } catch (Exception e) {
    	e.printStackTrace();
    }
    return map;
    }

    *//**
      * 传入七牛图片网址，输出图片信息json
      * @author sjw
      * @date 2016年8月17日 下午5:58:26
      */
    /*
    public static String getImageInfoJson(String url) {
    String retUrl = url + "?imageInfo";
    String imageInfo = WebResourceUtil.getURLResponse(retUrl, RequestMethod.GET, null, null, "utf-8");
    return imageInfo;
    }

    *//**
      * 上传过程服务
      * @param file : 文件
      * @return Map<String,Object>
      * @author jwSun
      * @date 2016年8月14日下午5:26:30
      */
    /*
    public static Map<String, Object> uploadServer(MultipartFile[] file, Long maxSize) throws Exception {
    List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
    Map<String, Object> retMap = new HashMap<String, Object>();
    Map<String, Object> result = null;
    for (int i = 0; i < file.length; i++) {
    	if (file[i].getSize() > maxSize) {
    		result.put("文件超过最大限制", maxSize);
    	} else {
    		result = QiNiuUtil.saveFile(file[i], null);
    	}
    	list.add(result);
    }
    retMap.put("list", list);
    return retMap;
    }

    *//**
      * 上传过程服务
      * @param file : 文件
      * @return Map<String,Object>
      * @author jwSun
      * @date 2016年8月14日下午5:26:30
      */
    /*
    public static Map<String, Object> uploadServer(MultipartFile file, Long maxSize) throws Exception {

    Map<String, Object> retMap = new HashMap<String, Object>();
    Map<String, Object> result = new HashMap<String, Object>();
    if (file.getSize() > maxSize) {
    	result.put("文件超过最大限制", maxSize);
    } else {
    	result = QiNiuUtil.saveFile(file, null);
    }
    retMap.put("list", result);
    return retMap;
    }

    *//**
      * @description 删除指定七牛空间的指定文件
      * @author sjw
      * @date 2016年10月11日 下午3:48:06 
      * @param bucket 空间名
      * @param key 文件key值
      * @return
      */
    /*
    public static Boolean deleteFile(String bucket, String key) {
    try {
    	String entry = bucket + ":" + key;
    	String encodedString = EncryptUtil.encodeBase64Safe(entry.getBytes("utf-8"));
    	String authorization = auth.authorization("/delete/" + encodedString).get("Authorization").toString();
    	Map<String, String> map = new HashMap<String, String>();
    	map.put("Authorization", authorization);
    	WebResourceUtil.getURLResponse("http://rs.qiniu.com" + "/delete/" + encodedString, RequestMethod.POST, map,
    			null, null);
    	return true;
    } catch (Exception e) {
    	e.printStackTrace();
    	return false;
    }
    }

    @SuppressWarnings("unchecked")
    public static List<String> getAllFile() {
    String url = "rsf.qbox.me";
    String suffix = "/list?bucket=" + Constant.QINIU_CONFIG.BUCKET;
    String authorization = auth.authorization(suffix).get("Authorization").toString();
    Map<String, String> map = new HashMap<String, String>();
    map.put("Authorization", authorization);
    JSONObject jsonObject = (JSONObject) JSONObject.parse(WebResourceUtil.getURLResponse(url + suffix,
    		RequestMethod.POST, map, null, null));
    List<JSONObject> list = (List<JSONObject>) jsonObject.get("items");
    List<String> retList = new ArrayList<String>();
    for (JSONObject obj : list) {
    	retList.add(obj.get("key").toString());
    }
    return retList;
    }

    public static void main(String[] args) {
    getAllFile();
    }*/
}

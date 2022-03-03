package com.ggz.server.utils;

import org.csource.fastdfs.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * FastDFS工具类
 *
 * @author ggz on 2022/2/23
 */
public class FastDFSUtils {

    private static final Logger logger = LoggerFactory.getLogger(FastDFSUtils.class);

    /**
     * 初始化参数
     */
    static {
        try {
            String filePath = new ClassPathResource("config/fdfs_client.conf").getFile().getAbsolutePath();
            ClientGlobal.init(filePath);
        } catch (Exception e) {
            logger.error("读取fdfs文件失败");
        }
    }

    public static String[] upload(MultipartFile file) {
        String name = file.getOriginalFilename();
        logger.info("文件名：" + name);
        StorageClient storageClient = null;
        String[] uploadResult = null;
        try {
            //获取客户端
            storageClient = getStorageClient();
            //上传
            uploadResult = storageClient.upload_file(file.getBytes(), name.substring(name.lastIndexOf(".") + 1), null);

        } catch (Exception e) {
            logger.error("上传失败！", e.getMessage());
        }
        if (null == uploadResult) {
            logger.error("上传失败！", storageClient.getErrorCode());
        }
        return uploadResult;
    }

    public static FileInfo getFileInfo(String groupName, String remoteFileName) {
        StorageClient storageClient = null;
        try {
            //获取客户端
            storageClient = getStorageClient();

            return storageClient.get_file_info(groupName, remoteFileName);

        } catch (Exception e) {
            logger.error("文件信息获取失败！", e.getMessage());
        }
        return null;
    }

    /**
     * 下载文件
     *
     * @param groupName
     * @param remoteFileName
     * @return
     */
    public static InputStream downFile(String groupName, String remoteFileName) {
        StorageClient storageClient = null;
        try {
            //获取客户端
            storageClient = getStorageClient();

            byte[] bytes = storageClient.download_file(groupName, remoteFileName);

            InputStream inputStream = new ByteArrayInputStream(bytes);

            return inputStream;

        } catch (Exception e) {
            logger.error("文件信息获取失败！", e.getMessage());
        }
        return null;
    }

    /**
     * 删除文件
     *
     * @param groupName
     * @param remoteFileName
     */
    public static void deleteFile(String groupName, String remoteFileName) {
        StorageClient storageClient = null;
        try {
            //获取客户端
            storageClient = getStorageClient();

            storageClient.delete_file(groupName, remoteFileName);

        } catch (Exception e) {
            logger.error("文件删除失败！", e.getMessage());
        }
    }

    /**
     * 获取完整文件路径
     *
     * @return
     */
    public static String getTrackerUrl() {
        TrackerClient trackerClient = new TrackerClient();
        TrackerServer trackerServer = null;
        StorageServer storageServer = null;
        try {
            trackerServer = trackerClient.getTrackerServer();
            storageServer = trackerClient.getStoreStorage(trackerServer);
        } catch (Exception e) {
            logger.error("获取文件路径失败！", e.getMessage());
        }
        return "http://" + storageServer.getInetSocketAddress().getHostString() + ":80/";
    }

    /**
     * 生成StorageClient客户端
     *
     * @return
     * @throws IOException
     */
    private static StorageClient getStorageClient() throws IOException {
        TrackerServer trackerServer = getTrackerServer();
        StorageClient storageClient = new StorageClient(trackerServer);
        return storageClient;
    }

    /**
     * 生成TrackerServer服务器
     *
     * @return
     * @throws IOException
     */
    private static TrackerServer getTrackerServer() throws IOException {
        TrackerClient trackerClient = new TrackerClient();
        TrackerServer trackerServer = trackerClient.getTrackerServer();
        return trackerServer;
    }
}
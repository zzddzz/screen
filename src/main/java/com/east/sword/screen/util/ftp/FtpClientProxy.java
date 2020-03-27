package com.east.sword.screen.util.ftp;

import com.east.sword.screen.util.FileUtil;
import com.google.common.collect.Lists;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPFileFilter;

import java.io.*;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

/**
 * @CreateDate 9:16 2020/3/27.
 * @Author ZZD
 */
public class FtpClientProxy {

    //FTPClient对象
    public FTPClient ftpClient;

    public FtpClientProxy(FTPClient ftpClient) {
        this.ftpClient = ftpClient;
    }


    /**
     * 当前命令执行完成命令完成
     *
     * @throws IOException
     */
    public void complete() throws IOException {
        ftpClient.completePendingCommand();
    }

    /**
     * Description: 向FTP服务器上传文件
     *
     * @param remoteFile 上传到FTP服务器上的文件名
     * @param input      本地文件流
     * @return 成功返回true，否则返回false
     */
    public boolean uploadFile(String remoteFile, InputStream input) {
        boolean result = false;
        try {
            ftpClient.enterLocalPassiveMode();
            result = ftpClient.storeFile(remoteFile, input);
            input.close();
            ftpClient.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Description: 向FTP服务器上传文件
     *
     * @param remoteFile 上传到FTP服务器上的文件名
     * @param localFile  本地文件
     * @return 成功返回true，否则返回false
     * @Version1.0
     */
    public boolean uploadFile(String remoteFile, String localFile) {
        FileInputStream input = null;
        try {
            input = new FileInputStream(new File(localFile));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return uploadFile(remoteFile, input);
    }

    /**
     * 拷贝文件
     *
     * @param fromFile
     * @param toFile
     * @return
     * @throws Exception
     */
    public boolean copyFile(String fromFile, String toFile) throws Exception {
        InputStream in = getFileInputStream(fromFile);
        boolean flag = ftpClient.storeFile(toFile, in);
        in.close();
        return flag;
    }

    /**
     * 获取文件输入流
     *
     * @param fileName
     * @return
     * @throws IOException
     */
    public InputStream getFileInputStream(String fileName) throws Exception {
        ByteArrayOutputStream fos = new ByteArrayOutputStream();
        ftpClient.retrieveFile(fileName, fos);
        ByteArrayInputStream in = new ByteArrayInputStream(fos.toByteArray());
        fos.close();
        return in;
    }

    /**
     * Description: 从FTP服务器下载文件
     *
     * @return
     * @Version1.0
     */
    public boolean downFile(String remoteFile, String localFile) {
        boolean result = false;
        try {
            OutputStream os = new FileOutputStream(localFile);
            ftpClient.retrieveFile(remoteFile, os);
            os.flush();
            os.close();
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 从ftp中获取文件流
     *
     * @param filePath
     * @return
     * @throws Exception
     */
    public InputStream getInputStream(String filePath) throws Exception {
        InputStream inputStream = ftpClient.retrieveFileStream(filePath);
        return inputStream;
    }

    /**
     * ftp中文件重命名
     *
     * @param fromFile
     * @param toFile
     * @return
     * @throws Exception
     */
    public boolean rename(String fromFile, String toFile) throws Exception {
        boolean result = ftpClient.rename(fromFile, toFile);
        return result;
    }

    /**
     * 获取ftp目录下的所有图片文件
     *
     * @param dir
     * @param order -1 倒序,其他正序排列
     * @return
     */
    public List<FTPFile> getPicFiles(String dir, Integer searchSize, Integer order,String regexChar) throws Exception {
        List<FTPFile> rtnResult;
        FTPFile[] files = ftpClient.listFiles(dir);
        List<FTPFile> ftpFiles = Arrays.stream(files).filter(meta-> FileUtil.isPic(meta) && FileUtil.validatePicOfScreen(meta.getName(), regexChar))
                .collect(Collectors.toList());
        ftpFiles = ftpFiles.stream().sorted(Comparator.comparing(meta -> meta.getTimestamp().getTime())).collect(Collectors.toList());
        if (order == -1) {
            rtnResult = Lists.reverse(ftpFiles);
        } else {
            rtnResult = ftpFiles;
        }
        if (null != searchSize) {
            rtnResult = rtnResult.subList(0, searchSize > rtnResult.size() ? rtnResult.size() : searchSize);
        }
        return rtnResult;
    }

    /**
     * 获取ftp目录下的某种类型的文件
     *
     * @param dir
     * @param filter
     * @return
     */
    public FTPFile[] getPicFiles(String dir, FTPFileFilter filter) {
        FTPFile[] files = new FTPFile[0];
        try {
            files = ftpClient.listFiles(dir, filter);
        } catch (Throwable thr) {
            thr.printStackTrace();
        }
        return files;
    }

    /**
     * 创建文件夹
     *
     * @param remoteDir
     * @return 如果已经有这个文件夹返回false
     */
    public boolean makeDirectory(String remoteDir) throws Exception {
        boolean result = false;
        try {
            result = ftpClient.makeDirectory(remoteDir);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 创建目录
     *
     * @param dir
     * @return
     * @throws Exception
     */
    public boolean mkdirs(String dir) throws Exception {
        boolean result = false;
        if (null == dir) {
            return result;
        }
        ftpClient.changeWorkingDirectory("/");
        StringTokenizer dirs = new StringTokenizer(dir, "/");
        String temp = null;
        while (dirs.hasMoreElements()) {
            temp = dirs.nextElement().toString();
            //创建目录
            ftpClient.makeDirectory(temp);
            //进入目录
            ftpClient.changeWorkingDirectory(temp);
            result = true;
        }
        ftpClient.changeWorkingDirectory("/");
        return result;
    }

    /**
     * 删除文件
     *
     * @param fileName
     * @throws Exception
     */
    public void deleteFile(String fileName) throws Exception {
        ftpClient.deleteFile(fileName);
    }
}

package com.chengma.devplatform.service.impl;

import com.chengma.devplatform.common.dao.ResponseResult;
import com.chengma.devplatform.service.FileStreamManageService;
import com.jcraft.jsch.*;
import jcifs.smb.SmbException;
import jcifs.smb.SmbFile;
import jcifs.smb.SmbFileOutputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Decoder;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Vector;

import static org.aspectj.bridge.ISourceLocation.NO_FILE;

/**
 * Created by ddgui on 2017/10/16.
 */
@Service
@Transactional
public class FileStreamManageServiceImpl implements FileStreamManageService {

    @Autowired
    private Environment env;

    @Override
    public ResponseResult uploadFile(InputStream inputStream, HashMap<String, Object> params){
        String systemType = env.getProperty("serverConfig.systemType");
        if("windows".equals(systemType)){
            return this.uploadFileToWindows(inputStream,params);
        }else if("linux".equals(systemType)){
            return this.uploadFileToLinux(inputStream,params);
        }
        return null;
    }

    @Override
    public void uploadVideo(MultipartFile file, String videoPath, String newFileName) {
        InputStream in=null;
        try {
            in=file.getInputStream() ;
        } catch (IOException e) {
            e.printStackTrace();
        }
        HashMap<String, Object> params=new HashMap<>();
        params.put("uploadPath", videoPath);
        params.put("newFileName", newFileName);
        this.uploadFile(in,params);
    }

    @Override
    public void uploadImage(BASE64Decoder decoder, String str, String imagePath, String prefix, String newFileName){
        HashMap<String, Object> params = new HashMap<>();
        InputStream inputStream = null;
        try {
            inputStream = new ByteArrayInputStream(decoder.decodeBuffer(str.substring(prefix.length())));
        } catch (IOException e) {
            e.printStackTrace();
        }
        params.put("uploadPath", imagePath);
        params.put("newFileName", newFileName);
        this.uploadFile(inputStream,params);
    }

    /**
     * 上传文件到window服务器
     * @param inputStream
     * @param params
     * host : 服务器ip
     * port : 服务器端口
     * username : 服务器账号
     * password : 服务器密码
     * @return
     */
    public ResponseResult uploadFileToWindows(InputStream inputStream, HashMap<String, Object> params){
        ResponseResult response = new ResponseResult();
        String host = env.getProperty("serverConfig.host");
        String username = env.getProperty("serverConfig.username");
        String password = env.getProperty("serverConfig.password");
        String uploadPath = params.get("uploadPath") == null ? "" : params.get("uploadPath").toString();
        String newFileName = params.get("newFileName") == null ? "" : params.get("newFileName").toString();
        response = smbUpload(host,username,password,uploadPath,inputStream,newFileName);
        return response;
    }

    /**
     * 上传文件到linux服务器
     * @param inputStream
     * @param params
     * host : 服务器ip
     * port : 服务器端口
     * username : 服务器账号
     * password : 服务器密码
     * @return
     */
    public ResponseResult uploadFileToLinux(InputStream inputStream, HashMap<String, Object> params){
        ResponseResult response = new ResponseResult();
        String host = env.getProperty("serverConfig.host");
        Integer port = Integer.parseInt(env.getProperty("serverConfig.port"));
        String username = env.getProperty("serverConfig.username");
        String password = env.getProperty("serverConfig.password");
        String uploadPath = params.get("uploadPath") == null ? "" : params.get("uploadPath").toString();
        String newFileName = params.get("newFileName") == null ? "" : params.get("newFileName").toString();
        response = sshUpload(host,port,username,password,uploadPath,inputStream,newFileName);
        return response;
    }

    /**
     * ftp下载文件（夹）  从linux服务器
     * @param directory
     * @param remoteFileName
     * @param localFile
     * @return
     */
    @Override
    public ResponseResult downloadFileFromLinux(String directory,String remoteFileName,String localFile) {
        ResponseResult json = new ResponseResult();
        String host = env.getProperty("serverConfig.host");
        Integer port = Integer.parseInt(env.getProperty("serverConfig.port"));
        String username = env.getProperty("serverConfig.username");
        String password = env.getProperty("serverConfig.password");
        json = sshDownload(host,port,username,password,directory,remoteFileName,localFile);
        return json;
    }


    /**
     * smb下载文件（夹）  从Windows服务器
     */
    /*public ResponseResult downloadFileFromWindows(String srcFile, HttpServletResponse response) {
        ResponseResult json = new ResponseResult();
        String host = SysRestConfig.getInstance().getProperty("host");
        String username = SysRestConfig.getInstance().getProperty("username");
        String password = SysRestConfig.getInstance().getProperty("password");
        json = smbDownload(host,username,password,srcFile,response);
        return json;
    }
*/
     private ResponseResult smhDownload(String host, String username, String password, String srcFile, HttpServletResponse response) {
        SmbFile smbFile = null;
        InputStream in = null;
        OutputStream os = null;
        ResponseResult json = new ResponseResult();
        try {
            byte[] buffer = new byte[1024];
            //构建连接字符串,并取得文件连接
            StringBuilder conStr = new StringBuilder();
            conStr.append("smb://");
            conStr.append(username + ":" + password);
            conStr.append("@" + host + "/");
            conStr.append(srcFile);

            try {
                BufferedInputStream bis = null;
                smbFile = new SmbFile(conStr.toString());
                in = smbFile.getInputStream();
                os = response.getOutputStream();
                bis = new BufferedInputStream(in);
                int size = -1;
                while((size = in.read(buffer))!=-1){
                    os.write(buffer, 0,size);
                    os.flush();
                }
            } catch (MalformedURLException e) {
                json.setMsgCode("connect server fail");
                e.printStackTrace();
            }
        }catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if(os!=null){
                    os.close();
                }
                if(in!=null){
                    in.close();
                }
                json.setStatusCode(ResponseResult.SUCCESS_CODE);
            } catch (IOException e) {
                json.setMsgCode("close stream fail");
                e.printStackTrace();
            }
        }
        return json;
    }

    private ResponseResult sshDownload(String host, Integer port, String username, String password, String directory,String remoteFileName,String localFile) {
        ResponseResult response = new ResponseResult();
        ChannelSftp sftp = null;
        Session session = null;
        try {
            session = connect(host, port, username, password);
            Channel channel = session.openChannel("sftp");
            channel.connect();
            sftp = (ChannelSftp) channel;
            response = sshDownload(directory,remoteFileName,localFile,sftp);
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            if(sftp != null){
                sftp.disconnect();
            }
            if(session != null){
                session.disconnect();
            }
        }
        return response;
    }

    /**
     * 载单个文件
     * @param directory       ：远程下载目录(以路径符号结束)
     * @param remoteFileName  FTP服务器文件名称 如：xxx.txt ||xxx.txt.zip
     * @param localFile       本地文件路径 如 D:\\xxx.txt
     * @return
     */
    private static ResponseResult sshDownload(String directory,String remoteFileName,String localFile,ChannelSftp sftp){
        ResponseResult response = new ResponseResult();
        response.setStatusCode(ResponseResult.SUCCESS_CODE);
        File file = null;
        OutputStream output = null;
        try {
            file = new File(localFile);
            File fileParent = file.getParentFile();
            if (file.exists()){
                file.delete();
            }
            if(!fileParent.exists()){
                boolean flag=fileParent.mkdirs();
                if(flag){
                    System.out.println("创建文件夹成功");
                }else{
                    System.out.println("创建文件夹失败");
                }
            }
            file.createNewFile();
            sftp.cd(directory);
            output = new FileOutputStream(file);
            sftp.get(remoteFileName, output);
        }
        catch (Exception e) {
            e.printStackTrace();
            response.setStatusCode(ResponseResult.FAIL_CODE);
            response.setMsgCode("downLoadFail");
        }
        finally {
            if (output != null) {
                try {
                    output.close();
                }
                catch (Exception e) {
                    e.printStackTrace();
                    response.setStatusCode(ResponseResult.FAIL_CODE);
                    response.setMsgCode("downLoadFail");
                }
            }
        }
        return response;
    }


    public ResponseResult sshUpload(String host, Integer port, String username, String password, String uploadPath, InputStream inputStream, String newFileName) {
        ResponseResult response = new ResponseResult();
        ChannelSftp sftp = null;
        Session session = null;
        try {
            session = connect(host, port, username, password);
            Channel channel = session.openChannel("sftp");
            channel.connect();
            sftp = (ChannelSftp) channel;
            response = sshUpload(uploadPath, inputStream, sftp,newFileName);
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            if(sftp != null){
                sftp.disconnect();
            }
            if(session != null){
                session.disconnect();
            }
        }
        return response;
    }

    /**
     * 连接sftp服务器
     * @param host 远程主机ip地址
     * @param port sftp连接端口，null 时为默认端口
     * @param username 用户名
     * @param password 密码
     * @return
     * @throws JSchException
     */
    public Session connect(String host, Integer port, String username, String password) throws JSchException{
        Session session = null;
        try {
            JSch jsch = new JSch();
            if(port != null){
                session = jsch.getSession(username, host, port.intValue());
            }else{
                session = jsch.getSession(username, host);
            }
            session.setPassword(password);
            //设置第一次登陆的时候提示，可选值:(ask | yes | no)
            session.setConfig("StrictHostKeyChecking", "no");
            //30秒连接超时
            session.connect(30000);
        } catch (JSchException e) {
            e.printStackTrace();
            System.out.println("SFTPUitl 获取连接发生错误");
            throw e;
        }
        return session;
    }

    /**
     * sftp上传文件(夹)
     * @param uploadPath
     * @param inputStream
     * @param sftp
     * @throws Exception
     */
    public ResponseResult sshUpload(String uploadPath, InputStream inputStream, ChannelSftp sftp, String newFileName){
        System.out.println("sftp upload file [directory] : "+uploadPath);
        ResponseResult response = new ResponseResult();
        //这里有点投机取巧，因为ChannelSftp无法去判读远程linux主机的文件路径,无奈之举
        try {
            Vector content = sftp.ls(uploadPath);
            if(content == null){
                sftp.mkdir(uploadPath);
            }
        } catch (SftpException e) {
            try {
                sftp.mkdir(uploadPath);
            } catch (SftpException e1) {
                e1.printStackTrace();
            }
        }
        //进入目标路径

        try {
            sftp.cd(uploadPath);
            //中文名称的
            sftp.put(inputStream, new String(newFileName.getBytes(),"UTF-8"));
            response.setStatusCode(ResponseResult.SUCCESS_CODE);
        } catch (SftpException e) {
            e.printStackTrace();
            response.setMsgCode("noDir");//不存在该目录
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            response.setMsgCode("uploadFail");//上传失败
        } catch (IOException e) {
            e.printStackTrace();
            response.setMsgCode("uploadFail");//上传失败
        }finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return response;
    }

    public ResponseResult smbUpload(String host, String username, String password, String uploadPath, MultipartFile file, String newFileName){
        SmbFile smbFile = null;
        InputStream in = null;
        OutputStream os = null;
        ResponseResult response = new ResponseResult();
        try {
            in = file.getInputStream();

            byte[] buffer = new byte[1024];
            //构建连接字符串,并取得文件连接
            StringBuilder conStr = new StringBuilder();
            conStr.append("smb://");
            conStr.append(username + ":" + password);
            conStr.append("@" + host + "/");
            conStr.append(uploadPath );

            try {
                smbFile = new SmbFile(conStr.toString());
                if(!smbFile.exists()){
                    smbFile.mkdir();
                }
                conStr.append(newFileName);
                smbFile = new SmbFile(conStr.toString());
            }catch(SmbException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                response.setMsgCode("connect server fail");
                e.printStackTrace();
            }
            try {
                os = new BufferedOutputStream(new SmbFileOutputStream(smbFile));
                int size = -1;
                while((size = in.read(buffer))!=-1){
                    os.write(buffer, 0,size);
                }

            } catch (Exception e) {
                response.setMsgCode("write out fail");
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if(os!=null){
                    os.close();
                }
                if(in!=null){
                    in.close();
                }
                response.setStatusCode(ResponseResult.SUCCESS_CODE);
            } catch (IOException e) {
                response.setMsgCode("close stream fail");
                e.printStackTrace();
            }
        }
        return response;
    }


    public ResponseResult smbUpload(String host, String username, String password, String uploadPath, InputStream inputStream, String newFileName){
        SmbFile smbFile = null;
        OutputStream os = null;
        ResponseResult response = new ResponseResult();
        try {
            byte[] buffer = new byte[1024];
            //构建连接字符串,并取得文件连接
            StringBuilder conStr = new StringBuilder();
            conStr.append("smb://");
            conStr.append(username + ":" + password);
            conStr.append("@" + host + "/");
            conStr.append(uploadPath );

            try {
                smbFile = new SmbFile(conStr.toString());
                if(!smbFile.exists()){
                    smbFile.mkdir();
                }
                conStr.append(newFileName);
                smbFile = new SmbFile(conStr.toString());
            }catch(SmbException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                response.setMsgCode("connect server fail");
                e.printStackTrace();
            }
            try {
                os = new BufferedOutputStream(new SmbFileOutputStream(smbFile));
                int size = -1;
                while((size = inputStream.read(buffer))!=-1){
                    os.write(buffer, 0,size);
                }

            } catch (Exception e) {
                response.setMsgCode("write out fail");
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                if(os!=null){
                    os.close();
                }
                if(inputStream!=null){
                    inputStream.close();
                }
                response.setStatusCode(ResponseResult.SUCCESS_CODE);
            } catch (IOException e) {
                response.setMsgCode("close stream fail");
                e.printStackTrace();
            }
        }
        return response;
    }


}

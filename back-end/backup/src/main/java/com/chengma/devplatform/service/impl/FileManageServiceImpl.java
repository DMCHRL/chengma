package com.chengma.devplatform.service.impl;

import com.jcraft.jsch.*;
import com.chengma.devplatform.common.dao.ResponseResult;
import com.chengma.devplatform.service.FileManageService;
import jcifs.smb.SmbException;
import jcifs.smb.SmbFile;
import jcifs.smb.SmbFileOutputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Vector;

/**
 * Created by ddgui on 2017/10/16.
 */
@Service
@Transactional
public class FileManageServiceImpl implements FileManageService {

    @Autowired
    private Environment env;

    @Override
    public ResponseResult uploadFile(MultipartFile file, HashMap<String, Object> params) {
        String systemType = env.getProperty("serverConfig.systemType");
        if ("windows".equals(systemType)) {
            return this.uploadFileToWindows(file, params);
        } else if ("linux".equals(systemType)) {
            return this.uploadFileToLinux(file, params);
        }
        return null;
    }

    @Override
    public ResponseResult downloadFile(String srcPath, HttpServletResponse response) {
        ResponseResult json = new ResponseResult();
        String fileName = srcPath.substring(srcPath.lastIndexOf("/") + 1);
        try {
            srcPath = URLDecoder.decode(srcPath, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        InputStream in = null;
        OutputStream os = null;
        byte[] buffer = null;
        try {
            in = new URL(srcPath).openStream();
            buffer = new byte[1024];
            response.reset();
            response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
            response.setContentType("multipart/form-data;");

            os = response.getOutputStream();
            int size = -1;
            while ((size = in.read(buffer)) != -1) {
                os.write(buffer, 0, size);
            }
            os.flush();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
            json.setMsgCode("downloadFail");
        } finally {
            try {
                if (os != null) {
                    os.close();
                }
                if (in != null) {
                    in.close();
                }
                json.setStatusCode(ResponseResult.SUCCESS_CODE);
            } catch (IOException e) {
                json.setMsgCode("closeFail");
                e.printStackTrace();
            }
        }
        return json;
    }

    /**
     * 上传文件到window服务器
     *
     * @param file   文件
     * @param params host : 服务器ip
     *               port : 服务器端口
     *               username : 服务器账号
     *               password : 服务器密码
     * @return ResponseResult
     */
    public ResponseResult uploadFileToWindows(MultipartFile file, HashMap<String, Object> params) {
        ResponseResult response = new ResponseResult();
        String host = env.getProperty("serverConfig.host");
        String username = env.getProperty("serverConfig.username");
        String password = env.getProperty("serverConfig.password");
        String uploadPath = params.get("uploadPath") == null ? "" : params.get("uploadPath").toString();
        String newFileName = params.get("newFileName") == null ? "" : params.get("newFileName").toString();
        response = smbUpload(host, username, password, uploadPath, file, newFileName);
        return response;
    }

    /**
     * 上传文件到linux服务器
     *
     * @param file   文件
     * @param params host : 服务器ip
     *               port : 服务器端口
     *               username : 服务器账号
     *               password : 服务器密码
     * @return ResponseResult
     */
    public ResponseResult uploadFileToLinux(MultipartFile file, HashMap<String, Object> params) {
        ResponseResult response = new ResponseResult();
        String host = env.getProperty("serverConfig.host");
        Integer port = Integer.parseInt(env.getProperty("serverConfig.port"));
        String username = env.getProperty("serverConfig.username");
        String password = env.getProperty("serverConfig.password");
        String uploadPath = params.get("uploadPath") == null ? "" : params.get("uploadPath").toString();
        String newFileName = params.get("newFileName") == null ? "" : params.get("newFileName").toString();
        response = sshUpload(host, port, username, password, uploadPath, file, newFileName);
        return response;
    }

    /*
     * sftp下载文件（夹）  从linux服务器
     * @param srcFile 下载文件完全路径

    @Override
    public ResponseResult downloadFileFromLinux(String srcFile, HttpServletResponse response) {
        ResponseResult json = new ResponseResult();
        String host = SysRestConfig.getInstance().getProperty("host");
        Integer port = Integer.parseInt(SysRestConfig.getInstance().getProperty("port"));
        String username = SysRestConfig.getInstance().getProperty("username");
        String password = SysRestConfig.getInstance().getProperty("password");
        json = sshDownload(host,port,username,password,srcFile,response);
        return json;
    }
     */

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
    private ResponseResult smbDownload(String host, String username, String password, String srcFile, HttpServletResponse response) {
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
                while ((size = in.read(buffer)) != -1) {
                    os.write(buffer, 0, size);
                    os.flush();
                }
            } catch (MalformedURLException e) {
                json.setMsgCode("connect server fail");
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (os != null) {
                    os.close();
                }
                if (in != null) {
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


    public ResponseResult sshUpload(String host, Integer port, String username, String password, String uploadPath, MultipartFile file, String newFileName) {
        ResponseResult response = new ResponseResult();
        ChannelSftp sftp = null;
        Session session = null;
        try {
            session = connect(host, port, username, password);
            Channel channel = session.openChannel("sftp");
            channel.connect();
            sftp = (ChannelSftp) channel;
            response = sshUpload(uploadPath, file, sftp, newFileName);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (sftp != null) {
                sftp.disconnect();
            }
            if (session != null) {
                session.disconnect();
            }
        }
        return response;
    }

    /**
     * 连接sftp服务器
     *
     * @param host     远程主机ip地址
     * @param port     sftp连接端口，null 时为默认端口
     * @param username 用户名
     * @param password 密码
     * @return Session
     * @throws JSchException 异常
     */
    public Session connect(String host, Integer port, String username, String password) throws JSchException {
        Session session = null;
        try {
            JSch jsch = new JSch();
            if (port != null) {
                session = jsch.getSession(username, host, port.intValue());
            } else {
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
     *
     * @param uploadPath    上传路径
     * @param multipartFile 多文件
     * @param sftp          文件传送协议
     * @param newFileName   新文件名
     * @return ResponseResult
     */
    public ResponseResult sshUpload(String uploadPath, MultipartFile multipartFile, ChannelSftp sftp, String newFileName) {
        System.out.println("sftp upload file [directory] : " + uploadPath);
        ResponseResult response = new ResponseResult();
        //这里有点投机取巧，因为ChannelSftp无法去判读远程linux主机的文件路径,无奈之举
        try {
            Vector content = sftp.ls(uploadPath);
            if (content == null) {
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
        InputStream in = null;
        try {
            sftp.cd(uploadPath);
            in = multipartFile.getInputStream();
            //中文名称的
            sftp.put(in, new String(newFileName.getBytes(), "UTF-8"));
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
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return response;
    }

    public ResponseResult smbUpload(String host, String username, String password, String uploadPath, MultipartFile file, String newFileName) {
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
            conStr.append(uploadPath);

            try {
                smbFile = new SmbFile(conStr.toString());
                if (!smbFile.exists()) {
                    smbFile.mkdir();
                }
                conStr.append(newFileName);
                smbFile = new SmbFile(conStr.toString());
            } catch (SmbException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                response.setMsgCode("connect server fail");
                e.printStackTrace();
            }
            try {
                os = new BufferedOutputStream(new SmbFileOutputStream(smbFile));
                int size = -1;
                while ((size = in.read(buffer)) != -1) {
                    os.write(buffer, 0, size);
                }

            } catch (Exception e) {
                response.setMsgCode("write out fail");
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (os != null) {
                    os.close();
                }
                if (in != null) {
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

    public ResponseResult sshDownload(String host, Integer port, String username, String password, String srcPath, HttpServletResponse response) {
        ChannelSftp sftp = null;
        Session session = null;
        ResponseResult json = new ResponseResult();
        try {
            session = connect(host, port, username, password);
            Channel channel = session.openChannel("sftp");
            channel.connect();
            sftp = (ChannelSftp) channel;
            json = sshDownload(srcPath, sftp, response);
        } catch (Exception e) {
            e.printStackTrace();
            json.setMsgCode("connectFTPFail");
        } finally {
            if (sftp != null) {
                sftp.disconnect();
            }
            if (session != null) {
                session.disconnect();
            }
        }
        return json;
    }

    public ResponseResult sshDownload(String srcFile, ChannelSftp sftp, HttpServletResponse response) {
        System.out.println("sftp download file [directory] : " + srcFile);
        ResponseResult json = new ResponseResult();
        //文件
        if (srcFile.indexOf(".") > -1) {
            byte[] buff = new byte[1024];
            BufferedInputStream bis = null;
            OutputStream os = null;
            try {
                InputStream in = sftp.get(srcFile);
                os = response.getOutputStream();
                bis = new BufferedInputStream(in);
                int size = -1;
                while ((size = in.read(buff)) != -1) {
                    os.write(buff, 0, size);
                    os.flush();
                }
            } catch (IOException e) {
                json.setMsgCode("downloadFail");
                e.printStackTrace();
            } catch (SftpException e) {
                e.printStackTrace();
                System.out.println("ChannelSftp sftp下载文件发生错误");
            } finally {
                try {
                    if (os != null) {
                        os.close();
                    }
                    if (bis != null) {
                        bis.close();
                    }
                    json.setStatusCode(ResponseResult.SUCCESS_CODE);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
        return json;
    }
}

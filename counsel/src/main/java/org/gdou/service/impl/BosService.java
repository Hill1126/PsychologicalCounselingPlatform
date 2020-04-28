package org.gdou.service.impl;

import com.baidubce.services.bos.BosClient;
import lombok.extern.slf4j.Slf4j;
import org.gdou.common.result.Result;
import org.gdou.common.result.ResultGenerator;
import org.gdou.config.BaiDuBosConfig;
import org.gdou.model.po.User;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashSet;
import java.util.UUID;

/**
 * 关于百度Bos的service操作
 * @author HILL
 * @version V1.0
 * @date 2020/3/11
 **/
@Service
@Slf4j
public class BosService {

    private BosClient bosClient;
    private HashSet<String> imgSuffixSet;

    public BosService(BosClient bosClient) {
        this.bosClient = bosClient;
        this.imgSuffixSet = new HashSet<String>();
        imgSuffixSet.add(".jpg");
        imgSuffixSet.add(".png");
    }

    /**
     * 采用异步方法上传图片到百度BOS
     * @Author: HILL
     * @date: 2020/3/9 16:47
     *
     * @param avatar
     * @return: void
     **/
    public Result uploadAvatar(MultipartFile avatar, User user) throws IOException {
        //校验文件的后缀名是否为jpg、png
        if (!checkFileSuffix(avatar) ){
            return ResultGenerator.genFailResult("上传的文件必须为png、jpg文件");
        }
        //保存文件到百度的文件管理库
        var fileName = UUID.randomUUID().toString()+getSuffixName(avatar);
        var putObjectResponse = bosClient.putObject(BaiDuBosConfig.AVATAR_BUCKET_NAME, fileName, avatar.getInputStream());
        log.info("插入头像图片{}到BOS 成功返回信息{} ",fileName,putObjectResponse.getETag());
        user.setAvatarUrl(buildImgUrl(BaiDuBosConfig.AVATAR_BUCKET_NAME,fileName));
        return ResultGenerator.genSuccessResult();
    }


    public Result uploadArticleImg(MultipartFile image) throws IOException {
        //校验文件的后缀名是否为jpg、png
        if (!checkFileSuffix(image) ){
            return ResultGenerator.genFailResult("上传的文件必须为png、jpg文件");
        }
        //保存文件到百度的文件管理库
        var fileName = UUID.randomUUID().toString()+getSuffixName(image);
        var response = bosClient.putObject(BaiDuBosConfig.ARTICLE_BUCKET_NAME, fileName, image.getInputStream());
        log.info("插入文章图片{}到BOS 成功返回信息{} ",fileName,response.getETag());
        //构建访问url并返回
        var path = buildImgUrl(BaiDuBosConfig.ARTICLE_BUCKET_NAME,fileName);
        return Result.genSuccessResult(path);
    }



    /**
     * 检查图片文件后缀是否为jpg或png
     * @Author: HILL
     * @date: 2020/4/27 23:25
     *
     * @param file 要检查的文件对象
     * @return: boolean 是则返回true，否则返回false
    **/
    private  boolean checkFileSuffix(MultipartFile file){
        String suffixName = getSuffixName(file);
        if (!imgSuffixSet.contains(suffixName.toLowerCase()) ){
            return false;
        }
        return true;
    }

    @NotNull
    private String getSuffixName(MultipartFile file) {
        var originalFileName = file.getOriginalFilename();
        var index = originalFileName.lastIndexOf(".");
        return originalFileName.substring(index);
    }


    /**
     * 构建图片的url
     * @Author: HILL
     * @date: 2020/3/10 21:26
     *
     * @param fileName 图片文件名
     * @return: java.lang.String 完成的http访问路径
     **/
    @NotNull
    private String buildImgUrl(String bucketName,String fileName) {
        //https://avatar-img.gz.bcebos.com/test-img
        String url = "https://" +
                bucketName + "." +
                BaiDuBosConfig.GZ_ENDPOINT + "/" + fileName;
        return url;
    }


}

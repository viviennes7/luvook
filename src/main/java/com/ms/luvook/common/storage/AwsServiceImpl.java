package com.ms.luvook.common.storage;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.ms.luvook.common.util.Base64Utils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

@Component
public class AwsServiceImpl implements StorageService{
    private final static String BUCKET_NAME = "kms-bucket-01";

    @Value("${aws.access}")
    private String accessKey;

    @Value("${aws.secret}")
    private String secretKey;

    private AmazonS3 amazonS3;

    @PostConstruct
    public void init() {
        AWSCredentials awsCredentials = new BasicAWSCredentials(accessKey, secretKey);
        amazonS3 = new AmazonS3Client(awsCredentials);
    }

    @Override
    public void uploadFile(String base64, String subDir, String fileName) {
        byte[] bytes = Base64Utils.decodeBase64ToBytes(base64);
        InputStream fis = new ByteArrayInputStream(bytes);

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(bytes.length);
        metadata.setContentType("image/png");
        metadata.setCacheControl("public, max-age=31536000");
        try {
            PutObjectRequest putObjectRequest =
                    new PutObjectRequest(BUCKET_NAME, subDir + fileName, fis, metadata);
            putObjectRequest.setCannedAcl(CannedAccessControlList.PublicRead);
            PutObjectResult result = amazonS3.putObject(putObjectRequest);
        } catch (AmazonServiceException ase) {
            ase.printStackTrace();
        }
    }
}

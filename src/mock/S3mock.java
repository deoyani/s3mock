/**
 * This software was developed at the National Institute of Standards and Technology by employees of
 * the Federal Government in the course of their official duties. Pursuant to title 17 Section 105
 * of the United States Code this software is not subject to copyright protection and is in the
 * public domain. This is an experimental system. NIST assumes no responsibility whatsoever for its
 * use by other parties, and makes no guarantees, expressed or implied, about its quality,
 * reliability, or any other characteristic. We would appreciate acknowledgement if the software is
 * used. This software can be redistributed and/or modified freely provided that any derivative
 * works bear some notice that they are derived from it, and any modified versions bear some notice
 * that they have been modified.
 * @author: Deoyani Nandrekar-Heinis
 */

/**
 * @author dsn1
 *
 */

package mock;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.AnonymousAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import io.findify.s3mock.S3Mock;

import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class S3mock{
    
	public static void main(String[] args) {
        S3Mock api = S3Mock.create(8001, "/tmp/s3");
        api.start();

        AmazonS3 client = AmazonS3ClientBuilder
                .standard()
                .withPathStyleAccessEnabled(true)
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration("http://localhost:8001", "us-east-1"))
                .withCredentials(new AWSStaticCredentialsProvider(new AnonymousAWSCredentials()))
                .build();
        if(!client.doesBucketExist("local-pres")){
        	client.createBucket("local-pres");
        	client.putObject("local-pres", "file/name", "contents");
        }
        
        if(!client.doesBucketExist("local-cache")){
        	client.createBucket("local-cache");
        	client.putObject("local-cache", "file/name", "contents");
        }
        
    }
}
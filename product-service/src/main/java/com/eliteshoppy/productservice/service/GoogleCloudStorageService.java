package com.eliteshoppy.productservice.service;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.eliteshoppy.productservice.exception.StorageException;
import com.google.api.gax.paging.Page;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Acl;
import com.google.cloud.storage.Acl.Role;
import com.google.cloud.storage.Acl.User;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.google.common.collect.Lists;
import com.google.common.io.Files;

@Component("storageService")
public class GoogleCloudStorageService implements StorageService {

	private static final String FILE_SEPERATOR = "/";

	@Value("${es.gcloud.cloud-storage.bucket}")
	private String cloudStorageBucketName;
	@Value("${es.gcloud.cloud-storage.storage-dir}")
	private String storageDir;
	@Value("${es.gcloud.cloud-storage.storage-access-key-path}")
	private static String storageAccessKeyJsonPath;
	
	private static Storage storage;
	
	static {
		// storage = StorageOptions.getDefaultInstance().getService();
		GoogleCredentials credentials;
		try {
			credentials = GoogleCredentials.fromStream(new FileInputStream(storageAccessKeyJsonPath))
					.createScoped(Lists.newArrayList("https://www.googleapis.com/auth/cloud-platform"));
			storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	static void authExplicit(String jsonPath) throws IOException {
		GoogleCredentials credentials = GoogleCredentials.fromStream(new FileInputStream(jsonPath))
				.createScoped(Lists.newArrayList("https://www.googleapis.com/auth/cloud-platform"));
		Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();
		System.out.println("Buckets:");
		Page<Bucket> buckets = storage.list();
		for (Bucket bucket : buckets.iterateAll()) {
			System.out.println(bucket.toString());
		}
	}

	@Override
	public String store(MultipartFile file) throws StorageException {
		try {
			return uploadFile(file);
		} catch (IOException e) {
			throw new StorageException("Error occured while storing the file!", e);
		}
	}

	private String uploadFile(MultipartFile file) throws IOException {
	    String fileName = file.getName();
	    
	    // Temporary
	    fileName = "file.jpg";
	    
		String resolvedPath = getResolvedPath(encodeFileName(fileName));
		
		/*
		 * BlobInfo blobInfo =
		 * storage.create(BlobInfo.newBuilder(cloudStorageBucketName, resolvedPath)
		 * .setAcl(new ArrayList<>(Arrays.asList(Acl.of(User.ofAllUsers(),
		 * Role.WRITER)))) .build(), file.getInputStream());
		 */
		
		BlobInfo blobInfo =
		        storage.create(
		            BlobInfo
		                .newBuilder(cloudStorageBucketName, fileName)
		                // Modify access list to allow all users with link to read file
		                .setAcl(new ArrayList<>(Arrays.asList(Acl.of(User.ofAllUsers(), Role.READER))))
		                .build(),
		                file.getInputStream());
		
		return blobInfo.getMediaLink();
	}

	private String encodeFileName(String fileName) {
		return Base64.getEncoder().encodeToString((fileName + LocalDateTime.now().toString()).getBytes())
				+ Files.getFileExtension(fileName);
	}

	private String getResolvedPath(String fileName) {
		return storageDir + FILE_SEPERATOR + fileName;
	}

}

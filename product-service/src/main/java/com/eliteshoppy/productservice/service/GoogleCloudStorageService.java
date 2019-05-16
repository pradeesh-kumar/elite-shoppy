package com.eliteshoppy.productservice.service;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.eliteshoppy.productservice.exception.StorageException;
import com.google.api.gax.paging.Page;
import com.google.auth.oauth2.GoogleCredentials;
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
	
	private static Storage storage;
	
	static {
		storage = StorageOptions.getDefaultInstance().getService();
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
		String fileName = encodeFileName(file.getName());
		String resolvedPath = getResolvedPath(fileName);
		BlobInfo blobInfo = storage.create(BlobInfo.newBuilder(cloudStorageBucketName, resolvedPath).build(),
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

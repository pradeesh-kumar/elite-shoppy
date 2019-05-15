package com.eliteshoppy.productservice.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.eliteshoppy.productservice.exception.StorageException;
import com.google.cloud.storage.Acl;
import com.google.cloud.storage.Acl.Role;
import com.google.cloud.storage.Acl.User;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
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
		String resolvedPath = getResolvedPath(encodeFileName(fileName));
		
		BlobInfo blobInfo = storage.create(BlobInfo.newBuilder(cloudStorageBucketName, resolvedPath)
				.setAcl(new ArrayList<>(Arrays.asList(Acl.of(User.ofAllUsers(), Role.WRITER))))
				.build(), file.getInputStream());
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

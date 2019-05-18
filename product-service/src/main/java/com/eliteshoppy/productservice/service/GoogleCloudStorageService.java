package com.eliteshoppy.productservice.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.eliteshoppy.productservice.exception.StorageException;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.Storage.BlobSourceOption;
import com.google.cloud.storage.StorageOptions;

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
		String fileName = encodeFileName(file.getOriginalFilename());
		String resolvedPath = getResolvedPath(fileName);
		BlobInfo blobInfo = storage.create(
				BlobInfo.newBuilder(cloudStorageBucketName, resolvedPath).setContentType(file.getContentType()).build(),
				file.getInputStream());
		return fileName;
	}

	private String encodeFileName(String fileName) {
		return Base64.getEncoder().encodeToString((fileName + LocalDateTime.now().toString()).getBytes()).replace("=",
				"") + "." + FilenameUtils.getExtension(fileName);
	}

	private String getResolvedPath(String fileName) {
		return storageDir + FILE_SEPERATOR + fileName;
	}

	@Override
	public Blob retrieve(String fileName) throws StorageException {
		try {
			Blob blob = storage.get(BlobId.of(cloudStorageBucketName, getResolvedPath(fileName)));
			return blob;
		} catch (RuntimeException e) {
			throw new StorageException("Error occured while reading the file!", e);
		}
	}

	@Override
	public void delete(String fileName) {
		long blobGeneration = 42;
		storage.delete(cloudStorageBucketName, getResolvedPath(fileName),
				BlobSourceOption.generationMatch(blobGeneration));
	}

	@Override
	public void deleteMany(List<String> fileNames) {
		List<BlobId> blobIds = new ArrayList<>();
		fileNames.forEach(n -> {
			blobIds.add(BlobId.of(cloudStorageBucketName, getResolvedPath(n)));
		});
		storage.delete(blobIds);
	}

}

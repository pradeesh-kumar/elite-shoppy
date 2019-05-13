package com.eliteshoppy.productservice.service;

import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.WritableResource;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import com.eliteshoppy.eliteshoppycommons.exception.EliteShoppyException;
import com.eliteshoppy.productservice.exception.FileStorageException;
import com.eliteshoppy.productservice.exception.ImageNotFoundException;
import com.eliteshoppy.productservice.model.ProductImage;
import com.eliteshoppy.productservice.repository.ProductImageRepository;
import com.google.common.io.Files;

@Service
@RefreshScope
public class ImageStorageServiceImpl implements ImageStorageService {
	
	private static final String GCLOUD_STORAGE_PROTOCOL = "gs://";
	private static final String FILE_SEPERATOR = "/";

	@Autowired
	private ProductImageRepository imageRepo;
	@Value("${es.gcloud.cloud-storage.bucket}")
	private String cloudStorageBucketName;
	@Value("${es.gcloud.cloud-storage.storage-dir}")
	private String storageDir;
	
	@Override
	public List<ProductImage> upload(List<MultipartFile> imageFiles, String productId) {
		List<ProductImage> images = new ArrayList<>();
		if (CollectionUtils.isEmpty(imageFiles)) {
			throw new EliteShoppyException("Please upload atleast one image");
		}
		imageFiles.forEach(i -> {
			images.add(upload(i, productId));
		});
		return images;
	}
	
	private ProductImage upload(MultipartFile imageFile, String productId) {
		String fileName = imageFile.getName();
		String path = getResolvedPath(encodeFileName(fileName));
		Resource gcsFile = new FileSystemResource(path);

		try (OutputStream os = ((WritableResource) gcsFile).getOutputStream()) {
			os.write(imageFile.getBytes());
			ProductImage i = new ProductImage();
			i.setPath(fileName);
			i.setProductId(productId);
			return imageRepo.save(i);
		} catch (IOException e) {
			e.printStackTrace();
			throw new FileStorageException("Could not store file " + fileName + ". Please try again! " + e.getMessage(), e);
		}
	}
	
	@Override
	public ProductImage findById(String imageId) {
		return imageRepo.findById(imageId).orElseThrow(
				() -> new ImageNotFoundException(String.format("Image for the id %d is not available", imageId)));
	}

	@Override
	public List<ProductImage> findByProductId(String productId) {
		return imageRepo.findByProductId(productId).orElseThrow(() -> new ImageNotFoundException("Product doesn't have any images"));
	}

	@Override
	public void deleteById(String imageId) {
		imageRepo.deleteById(imageId);
	}

	@Override
	public void deleteByProductId(String productId) {
		// TODO Code to delete images from file system
		imageRepo.deleteByProductId(productId);
	}
	
	/*
	 * public Resource loadFileAsResource(String fileName) { try { Path filePath =
	 * storageLocation.resolve(fileName).normalize(); Resource resource = new
	 * UrlResource(filePath.toUri()); if (resource.exists()) { return resource; }
	 * else { throw new ImageNotFoundException("Image not found in storage" +
	 * fileName); } } catch (MalformedURLException e) { throw new
	 * ImageNotFoundException("Image not found in storage" + fileName, e); } }
	 */
	
	private String encodeFileName(String fileName) {
		return Base64.getEncoder().encodeToString((fileName + LocalDateTime.now().toString()).getBytes()) + Files.getFileExtension(fileName);
	}
	
	private String getResolvedPath(String fileName) {
		return GCLOUD_STORAGE_PROTOCOL + cloudStorageBucketName + storageDir + FILE_SEPERATOR + fileName;
	}

}

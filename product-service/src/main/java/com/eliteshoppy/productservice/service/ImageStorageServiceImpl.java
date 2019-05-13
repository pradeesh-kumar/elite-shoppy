package com.eliteshoppy.productservice.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.eliteshoppy.eliteshoppycommons.exception.EliteShoppyException;
import com.eliteshoppy.productservice.exception.FileStorageException;
import com.eliteshoppy.productservice.exception.ImageNotFoundException;
import com.eliteshoppy.productservice.model.ProductImage;
import com.eliteshoppy.productservice.repository.ProductImageRepository;

@Service
@RefreshScope
public class ImageStorageServiceImpl implements ImageStorageService {

	private Path storageLocation;
	private ProductImageRepository imageRepo;
	
	public ImageStorageServiceImpl(@Autowired ProductImageRepository imageRepo, @Value("${file.upload-dir}") String uploadDir) {
		this.storageLocation = Paths.get(uploadDir).toAbsolutePath().normalize();
        try {
            Files.createDirectories(this.storageLocation);
        } catch (Exception ex) {
            throw new FileStorageException("Could not create the directory where the uploaded files will be stored.", ex);
        }
	}
	
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
		String fileName = StringUtils.cleanPath(imageFile.getOriginalFilename());
		
		try {
			Path targetLocation = storageLocation.resolve(fileName);
			Files.copy(imageFile.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
			ProductImage i = new ProductImage();
			i.setPath(fileName);
			i.setProductId(productId);
			return imageRepo.save(i);
		} catch (IOException e) {
			throw new FileStorageException("Could not store file " + fileName + ". Please try again!", e);
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

}

package com.eliteshoppy.productservice.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import com.eliteshoppy.eliteshoppycommons.exception.EliteShoppyException;
import com.eliteshoppy.productservice.exception.ImageNotFoundException;
import com.eliteshoppy.productservice.exception.StorageException;
import com.eliteshoppy.productservice.model.ProductImage;
import com.eliteshoppy.productservice.repository.ProductImageRepository;

@Service
@RefreshScope
@DependsOn("storageService")
public class ImageServiceImpl implements ImageService {

	@Autowired
	private ProductImageRepository imageRepo;
	@Autowired
	private StorageService storageService;
	
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
		ProductImage productImage = new ProductImage();
		try {
			String fileName = storageService.store(imageFile);
			productImage.setPath(fileName);
			productImage.setProductId(productId);
			productImage = imageRepo.save(productImage);
		} catch (StorageException e) {
			throw new EliteShoppyException("Error occured while storing the image!", e);
		}
		return productImage;
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

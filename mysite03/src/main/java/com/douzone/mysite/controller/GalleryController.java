package com.douzone.mysite.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.douzone.mysite.service.FileUploadService;
import com.douzone.mysite.service.GalleryService;
import com.douzone.mysite.vo.GalleryVo;

@Controller
@RequestMapping("/gallery")
public class GalleryController {
	@Autowired
	private GalleryVo galleryVo;
	
	@Autowired
	private FileUploadService fileUploadService;
	
	@Autowired
	private GalleryService galleryService;
	
	@RequestMapping("")
	public String index(Model model) {
		List<GalleryVo> list = galleryService.getImages();
		
		for(GalleryVo vo : list)
			System.out.println(vo);
		model.addAttribute("list", list);
		
		return "gallery/index";
	}
	
	@RequestMapping(value="/upload", method=RequestMethod.POST)
	public String upload(
		@RequestParam(value="comments", required=true, defaultValue="") String comments,
		@RequestParam(value="file") MultipartFile multipartFile) {
		
		galleryVo.setComments(comments);
		galleryVo.setUrl(fileUploadService.restore(multipartFile));
		galleryService.saveImage(galleryVo);
		
		return "redirect:/gallery";
	}
	
	@RequestMapping("/delete/{no}")
	public String delete(
		@PathVariable("no") Long no) {
		
		galleryService.removeImage(no);
		
		return "redirect:/gallery";
	}
}

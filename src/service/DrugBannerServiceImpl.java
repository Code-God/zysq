package service;

import java.io.File;
import java.util.List;
import java.util.Map;

import model.bo.drug.DrugBanner;

import org.apache.cxf.common.util.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.base.log.LogUtil;

import dao.drug.DrugBannerDao;
import service.drug.IDrugBannerService;
import util.UploadUtil;

@Service("drugBannerService")
public class DrugBannerServiceImpl implements IDrugBannerService {
	Logger logger = LogUtil.getLogger(LogUtil.SERVER);
	@Autowired
	DrugBannerDao drugBannerDao;

	@Override
	public void saveBanner(DrugBanner drugBanner,File file) {
		String picpath;//图片路径
		if (drugBanner!=null) {
			if (file!=null) {
				picpath=UploadUtil.upLoadImage(file, "bannerpic");
				if (!StringUtils.isEmpty(picpath)) {
					drugBanner.setImgpath(picpath.trim());
					drugBanner.setImgname(picpath.substring(picpath.lastIndexOf("/")+1,picpath.length()));
				}
			}
		}
		
		drugBannerDao.saveBanner(drugBanner);

	}

	@Override
	public List<DrugBanner> getAll() {
		
		return drugBannerDao.getAll();
	}

	@Override
	public void delete(Long id) {
		DrugBanner drugBanner=drugBannerDao.getBannerById(id);
		if (drugBanner!=null) {
			//删除图片
			UploadUtil.deletePic(drugBanner.getImgpath());
			drugBannerDao.delete(drugBanner);
		}
		
	}

	@Override
	public void modify(DrugBanner drugBanner, Long id, File file) {
		String picpath;//图片路径
		DrugBanner oldBanner = null;
		if (drugBanner!=null) {
			oldBanner=drugBannerDao.getBannerById(id);
		}
		if (file!=null) {
			UploadUtil.deletePic(drugBanner.getImgpath());
			picpath=UploadUtil.upLoadImage(file, "bannerpic");
			if (!StringUtils.isEmpty(picpath)) {
				oldBanner.setImgpath(picpath);
				oldBanner.setImgname(picpath.substring(picpath.lastIndexOf("/")+1,picpath.length()));
			}
		}
		oldBanner.setImglink(drugBanner.getImglink());
		drugBannerDao.modify(oldBanner);
		
		
		
	}

	@Override
	public DrugBanner getBannerById(Long id) {
		
		return drugBannerDao.getBannerById(id);
	}

}

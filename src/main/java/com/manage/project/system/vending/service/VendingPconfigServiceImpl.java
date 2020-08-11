package com.manage.project.system.vending.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.util.CellRangeAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.manage.project.system.vending.mapper.VendingCabinetMapper;
import com.manage.project.system.vending.mapper.VendingLaneMapper;
import com.manage.project.system.vending.mapper.VendingLanepMapper;
import com.manage.project.system.vending.mapper.VendingModelMapper;
import com.manage.project.system.vending.mapper.VendingPconfigMapper;
import com.manage.project.system.vending.mapper.VendingPlaneMapper;
import com.manage.project.system.product.domain.ProductInfo;
import com.manage.project.system.util.SystemUtil;
import com.manage.project.system.vending.domain.VendingCabinet;
import com.manage.project.system.vending.domain.VendingLane;
import com.manage.project.system.vending.domain.VendingLanep;
import com.manage.project.system.vending.domain.VendingModel;
import com.manage.project.system.vending.domain.VendingPconfig;
import com.manage.project.system.vending.domain.VendingPlane;
import com.manage.project.system.vending.service.IVendingPconfigService;
import com.manage.project.system.vending.vo.Cols;
import com.manage.project.system.vending.vo.PconfigSaveVo;
import com.manage.project.system.vending.vo.VendingLaneVo;
import com.alibaba.druid.support.logging.Log;
import com.manage.common.exception.vending.LaneProductCapacityException;
import com.manage.common.exception.vending.LaneProductNullException;
import com.manage.common.exception.vending.LaneProductWarnCapException;
import com.manage.common.exception.vending.WarnCapGreatThanCapacityException;
import com.manage.common.support.Convert;
import com.manage.common.utils.DateUtils;
import com.manage.common.utils.StringUtils;
import com.manage.common.utils.bean.BeanUtils;
import com.manage.common.utils.poi.ExcelUtil;
import com.manage.common.utils.security.ShiroUtils;
import com.manage.framework.config.ManageConfig;
import com.manage.framework.web.domain.AjaxResult;

/**
 * 售货机配货模板 服务层实现
 * 
 * @author xufeng
 * @date 2018-08-31
 */
@Service
public class VendingPconfigServiceImpl implements IVendingPconfigService 
{
	@Autowired
	private VendingPconfigMapper vendingPconfigMapper;
	
	@Autowired
	private IVendingPlaneService vendingPlaneService;
	
	@Autowired
	private VendingCabinetMapper vendingCabinetMapper;
	
	@Autowired
	private VendingLaneMapper vendingLaneMapper;
	
	@Autowired
	private VendingLanepMapper vendingLanepMapper;
	
	@Autowired
	private VendingPlaneMapper vendingPlaneMapper;
	
	@Autowired
	private VendingModelMapper vendingModelMapper;
	
	private Logger log = LoggerFactory.getLogger(VendingPconfigServiceImpl.class);

	/**
     * 查询售货机配货模板信息
     * 
     * @param logid 售货机配货模板ID
     * @return 售货机配货模板信息
     */
    @Override
	public VendingPconfig selectVendingPconfigById(String logid)
	{
    	VendingPconfig config = vendingPconfigMapper.selectVendingPconfigById(logid);
    	
    	VendingPlane vendingPlane = new VendingPlane();
    	vendingPlane.setMConfigId(config.getMConfigId());
    	List<VendingPlane> planes = vendingPlaneService.selectVendingPlaneList(vendingPlane);
    	config.setPlanes(planes);
    	return config;
	}
	
	/**
     * 查询售货机配货模板列表
     * 
     * @param vendingPconfig 售货机配货模板信息
     * @return 售货机配货模板集合
     */
	@Override
	public List<VendingPconfig> selectVendingPconfigList(VendingPconfig vendingPconfig)
	{
	    return vendingPconfigMapper.selectVendingPconfigList(vendingPconfig);
	}
	
    /**
     * 新增售货机配货模板
     * 
     * @param vendingPconfig 售货机配货模板信息
     * @return 结果
     */
	@Override
	@Transactional
	public int insertVendingPconfig(PconfigSaveVo pconfigSaveVo)
	{
		String corpId = ShiroUtils.getCorpId();
		VendingPconfig vendingPconfig = new VendingPconfig();
		BeanUtils.copyBeanProp(vendingPconfig, pconfigSaveVo);
		vendingPconfig.setCorpId(ShiroUtils.getUser().getCorpId());
		vendingPconfig.setLogid(UUID.randomUUID().toString());
		String mConfigId = SystemUtil.getSeqId(corpId, "as_vending_pconfig");
		vendingPconfig.setMConfigId(mConfigId);
		vendingPconfig.setSeqId(1);
		int r = vendingPconfigMapper.insertVendingPconfig(vendingPconfig);
		
		List<Cols> rows = pconfigSaveVo.getLanes();
		if (rows != null && !rows.isEmpty()) {
			for (Cols cols : rows) {
				List<VendingLaneVo> vls = cols.getCols();
				if (vls != null && !vls.isEmpty()) {
					for (VendingLaneVo vlv : vls) {
						VendingPlane plane = new VendingPlane();
						plane.setArrange(1);
						if (vlv.getLanep().getCapacity() == null) {
							plane.setCapacity(0);
						} else {
							plane.setCapacity(vlv.getLanep().getCapacity());
						}
						plane.setCol(vlv.getCol());
						plane.setCorpId(corpId);
						plane.setCreateTime(DateUtils.getTime());
						plane.setLaneEId(vlv.getLanep().getLaneEId());
						plane.setLaneSate(vlv.getLanep().getLaneSate());
						plane.setLaneSId(vlv.getLanep().getLaneSId());
						plane.setLogid(UUID.randomUUID().toString());
						plane.setMConfigId(mConfigId);
						plane.setMLaneId(SystemUtil.getSeqId(corpId, "as_vending_plane"));
						plane.setName(pconfigSaveVo.getName());
						plane.setProductId(vlv.getLanep().getProductId());
						ProductInfo product = SystemUtil.getProductById(vlv.getLanep().getProductId());
						if (product != null) {
							plane.setProductPic(product.getPicJson());
							plane.setSalePrice(product.getSalePrice());
						}
						plane.setRow(vlv.getRow());
						if (vlv.getLanep().getWarnCap() == null) {
							plane.setWarnCap(0);
						} else {
							plane.setWarnCap(vlv.getLanep().getWarnCap());
						}
						//检查是否配置了商品
						if(StringUtils.isEmpty(plane.getProductId())) {
							throw new LaneProductNullException();
						}
						//检查最大容量和阈值
						if(plane.getWarnCap()==null||plane.getWarnCap()<1) {
							throw new LaneProductWarnCapException();
						}
						if(plane.getCapacity()==null||plane.getCapacity()<1){
							throw new LaneProductCapacityException();
						}
						if(plane.getWarnCap()>plane.getCapacity()) {
							throw new WarnCapGreatThanCapacityException();
						}
						vendingPlaneService.insertVendingPlane(plane);
					}
				}
			}
		}
		
		// 关联售卖机
		String cabs = pconfigSaveVo.getCabs();
		if (!StringUtils.isEmpty(cabs)) {
			String[] cabinetIds = cabs.split(",");
			for (String cabinetId : cabinetIds) {
				this.relation(mConfigId, cabinetId);
			}
		}
		return r;
	}
	
	/**
     * 修改售货机配货模板
     * 
     * @param vendingPconfig 售货机配货模板信息
     * @return 结果
     */
	@Override
	@Transactional
	public int updateVendingPconfig(PconfigSaveVo pconfigSaveVo)
	{
		VendingPconfig vendingPconfig = new VendingPconfig();
		BeanUtils.copyBeanProp(vendingPconfig, pconfigSaveVo);
		
	    int r = vendingPconfigMapper.updateVendingPconfig(vendingPconfig);
	    
	    vendingPlaneService.deleteVendingPlaneByConfigId(vendingPconfig.getLogid());
	    
	    // 新增模板货道
	    String corpId = ShiroUtils.getCorpId();
	    List<Cols> rows = pconfigSaveVo.getLanes();
		if (rows != null && !rows.isEmpty()) {
			for (Cols cols : rows) {
				List<VendingLaneVo> vls = cols.getCols();
				if (vls != null && !vls.isEmpty()) {
					for (VendingLaneVo vlv : vls) {
						VendingPlane plane = new VendingPlane();
						plane.setArrange(1);
						plane.setCapacity(vlv.getLanep().getCapacity());
						plane.setCol(vlv.getCol());
						plane.setCorpId(corpId);
						plane.setCreateTime(DateUtils.getTime());
						plane.setLaneEId(vlv.getLanep().getLaneEId());
						plane.setLaneSate(vlv.getLanep().getLaneSate());
						plane.setLaneSId(vlv.getLanep().getLaneSId());
						plane.setLogid(UUID.randomUUID().toString());
						plane.setMConfigId(pconfigSaveVo.getMConfigId());
						plane.setMLaneId(SystemUtil.getSeqId(corpId, "as_vending_plane"));
						plane.setName(pconfigSaveVo.getName());
						plane.setProductId(vlv.getLanep().getProductId());
						ProductInfo product = SystemUtil.getProductById(vlv.getLanep().getProductId());
						if (product != null) {
							plane.setProductPic(product.getPicJson());
							plane.setSalePrice(product.getSalePrice());
						}
						plane.setRow(vlv.getRow());
						plane.setWarnCap(vlv.getLanep().getWarnCap());
						//检查最大容量和阈值
						if(plane.getWarnCap()==null||plane.getWarnCap()<1) {
							throw new LaneProductWarnCapException();
						}
						//检查最大容量和阈值
						if(plane.getWarnCap()==null||plane.getWarnCap()<1) {
							throw new LaneProductWarnCapException();
						}
						if(plane.getCapacity()==null||plane.getCapacity()<1){
							throw new LaneProductCapacityException();
						}
						if(plane.getWarnCap()>plane.getCapacity()) {
							throw new WarnCapGreatThanCapacityException();
						}
						vendingPlaneService.insertVendingPlane(plane);
					}
				}
			}
		}
		return r;
	}

	/**
     * 删除售货机配货模板对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	@Transactional
	public int deleteVendingPconfigByIds(String ids)
	{
		vendingPlaneService.deleteVendingPlaneByConfigId(ids);
		int r = vendingPconfigMapper.deleteVendingPconfigByIds(Convert.toStrArray(ids));
		return r;
	}

	@Override
	public VendingPconfig selectVendingPconfigByMconfigId(String mConfigId) {
		return vendingPconfigMapper.selectVendingPconfigByMconfigId(mConfigId);
	}

	@Override
	@Transactional
	public int relation(String mConfigId, String cabinetId) {
		// 查询模板基础信息
		VendingPconfig pconfig = vendingPconfigMapper.selectVendingPconfigByMconfigId(mConfigId);
		// 查询模板货道信息
		VendingPlane vendingPlane = new VendingPlane();
		vendingPlane.setMConfigId(mConfigId);
		List<VendingPlane> planeList = vendingPlaneService.selectVendingPlaneList(vendingPlane);
		
		// 查询关联的货柜，并更新
		VendingCabinet vendingCabinet = vendingCabinetMapper.selectVendingCabinetByCabinetId(cabinetId);
		vendingCabinet.setSeqId(pconfig.getSeqId());
		vendingCabinet.setFactoryId(pconfig.getFactoryId());
		vendingCabinet.setCabinetType(pconfig.getCabinetType());
		vendingCabinet.setDeviceId(pconfig.getDeviceId());
		int r = vendingCabinetMapper.updateVendingCabinet(vendingCabinet);
		// 删除货柜原有所有货道与商品信息
		// 先删除货道商品
		vendingLanepMapper.deleteVendingLanepByCabinetId(cabinetId);
		vendingLaneMapper.deleteVendingLaneByCabinetId(cabinetId);
		// 插入模板的货道和商品
		if (planeList != null) {
			String corpId = ShiroUtils.getCorpId();
			for (VendingPlane vp : planeList) {
				// 循环插入货道及商品
				VendingLane vl = new VendingLane();
				vl.setLogid(UUID.randomUUID().toString());
				String slaneId = SystemUtil.getSeqId(corpId, "as_vending_lane");
				vl.setSlaneId(slaneId);
				vl.setSiteId(vendingCabinet.getSiteId());
				vl.setSiteName(vendingCabinet.getSiteName());
				vl.setArrange(vp.getArrange());
				vl.setCabinetId(cabinetId);
				vl.setCabinetType(vendingCabinet.getCabinetType());
				vl.setSiteName(vendingCabinet.getSiteName());
				vl.setLaneId(vp.getLaneSId());
				vl.setLaneCode(vp.getLaneSId());
				vl.setCurState(vp.getLaneSate());
				vl.setRow(vp.getRow());
				vl.setCol(vp.getCol());
				vl.setArrange(vp.getArrange());
				vl.setCreateTime(DateUtils.getTime());
				vl.setCorpId(corpId);
				vendingLaneMapper.insertVendingLane(vl);
				
				VendingLanep vlp = new VendingLanep();
				vlp.setLogid(UUID.randomUUID().toString());
				vlp.setSlaneId(slaneId);
				vlp.setSiteId(vendingCabinet.getSiteId());
				vlp.setSiteName(vendingCabinet.getSiteName());
				vlp.setLaneSId(vp.getLaneSId());
				vlp.setLaneEId(vp.getLaneEId());
				vlp.setProductId(vp.getProductId());
				vlp.setProductName(vp.getProductName());
				vlp.setProductPic(vp.getProductPic());
				vlp.setSalePrice(vp.getSalePrice());
				vlp.setCapacity(vp.getCapacity());
				vlp.setWarnCap(vp.getWarnCap());
				vlp.setCurCap(0);
				vlp.setProductSate("1");
				vlp.setPStateTime(DateUtils.getTime());
				vlp.setLaneSate(vp.getLaneSate());
				vlp.setLSateTime(DateUtils.getTime());
				vlp.setCreateTime(DateUtils.getTime());
				vlp.setCorpId(corpId);
				vendingLanepMapper.insertVendingLanep(vlp);
			}
		}
		return r;
	}

	/**
	 * 导出配货模板
	 * 
	 * @param pconfigSaveVo
	 * @return
	 * @throws IOException 
	 */
	@Override
	public AjaxResult export(PconfigSaveVo pconfigSaveVo) throws IOException {
		FileOutputStream fos = null;
		try {
			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet sheet = workbook.createSheet("配货模板");
			//插入配货模板信息
			ExcelUtil<VendingPconfig> vendingPconfigUtil = new ExcelUtil<VendingPconfig>(VendingPconfig.class);
			VendingPconfig vendingPconfig = vendingPconfigMapper.selectVendingPconfigByMconfigId(pconfigSaveVo.getMConfigId());
			vendingPconfig.setFactoryName(SystemUtil.parseFactoryId(vendingPconfig.getFactoryId()));
			vendingPconfig.setCabinetTypeName(SystemUtil.parseCabinetType(vendingPconfig.getCabinetType()));
			VendingModel vendingModel = vendingModelMapper.selectVendingModelById(vendingPconfig.getDeviceId());
			vendingPconfig.setDeviceCode(vendingModel.getDeviceId());
			List<VendingPconfig> vendingPconfigList = Arrays.asList(vendingPconfig);
			int endRow = vendingPconfigUtil.insertListToSheet(workbook, sheet, vendingPconfigList, 0, 0,"配货模板信息");
			//插入货道信息
			ExcelUtil<VendingPlane> vendingPlaneUtil = new ExcelUtil<>(VendingPlane.class);
			List<VendingPlane> vendingPlaneList = vendingPlaneMapper.selectVendingPlaneByMconfigId(pconfigSaveVo.getMConfigId());
			for (VendingPlane vendingPlane : vendingPlaneList) {
				vendingPlane.setProductName(SystemUtil.getProductNameById(vendingPlane.getProductId()));
			}
			vendingPlaneUtil.insertListToSheet(workbook, sheet, vendingPlaneList, endRow+1, 0,"配货模板货道信息");
			String path="file/excel/"+UUID.randomUUID().toString()+".xls";
			File file = new File(ManageConfig.getUploadPrefix()+path);
			fos = new FileOutputStream(file);
			workbook.write(fos);
			fos.close();
			return AjaxResult.success(path);
		}catch (Exception e) {
			log.error("导出配货模板失败:",e);
			if(fos!=null) {
				fos.close();
			}
			return AjaxResult.error("导出配货模板失败,请联系管理员");
		}
	}

	/**
	 * 查询绑定了该机型的配货模板
	 * 
	 * @param deviceIds
	 * @return
	 */
	@Override
	public int selectVendingPconfigListByDeviceIds(String[] deviceIds) {
		return vendingPconfigMapper.selectVendingPconfigListByDeviceIds(deviceIds);
	}
	
}

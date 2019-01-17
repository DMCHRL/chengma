package com.chengma.devplatform.service.impl;

import com.chengma.devplatform.common.constant.DevplatformConstants;
import com.chengma.devplatform.common.constant.EnumRole;
import com.chengma.devplatform.common.dao.ResponseResult;
import com.chengma.devplatform.common.page.PageCommon;
import com.chengma.devplatform.domain.User;
import com.chengma.devplatform.repository.AssetDetailRepository;
import com.chengma.devplatform.repository.AssetRepository;
import com.chengma.devplatform.service.AssetDetailService;
import com.chengma.devplatform.service.AssetService;
import com.chengma.devplatform.service.UserService;
import com.chengma.devplatform.service.dto.AssetDTO;
import com.chengma.devplatform.service.dto.AssetDetailDTO;
import com.chengma.devplatform.service.dto.FundSignalDTO;
import com.chengma.devplatform.service.mapper.AssetDetailMapper;
import com.chengma.devplatform.service.mapper.AssetMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2018/11/26.
 */
@Service
@Transactional
public class AssetDetailServiceImpl implements AssetDetailService {

    private final AssetDetailRepository assetDetailRepository;

    private final AssetDetailMapper assetDetailMapper;

    @Autowired
    private PageCommon pageCommon;

    @Autowired
    private UserService userService;

    @Autowired
    private AssetService assetService;

    public AssetDetailServiceImpl(AssetDetailRepository assetDetailRepository, AssetDetailMapper assetDetailMapper){
        this.assetDetailRepository=assetDetailRepository;
        this.assetDetailMapper=assetDetailMapper;
    }
    
    @Override
    public Page<AssetDetailDTO> pageList(HashMap<String, Object> params) {
        int page_number = (params.get("page_number") == null ? 1 : Integer.valueOf(params.get("page_number").toString()));
        int page_size = (params.get("page_size") == null ? 10 : Integer.valueOf(params.get("page_size").toString()));

        HashMap<String, Object> formParams = (HashMap<String, Object>) params.get("formParams");


        StringBuilder column = new StringBuilder(" select d.*,u.c_mobile,u.c_name,ifnull(j.first_name,'') as approve_name  ");
        StringBuilder cond = new StringBuilder(" from t_asset_detail d join t_lhfz_mobile_user u on d.c_user_id=u.c_user_id LEFT JOIN jhi_user j on j.id=d.c_approve_id  ");
        Page<AssetDetailDTO> page = pageCommon.execPage(column.toString(), cond.toString(), page_number, page_size, AssetDetailDTO.class);
        return page;
    }

    @Override
    public AssetDetailDTO save(AssetDetailDTO assetDetailDTO) {
        return assetDetailMapper.toDto(assetDetailRepository.save(assetDetailMapper.toEntity(assetDetailDTO)));
    }

    @Override
    public AssetDetailDTO createAssetDetailDTO(AssetDetailDTO assetDetailDTO) {
        //添加
        String userId = userService.getUserWithAuthorities().getId();
        if(StringUtils.isBlank(assetDetailDTO.getId())){
            assetDetailDTO.setCreateAt(new Date());
            assetDetailDTO.setUserId(userId);
            assetDetailDTO.setStatus(DevplatformConstants.ASSET_STATUS_APPLYING);
        }else{
            //修改
            assetDetailDTO.setApproveAt(new Date());
            assetDetailDTO.setApproveId(userId);
        }
        if(DevplatformConstants.USER_ASSET_FUND_OUT.equals(assetDetailDTO.getType())){
            assetDetailDTO.setMoney(-assetDetailDTO.getMoney());
        }
        if(DevplatformConstants.USER_ASSET_FUND_JOIN.equals(assetDetailDTO.getType())){
            assetDetailDTO.setApproveAt(new Date());
            assetDetailDTO.setApproveId(userId);
            assetDetailDTO.setStatus(DevplatformConstants.ASSET_STATUS_PASSED);
        }


        return assetDetailMapper.toDto(assetDetailRepository.save(assetDetailMapper.toEntity(assetDetailDTO)));
    }

    @Override
    public HashMap<String, Object> checkCreateAssetDetailDTO(AssetDetailDTO assetDetailDTO) {
        HashMap<String, Object>  retMap = new HashMap<>();

        if( userService.getUserWithAuthorities() == null){
            retMap.put("statusCode",ResponseResult.FAIL_CODE);
            retMap.put("msg","请登录");
            return  retMap;
        }

        if( assetDetailDTO.getBankNum() == null){
            retMap.put("statusCode",ResponseResult.FAIL_CODE);
            retMap.put("msg","请选择银行卡号");
            return  retMap;
        }

        if(DevplatformConstants.USER_ASSET_FUND_OUT.equals(assetDetailDTO.getType())){
            String userId = userService.getUserWithAuthorities().getId();
            AssetDTO assetDTO = assetService.findByUserId(userId);
            if(assetDTO.getBalance() < assetDetailDTO.getMoney()){
                retMap.put("statusCode",ResponseResult.FAIL_CODE);
                retMap.put("msg","请输入有效金额");
                return  retMap;
            }
        }

        retMap.put("statusCode", ResponseResult.SUCCESS_CODE);
        return retMap;
    }

    @Override
    public AssetDetailDTO findOne(String id) {
        return assetDetailMapper.toDto(assetDetailRepository.findOne(id));
    }

    @Override
    public List<AssetDetailDTO> findAll() {
        return assetDetailMapper.toDto(assetDetailRepository.findAll());
    }

    @Override
    public void delete(String id) {
        assetDetailRepository.delete(id);
    }

    @Override
    public List<AssetDetailDTO> loadMyAssetDetail() {
        return assetDetailMapper.toDto(assetDetailRepository.findByUserIdEquals(userService.getUserWithAuthorities().getId()));
    }

    @Override
    public HashMap<String, Object> approveAssetDetail(HashMap<String, Object> params) {
        HashMap<String, Object>  retMap = new HashMap<>();
        String id = params.get("id") == null? "" : (String)params.get("id");
        String status = params.get("status") == null? " " : ((String)params.get("status"));
        User user = userService.getUserWithAuthorities();
        AssetDetailDTO assetDetailDTO = findOne(id);

        if(!(EnumRole.ADMIN.value().equals(user.getDepartment()) || EnumRole.SERVICE.value().equals(user.getDepartment()))){
            retMap.put("statusCode",ResponseResult.FAIL_CODE);
            retMap.put("msg","权限不足");
            return  retMap;
        }

        if(null == assetDetailDTO){
            retMap.put("statusCode",ResponseResult.FAIL_CODE);
            retMap.put("msg","请选择记录");
            return  retMap;
        }

        if(!DevplatformConstants.ASSET_STATUS_APPLYING.equals(assetDetailDTO.getStatus())){
            retMap.put("statusCode",ResponseResult.FAIL_CODE);
            retMap.put("msg","已处理...");
            return  retMap;
        }


        assetDetailDTO.setApproveId(user.getId());
        assetDetailDTO.setApproveAt(new Date());

        //更新资金
        if(DevplatformConstants.ASSET_STATUS_PASSED.equals(status)){
            assetService.updateAsset(assetDetailDTO.getUserId(),assetDetailDTO.getMoney(),assetDetailDTO.getType());
            assetDetailDTO.setStatus(DevplatformConstants.ASSET_STATUS_PASSED);
        }else if(DevplatformConstants.ASSET_STATUS_REJECT.equals(status)){
            assetDetailDTO.setStatus(DevplatformConstants.ASSET_STATUS_REJECT);
        }
        save(assetDetailDTO);

        retMap.put("statusCode", ResponseResult.SUCCESS_CODE);
        return retMap;
    }
}
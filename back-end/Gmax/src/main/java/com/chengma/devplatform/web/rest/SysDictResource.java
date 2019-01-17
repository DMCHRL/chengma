package com.chengma.devplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.chengma.devplatform.common.dao.ResponseResult;
import com.chengma.devplatform.common.util.ColumnAttr;
import com.chengma.devplatform.common.util.DateUtil;
import com.chengma.devplatform.common.util.ExcelExport;
import com.chengma.devplatform.common.util.IRowSetting;
import com.chengma.devplatform.service.SysDictService;
import com.chengma.devplatform.service.dto.SysDictDTO;
import com.chengma.devplatform.service.dto.SysSetting;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * REST controller for managing SysDict.
 *
 * @author administrator
 */
@RestController
@RequestMapping("/api")
public class SysDictResource {

    private final Logger log = LoggerFactory.getLogger(SysDictResource.class);

    private static final String ENTITY_NAME = "sysDict";

    private final SysDictService sysDictService;

    public SysDictResource(SysDictService sysDictService) {
        this.sysDictService = sysDictService;
    }


    /**
     * post /sysDict/save ：保存数据字典
     *
     * @param sysDictDTO 数据字典信息模型
     * @return statusCode:成功0000，失败0100。
     */
    @PostMapping("/sysDict/save")
    @Timed
    public ResponseEntity<ResponseResult> createSysDict(@RequestBody SysDictDTO sysDictDTO) {
        ResponseResult json = sysDictService.save(sysDictDTO);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

    /**
     * post /sysDict/list ：获取数据字典page。
     *
     * @param params 参数：{page_number:"",page_size:"",formParams{group:"",dictKey:"",dictDesc:"",parentGroup:""}}
     * @return statusCode:成功0000，失败0100。
     */
    @PostMapping("/sysDict/list")
    @Timed
    public ResponseEntity<ResponseResult> getAllSysDicts(@RequestBody HashMap<String, Object> params) {
        ResponseResult response = sysDictService.findAll(params);
        HashMap<String, Object> data = (HashMap<String, Object>) response.getData();
        HttpHeaders headers = (HttpHeaders) data.get("headers");
        return new ResponseEntity<>(response, headers, HttpStatus.OK);
    }

    /**
     * post /sysDict/get ：获取数据字典信息
     *
     * @param params 参数：{id:"",parentGroup:""}
     * @return statusCode:成功0000，失败0100。
     */
    @PostMapping("/sysDict/get")
    @Timed
    public ResponseEntity<ResponseResult> getSysDict(@RequestBody HashMap<String, Object> params) {
        HashMap<String, Object> response = new HashMap<>();
        SysDictDTO sysDictDTO = sysDictService.findOne(params);
        ResponseResult json = new ResponseResult();
        json.setData(sysDictDTO);
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

    /**
     * get /sysDict/delete/{id} ：删除数据字典（可批量删除）
     *
     * @param id 需要删除的数据字典的id，多个id之间用“,”隔开。
     * @return statusCode:成功0000，失败0100。
     */
    @GetMapping("/sysDict/delete/{id}")
    @Timed
    public ResponseEntity<ResponseResult> deleteSysDict(@PathVariable String id) {
        ResponseResult response = sysDictService.logicalDelete(id);
        return new ResponseEntity<>(response, null, HttpStatus.OK);
    }

    /**
     * get /sysDict/export ：导出数据字典信息的excel文件。
     *
     * @param group       组别
     * @param dictKey     键
     * @param dictDesc    描述（值）
     * @param parentGroup 父组别
     * @param response    HTTP响应
     * @throws Exception 异常
     */
    @GetMapping("/sysDict/export")
    @Timed
    public void Export(String group, String dictKey, String dictDesc, String parentGroup, HttpServletResponse response) throws Exception {

        List<SysDictDTO> lists = sysDictService.getSysDictList(group, dictKey, dictDesc, parentGroup);

        final ExcelExport ee = new ExcelExport();
        Date date = new Date();
        //导出excel的sheet名字
        ee.createSheet("sheet1");
        // 表头
        CellStyle headCellStyle = ee.createCellStyle();
        headCellStyle.setAlignment(CellStyle.ALIGN_CENTER);
        headCellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        headCellStyle.setWrapText(true);
        headCellStyle.setBorderBottom(CellStyle.BORDER_THIN);
        headCellStyle.setBorderLeft(CellStyle.BORDER_THIN);
        headCellStyle.setBorderRight(CellStyle.BORDER_THIN);
        headCellStyle.setBorderTop(CellStyle.BORDER_THIN);
        Font headFont = ee.createFont();
        headFont.setFontHeight((short) 500);
        headFont.setFontHeightInPoints((short) 20);
        headFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
        headCellStyle.setFont(headFont);
        ee.mergeCell(0, 1, 0, 5);
        Cell headCell = ee.getCell(0, 0);
        headCell.setCellStyle(headCellStyle);
        headCell.setCellValue("数字字典列表");

        //Excel单元格格式样式（居中，字体，边框等）
        final CellStyle colCellStyle = ee.createCellStyle();
        // 设置背景色
        colCellStyle.setFillForegroundColor((short) 11);
        colCellStyle.setFillPattern(HSSFCellStyle.NO_FILL);
        //设置边框
        colCellStyle.setAlignment(CellStyle.ALIGN_CENTER);
        colCellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        colCellStyle.setWrapText(true);
        colCellStyle.setBorderBottom(CellStyle.BORDER_THIN);
        colCellStyle.setBorderLeft(CellStyle.BORDER_THIN);
        colCellStyle.setBorderRight(CellStyle.BORDER_THIN);
        colCellStyle.setBorderTop(CellStyle.BORDER_THIN);
        //设置字体
        Font colFont = ee.createFont();
        //colFont.setFontName("仿宋_GB2312");
        colFont.setBoldweight(Font.BOLDWEIGHT_NORMAL);
        colFont.setFontHeightInPoints((short) 11);
        colCellStyle.setFont(colFont);

        ee.setList(lists, new IRowSetting() {
            @Override
            public int startRow() {
                return 2;
            }//设置首行位置

            @Override
            public Object contentSetting(Object obj, int row) {
                SysDictDTO s = (SysDictDTO) obj;
                for (int i = 0; i < 6; i++) {
                    int rowNum = this.startRow() + row + 1;
                    Cell cell = ee.getCell(rowNum, i);
                    cell.setCellStyle(colCellStyle);
                }
                return s;
            }

            @Override
            public boolean columnSetting(List<ColumnAttr> list) {

                ColumnAttr group = new ColumnAttr("组别", "group", 15, colCellStyle);
                ColumnAttr dictKey = new ColumnAttr("键", "dictKey", 15, colCellStyle);
                ColumnAttr dictDesc = new ColumnAttr("描述", "dictDesc", 15, colCellStyle);
                ColumnAttr sort = new ColumnAttr("排序", "sort", 15, colCellStyle);
                ColumnAttr parentGroup = new ColumnAttr("父组别", "parentGroup", 15, colCellStyle);
                ColumnAttr childrenCount = new ColumnAttr("子组别数", "childrenCount", 15, colCellStyle);
                list.add(group);
                list.add(dictKey);
                list.add(dictDesc);
                list.add(sort);
                list.add(parentGroup);
                list.add(childrenCount);
                return true;
            }
        });

        ee.export("数字字典列表-" + DateUtil.formatSimpleDate(date), response);

    }

    /**
     * post /sysDict/getSysShowHeader ：获取系统头部菜单。
     *
     * @return statusCode:成功0000，失败0100。
     */
    @PostMapping("/sysDict/getSysShowHeader")
    @Timed
    public ResponseEntity<ResponseResult> getSysShowHeader() {
        SysSetting sysSetting = sysDictService.getSysShowHeader();
        ResponseResult json = new ResponseResult();
        json.setData(sysSetting);
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

    /**
     * post /sysDict/dictList ：根据父组别获取数据字典列表
     *
     * @param parentGroup 父组别
     * @return statusCode:成功0000，失败0100。
     * @throws Exception 异常
     */
    @GetMapping("/sysDict/dictList")
    @Timed
    public ResponseEntity<ResponseResult> Export(String parentGroup) throws Exception {
        List<SysDictDTO> lists = sysDictService.getDictList(parentGroup);
        ResponseResult response = new ResponseResult();
        response.setData(lists);
        response.setStatusCode(ResponseResult.SUCCESS_CODE);
        return new ResponseEntity<>(response, null, HttpStatus.OK);
    }

    /**
     * post /sysDict/enableSysDict ：启用、禁用数据字典
     *
     * @param params 参数：{id:''}id，字典id；delFlag，状态。
     * @return statusCode:成功0000，失败0100。
     */
    @PostMapping("/sysDict/enableSysDict")
    @Timed
    public ResponseEntity<ResponseResult> enableSysDict(@RequestBody HashMap<String, Object> params) {
        log.debug("启用、禁用数据");
        HashMap<String, Object> response = sysDictService.enableSysDict(params);
        ResponseResult json = new ResponseResult();
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

}
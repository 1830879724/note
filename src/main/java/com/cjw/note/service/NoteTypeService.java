package com.cjw.note.service;

import cn.hutool.core.util.StrUtil;
import com.cjw.note.dao.NoteTypeDao;
import com.cjw.note.po.NoteType;
import com.cjw.note.vo.ResultInfo;

import java.util.List;

public class NoteTypeService {

    private NoteTypeDao typeDao=new NoteTypeDao();

    /**
     * 查询用户列表
         1. 调用Dao层的查询方法，通过用户ID查询类型集合
         2. 返回类型集合
     * @param userId
     * @return
     */
    public List<NoteType> findTypeList(Integer userId){
        // 1. 调用Dao层的查询方法，通过用户ID查询类型集合
        List<NoteType> typeList=typeDao.findTypeListByUserId(userId);
        // 2. 返回类型集合
        return typeList;
    }

    /**
     * 删除类型
             1. 判断参数是否为空
             2. 调用Dao层，通过类型ID查询云记记录的数量
             3. 如果云记数量大于0，说明存在子记录，不可删除
             code=0，msg="该类型存在子记录，不可删除"，返回resultInfo对象
             4. 如果不存在子记录，调用Dao层的更新方法，通过类型ID删除指定的类型记录，返回受影响的行数
             5. 判断受影响的行数是否大于0
             大于0，code=1；否则，code=0，msg="删除失败"
             6. 返回ResultInfo对象
     * @param typeId
     * @return
     */
    public ResultInfo<NoteType> deleteType(String typeId) {
        ResultInfo<NoteType> resultInfo= new ResultInfo<>();
        // 1. 判断参数是否为空
        if (StrUtil.isBlank(typeId)){
            resultInfo.setCode(0);
            resultInfo.setMsg("系统异常，请重试");
            return resultInfo;
        }
        //2. 调用Dao层，通过类型ID查询云记记录的数量
        long noteCount=typeDao.findBoteCountByTypeId(typeId);
        //3. 如果云记数量大于0，说明存在子记录，不可删除
        if (noteCount >0){
            resultInfo.setCode(0);
            resultInfo.setMsg("该类型存在子记录，不可删除");
            return resultInfo;
        }
        // 4. 如果不存在子记录，调用Dao层的更新方法，通过类型ID删除指定的类型记录，返回受影响的行数
        int row =typeDao.deleteTypeById(typeId);
        //5. 判断受影响的行数是否大于0
        if (row>0){
            resultInfo.setCode(1);
        }else {
            resultInfo.setCode(0);
            resultInfo.setMsg("删除类型失败");
        }
        return  resultInfo;
    }
}
